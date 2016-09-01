package com.lanyine.manifold.mybatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class DualHahshMap<K1, K2, V> implements IDualMap<K1, K2, V> {
	private final Map<K1, V> map1 = new HashMap<>();
	private final Map<K2, V> map2 = new HashMap<>();

	@Override
	public Map<K1, V> getMap1() {
		return Collections.unmodifiableMap(map1);
	}

	@Override
	public Map<K2, V> getMap2() {
		return Collections.unmodifiableMap(map2);
	}

	@Override
	public boolean isEmpty() {
		return map1.isEmpty();
	}

	@Override
	public int size() {
		return map1.size();
	}

	@Override
	public synchronized void put(K1 key1, K2 key2, V value) {
		map1.put(key1, value);
		map2.put(key2, value);
	}
}
