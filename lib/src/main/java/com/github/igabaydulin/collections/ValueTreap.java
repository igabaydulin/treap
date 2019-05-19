package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.NavigableMap;

public interface ValueTreap<K, V> extends NavigableMap<K, V> {

  V getByIndex(int index);

  boolean contains(K key);

  V put(K key, V value, double priority);

  boolean putBack(K[] keys, V[] values, double[] priorities);

  boolean putBack(K[] keys, V[] values);

  boolean putFront(K[] keys, V[] values, double[] priorities);

  boolean putFront(K[] keys, V[] values);

  boolean split(
      K key,
      Reference<ValueTreap<K, V>> left,
      Reference<ValueTreap<K, V>> right,
      boolean inclusive,
      boolean inclusiveLeft);

  ValueTreap<K, V> merge(ValueTreap<K, V> right);

  int height();
}
