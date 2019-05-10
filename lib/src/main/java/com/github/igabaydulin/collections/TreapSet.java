package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.Treap.Node;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

public class TreapSet<T extends Comparable<T>> implements Set<T> {

  private Treap<T> treap;

  public TreapSet() {
    this(new Treap<>());
  }

  public TreapSet(long seed) {
    this(new Treap<>(seed));
  }

  public TreapSet(Treap<T> treap) {
    this.treap = treap;
  }

  public TreapSet(Collection<T> collection) {
    this.treap = new Treap<>();
    addAll(collection);
  }

  @Override
  public int size() {
    return treap.size();
  }

  @Override
  public boolean isEmpty() {
    return treap.size() > 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object o) {
    try {
      return treap.contains((T) o);
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Iterator<T> iterator() {
    return new Iterator<T>() {

      private Object[] array = TreapSet.this.toArray();
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < array.length;
      }

      @Override
      public T next() {
        return (T) array[index++];
      }
    };
  }

  @Override
  public Object[] toArray() {
    return toArray(new Object[0]);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T1> T1[] toArray(T1[] a) {
    Node<T> root = treap.getRoot();
    int index = 0;
    int size = size();
    T1[] array = (T1[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

    if (Objects.isNull(root)) {
      return array;
    }

    Stack<Node<T>> stack = new Stack<>();
    Node<T> node = root;
    while (!stack.empty() || Objects.nonNull(node)) {
      if (Objects.nonNull(node)) {
        stack.push(node);
        node = node.getLeft();
      } else {
        node = stack.pop();
        array[index++] = (T1) node.getValue();
        node = node.getRight();
      }
    }

    return array;
  }

  @Override
  public boolean add(T t) {
    return treap.add(t);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    return treap.delete((T) o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return c.stream().map(this::contains).reduce((op1, op2) -> op1 && op2).orElse(true);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    int currentSize = size();
    c.forEach(it -> treap.add(it));

    return currentSize != size();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean retainAll(Collection<?> c) {
    int currentSize = size();
    treap = new Treap<>();
    c.forEach(it -> treap.add((T) it));

    return currentSize != size();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll(Collection<?> c) {
    int currentSize = size();
    c.forEach(it -> treap.delete((T) it));

    return currentSize != size();
  }

  @Override
  public void clear() {
    treap = new Treap<>();
  }

  public Treap<T> getTreap() {
    return treap;
  }
}
