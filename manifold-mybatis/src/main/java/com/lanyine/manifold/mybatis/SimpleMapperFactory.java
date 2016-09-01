package com.lanyine.manifold.mybatis;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.lanyine.manifold.mybatis.util.JarHelper;

public class SimpleMapperFactory implements MapperFactory, InitializingBean {
	private final Logger log = LoggerFactory.getLogger(SimpleMapperFactory.class);

	private String guideName;
	private String suffix;

	public SimpleMapperFactory() {
		this.guideName = "mybatis-configuration.xml";
		this.suffix = "Mapper.xml";
	}

	public SimpleMapperFactory(String guideName, String suffix) {
		this.guideName = guideName;
		this.suffix = suffix;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("init mybatis [mapper<===>entity]");
		cacheData(guideName, suffix);
		log.info("init mybatis successful,sum:" + mapperCaches.size());
	}

	@Override
	public void cacheData(String guideName, String suffix) {
		if (mapperCaches.isEmpty()) {
			try {
				URL resource = Thread.currentThread().getContextClassLoader().getResource(guideName);

				if (resource != null) {
					if (resource.getProtocol().equals("file")) {
						File file = new File(resource.getPath()).getParentFile();
						Collection<File> files = FileUtils.listFiles(file, new IOFileFilter() {
							public boolean accept(File file) {
								return file.getName().endsWith(suffix);
							}

							public boolean accept(File file, String s) {
								return true;
							}

						}, new IOFileFilter() {
							public boolean accept(File file) {
								return true;
							}

							public boolean accept(File file, String s) {
								return true;
							}

						});
						for (File f : files) {
							cacheDataFromMapper(f.getName(), FileUtils.readFileToString(f, "UTF-8"));
						}
					} else if (resource.getProtocol().equals("jar")) {
						String jarFilePath = resource.getFile();
						jarFilePath = jarFilePath.substring("file:".length(),
								jarFilePath.length() - ("!/" + guideName).length());
						jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");

						JarFile jarFile = new JarFile(jarFilePath);

						List<String> fileNames = JarHelper.listFiles(jarFile, suffix);
						if (!fileNames.isEmpty()) {
							for (String fileName : fileNames) {
								cacheDataFromMapper(fileName, JarHelper.readFile(jarFile, fileName));
							}
						}
						jarFile.close();
					} else {
						log.error("Guide file[%s] is unsupport!", guideName);
					}
				} else {
					log.error("can't find [%s]", guideName);
				}
			} catch (Exception e) {
				log.error("load mybatis file failed:" + e.getMessage());
				throw new RuntimeException(e);
			}
		}
	}
}
