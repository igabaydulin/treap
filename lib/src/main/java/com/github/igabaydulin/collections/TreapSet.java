package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.TreapMap.Node;
import com.github.igabaydulin.collections.utils.Reference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class TreapSet<K extends Comparable<K>> implements Treap<K> {

  private TreapMap<K, K> treapMap;

  public TreapSet() {
    this.treapMap = new TreapMap<>();
  }

  @SuppressWarnings("unused")
  public TreapSet(long seed) {
    this.treapMap = new TreapMap<>(seed);
  }

  private TreapSet(TreapMap<K, K> treapMap) {
    this.treapMap = treapMap;
  }

  @Override
  public K get(int index) {
    return treapMap.getByIndex(index);
  }

  @Override
  public boolean contains(K value) {
    return treapMap.get(value) != null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object value) {
    try {
      return contains((K) value);
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return c.stream().map(this::contains).reduce((op1, op2) -> op1 && op2).orElse(true);
  }

  @Override
  public boolean add(K value, double priority) {
    return treapMap.put(value, value, priority) == null;
  }

  @Override
  public boolean add(K value) {
    return treapMap.put(value, value) == null;
  }

  @Override
  public boolean addBack(K[] values, double[] priorities) {
    return treapMap.putBack(values, values, priorities);
  }

  @Override
  public boolean addBack(K[] values) {
    return treapMap.putBack(values, values);
  }

  @Override
  public boolean addFront(K[] values, double[] priorities) {
    return treapMap.putFront(values, values, priorities);
  }

  @Override
  public boolean addFront(K[] values) {
    return treapMap.putFront(values, values);
  }

  @Override
  public boolean addAll(Collection<? extends K> c) {
    int currentSize = size();
    c.forEach(this::add);

    return currentSize != size();
  }

  @Override
  public boolean delete(K value) {
    return treapMap.remove(value) != null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object value) {
    try {
      return delete((K) value);
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll(Collection<?> c) {
    int currentSize = size();
    c.forEach(it -> delete((K) it));

    return currentSize != size();
  }

  // TODO: Always returns true, should be fixed to return true if any element were removed only
  @Override
  @SuppressWarnings("unchecked")
  public boolean retainAll(Collection<?> c) {
    clear();
    c.forEach(it -> add((K) it));
    return true;
  }

  @Override
  public void clear() {
    treapMap = new TreapMap<>(treapMap.getRandom());
  }

  @Override
  public boolean split(K value, Reference<Treap<K>> left, Reference<Treap<K>> right, boolean keep) {
    Reference<ValueTreap<K, K>> leftDictionary = new Reference<>();
    Reference<ValueTreap<K, K>> rightDictionary = new Reference<>();

    boolean contains = treapMap.split(value, leftDictionary, rightDictionary, keep);
    left.set(new TreapSet<>((TreapMap<K, K>) leftDictionary.get()));
    right.set(new TreapSet<>((TreapMap<K, K>) rightDictionary.get()));

    return contains;
  }

  @Override
  public boolean split(K value, Reference<Treap<K>> left, Reference<Treap<K>> right) {
    return split(value, left, right, true);
  }

  @Override
  public Treap<K> merge(Treap<K> right) {
    return new TreapSet<>(treapMap.merge(((TreapSet<K>) right).treapMap));
  }

  @Override
  public int size() {
    return treapMap.size();
  }

  @Override
  public int height() {
    return treapMap.height();
  }

  @Override
  public boolean isEmpty() {
    return treapMap.isEmpty();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Iterator<K> iterator() {
    return new Iterator<K>() {

      private Object[] array = toArray();
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < array.length;
      }

      @Override
      public K next() {
        return (K) array[index++];
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
    Node<K, K> root = treapMap.getRoot();
    int index = 0;
    int size = size();
    T1[] array = (T1[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

    if (Objects.isNull(root)) {
      return array;
    }

    Stack<Node<K, K>> stack = new Stack<>();
    Node<K, K> node = root;
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
  public String toString() {
    return "TreapSet{" + "array=" + Arrays.toString(toArray()) + '}';
  }
}
