package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;

interface Treap<T extends Comparable<T>> {

  boolean contains(T value);

  boolean add(T value, double priority);

  boolean add(T value);

  boolean addBack(T[] values, double[] priorities);

  boolean addBack(T[] values);

  boolean addFront(T[] values, double[] priorities);

  boolean addFront(T[] values);

  boolean delete(T value);

  boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right);

  /**
   * Merges two treaps into one
   *
   * <p>{@link ClassCastException} may be thrown by implementation if it does not support merging with another
   * implementation
   */
  Treap<T> merge(Treap<T> right);

  int size();

  int height();

  boolean isEmpty();
}
