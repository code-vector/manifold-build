package com.lanyine.manifold.mybatis.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * jar文件工具
 *
 * @author shadow
 * @Date 2015年8月12日 下午9:58:17
 */
public class JarHelper {

	public static List<String> listFiles(JarFile jarFile, String endsWith) {
		List<String> files = new ArrayList<String>();
		if (jarFile != null && StringUtils.isEmpty(endsWith)) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();

				if (name.endsWith(endsWith)) {
					files.add(name);
				}
			}
		}
		return files;
	}

	public static List<String> readLines(JarFile jarFile, String fileName) throws IOException {
		if (jarFile == null || StringUtils.isEmpty(fileName))
			return null;

		List<String> lines = new ArrayList<String>();

		JarEntry entry = jarFile.getJarEntry(fileName);
		InputStream inputStream = jarFile.getInputStream(entry);

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}

		bufferedReader.close();
		inputStreamReader.close();

		return lines;
	}

	public static String readFile(JarFile jarFile, String fileName) throws IOException {
		if (jarFile == null || StringUtils.isEmpty(fileName))
			return null;

		StringBuilder sb = new StringBuilder();

		JarEntry entry = jarFile.getJarEntry(fileName);
		InputStream inputStream = jarFile.getInputStream(entry);

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		bufferedReader.close();
		inputStreamReader.close();

		return sb.toString();
	}
}
