package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.TreapMap.Node;
import com.github.igabaydulin.collections.ValueTreap.Inclusion;
import com.github.igabaydulin.collections.utils.Reference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.SortedSet;
import java.util.Stack;

public class TreapSet<K> implements Treap<K> {

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
  public boolean contains(Object value) {
    try {
      //noinspection SuspiciousMethodCalls
      return treapMap.get(value) != null;
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
  public boolean remove(Object value) {
    try {
      return treapMap.remove(value) != null;
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    int currentSize = size();
    c.forEach(this::remove);

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
  public boolean split(K value, Reference<Treap<K>> left, Reference<Treap<K>> right, Inclusion inclusion) {
    Reference<ValueTreap<K, K>> leftDictionary = new Reference<>();
    Reference<ValueTreap<K, K>> rightDictionary = new Reference<>();

    boolean contains = treapMap.split(value, leftDictionary, rightDictionary, inclusion);
    left.set(new TreapSet<>((TreapMap<K, K>) leftDictionary.get()));
    right.set(new TreapSet<>((TreapMap<K, K>) rightDictionary.get()));

    return contains;
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
  public K lower(K k) {
    return treapMap.lowerKey(k);
  }

  @Override
  public K floor(K k) {
    return treapMap.floorKey(k);
  }

  @Override
  public K ceiling(K k) {
    return treapMap.ceilingKey(k);
  }

  @Override
  public K higher(K k) {
    return treapMap.higherKey(k);
  }

  @Override
  public K pollFirst() {
    return treapMap.pollFirstEntry().getKey();
  }

  @Override
  public K pollLast() {
    return treapMap.pollLastEntry().getKey();
  }

  @Override
  public NavigableSet<K> descendingSet() {
    return treapMap.descendingKeySet();
  }

  @Override
  public Iterator<K> descendingIterator() {
    return treapMap.new KeyIterator();
  }

  @Override
  public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
    return treapMap.subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
  }

  @Override
  public NavigableSet<K> headSet(K toElement, boolean inclusive) {
    return treapMap.headMap(toElement, inclusive).navigableKeySet();
  }

  @Override
  public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
    return treapMap.tailMap(fromElement, inclusive).navigableKeySet();
  }

  @Override
  public SortedSet<K> subSet(K fromElement, K toElement) {
    return treapMap.subMap(fromElement, toElement).navigableKeySet();
  }

  @Override
  public SortedSet<K> headSet(K toElement) {
    return treapMap.headMap(toElement).navigableKeySet();
  }

  @Override
  public SortedSet<K> tailSet(K fromElement) {
    return treapMap.tailMap(fromElement).navigableKeySet();
  }

  @Override
  public Comparator<? super K> comparator() {
    return treapMap.comparator();
  }

  @Override
  public K first() {
    return treapMap.firstKey();
  }

  @Override
  public K last() {
    return treapMap.lastKey();
  }

  @Override
  public String toString() {
    return "TreapSet{" + "array=" + Arrays.toString(toArray()) + '}';
  }
}
