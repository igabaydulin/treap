package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.ValueTreap.Inclusion;
import com.github.igabaydulin.collections.utils.Reference;
import java.util.NavigableSet;

public interface Treap<T> extends NavigableSet<T> {

  T get(int index);

  boolean add(T value, double priority);

  boolean addBack(T[] values, double[] priorities);

  boolean addBack(T[] values);

  boolean addFront(T[] values, double[] priorities);

  boolean addFront(T[] values);

  boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right, Inclusion inclusion);

  /**
   * Merges two treaps into one
   *
   * <p>{@link ClassCastException} may be thrown by implementation if it does not support merging with another
   * implementation
   */
  Treap<T> merge(Treap<T> right);

  int height();
}
