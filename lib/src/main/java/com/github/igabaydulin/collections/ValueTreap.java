package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;

interface ValueTreap<K extends Comparable<K>, V> {

  V getByIndex(int index);

  V get(K key);

  boolean contains(K key);

  V put(K key, V value, double priority);

  V put(K key, V value);

  boolean putBack(K[] keys, V[] values, double[] priorities);

  boolean putBack(K[] keys, V[] values);

  boolean putFront(K[] keys, V[] values, double[] priorities);

  boolean putFront(K[] keys, V[] values);

  V remove(K key);

  boolean split(K key, Reference<ValueTreap<K, V>> left, Reference<ValueTreap<K, V>> right, boolean keep);

  boolean split(K key, Reference<ValueTreap<K, V>> left, Reference<ValueTreap<K, V>> right);

  ValueTreap<K, V> merge(ValueTreap<K, V> right);

  int size();

  int height();

  boolean isEmpty();
}
