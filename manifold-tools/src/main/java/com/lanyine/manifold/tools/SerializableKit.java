package com.lanyine.manifold.tools;

import java.io.*;

/**
 * 序列化工具
 * 
 * @author shdow
 * 
 */
@SuppressWarnings("unchecked")
public class SerializableKit {
	/**
	 * 将对象序列化为byte[]
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		byte[] bytes = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
					bytes = baos.toByteArray();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return bytes;
	}

	/**
	 * 将byte[]反序列化为Object
	 * 
	 * @param bytes
	 * @return
	 */
	public static <T> T deserialize(byte[] bytes) {
		if(bytes == null || bytes.length == 0)return null;
		Object obj = null;
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return (T) obj;
	}
}
