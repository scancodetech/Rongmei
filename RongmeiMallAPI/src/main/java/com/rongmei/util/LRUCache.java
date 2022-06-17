package com.rongmei.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {

  private Map<K, V> map;
  private final int cacheSize;

  public LRUCache(int initialCapacity) {
    map = new LinkedHashMap<K, V>(initialCapacity, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
      }
    };
    this.cacheSize = initialCapacity;
  }

  public V get(K k) {
    return map.get(k);
  }

  public void put(K k, V v) {
    map.put(k, v);
  }

  public boolean containsKey(K k) {
    return map.containsKey(k);
  }
}
