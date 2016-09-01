package com.lanyine.manifold.mybatis;

import java.util.Map;

public interface IDualMap<K1, K2, V> {

	/**
	 * @return Unmodifiable version of underlying map1
	 */
	Map<K1, V> getMap1();

	/**
	 * @return Unmodifiable version of underlying map2
	 */
	Map<K2, V> getMap2();

	void put(K1 key1, K2 key2, V value);

	boolean isEmpty();

	int size();

}