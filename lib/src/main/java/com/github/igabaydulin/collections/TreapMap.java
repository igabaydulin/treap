package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.stream.Stream;

public class TreapMap<K, V> implements ValueTreap<K, V> {

  private final Random random;
  private final Comparator<K> comparator;
  private Node<K, V> root;

  public TreapMap(long seed) {
    this.random = new Random(seed);
    this.comparator = null;
  }

  public TreapMap() {
    this.random = new Random();
    this.comparator = null;
  }

  private TreapMap(Node<K, V> node) {
    this();
    this.root = node;
  }

  TreapMap(Random random) {
    this.random = random;
    this.comparator = null;
  }

  private TreapMap(Node<K, V> node, Random random, Comparator<K> comparator) {
    this.random = random;
    this.comparator = comparator;
    this.root = node;
  }

  @Override
  public V getByIndex(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException();
    }

    if (root == null) {
      return null;
    }

    return root.getByIndex(index).value;
  }

  @Override
  public V put(K key, V value) {
    return put(key, value, random.nextDouble());
  }

  @Override
  public V put(K key, V value, double priority) {
    if (root == null) {
      root = new Node<>(key, value, priority, comparator);
      return null;
    } else {
      Node<K, V> node = root;

      while (node.getPriority() > priority) {
        int comparison = node.compare(key);
        if (comparison == 0) {
          V previousValue = node.value;
          node.value = value;
          return previousValue;
        } else if (comparison > 0) {
          if (Objects.isNull(node.getLeft())) {
            node.setLeft(new Node<>(key, value, priority, comparator));
            Node.updateParentInfo(node);
            return null;
          }

          node = node.left;
        } else {
          if (Objects.isNull(node.getRight())) {
            node.setRight(new Node<>(key, value, priority, comparator));
            Node.updateParentInfo(node);
            return null;
          }

          node = node.right;
        }
      }

      while (true) {
        int comparison = node.compare(key);
        if (comparison == 0) {
          V previousValue = node.value;
          node.value = value;
          return previousValue;
        } else {
          if (comparison > 0) {
            if (Objects.isNull(node.getLeft())) {
              Node<K, V> left = new Node<>(key, value, priority, comparator);
              node.setLeft(left);
              node = left;
              break;
            } else {
              node = node.left;
            }
          } else {
            if (Objects.isNull(node.getRight())) {
              Node<K, V> right = new Node<>(key, value, priority, comparator);
              node.setRight(right);
              node = right;
              break;
            } else {
              node = node.right;
            }
          }
        }
      }

      while (Objects.nonNull(node.getParent())) {
        Node<K, V> parent = node.getParent();

        if (node.getPriority() > parent.getPriority()) {
          node.setParent(parent.getParent());
          if (node.compare(parent.getKey()) < 0) {
            parent.setLeft(node.getRight());
            node.setRight(parent);
          } else {
            parent.setRight(node.getLeft());
            node.setLeft(parent);
          }

          if (Objects.nonNull(node.getParent())) {
            if (node.getParent().compare(node.getKey()) > 0) {
              node.getParent().setLeft(node);
            } else {
              node.getParent().setRight(node);
            }
          }
        } else {
          node.updateInfo();
          node = parent;
        }
      }

      node.updateInfo();
      root = node;
      return null;
    }
  }

  @Override
  public boolean putBack(K[] keys, V[] values, double[] priorities) {
    if (keys.length == 0) {
      return false;
    }

    int index = 0;
    Node<K, V> back;
    if (root == null) {
      put(keys[0], values[0], priorities[0]);
      back = root;
      ++index;
    } else {
      back = root;
      while (back.right != null) {
        back = back.right;
      }
    }

    while (index < keys.length) {
      if (back.priority > priorities[index]) {
        back.setRight(new Node<>(keys[index], values[index], priorities[index], comparator));
        back = back.right;
      } else {
        while (back.parent != null && back.priority < priorities[index]) {
          back = back.parent;
        }

        if (back.parent == null) {
          root = new Node<>(keys[index], values[index], priorities[index], back, null, comparator);
        } else {
          back.parent.setRight(new Node<>(keys[index], values[index], priorities[index], back, null, comparator));
        }
      }
      ++index;
    }

    return true;
  }

  @Override
  public boolean putBack(K[] keys, V[] values) {
    double[] priorities = new double[keys.length];
    for (int i = 0; i < priorities.length; ++i) {
      priorities[i] = random.nextDouble();
    }

    return putBack(keys, values, priorities);
  }

  @Override
  public boolean putFront(K[] keys, V[] values, double[] priorities) {
    if (keys.length == 0) {
      return false;
    }

    int index = 0;
    Node<K, V> back;
    if (root == null) {
      put(keys[0], values[0], priorities[0]);
      back = root;
      ++index;
    } else {
      back = root;
      while (back.left != null) {
        back = back.left;
      }
    }

    while (index < keys.length) {
      if (back.priority > priorities[index]) {
        back.setLeft(new Node<>(keys[index], values[index], priorities[index], comparator));
        back = back.left;
      } else {
        while (back.parent != null && back.priority < priorities[index]) {
          back = back.parent;
        }

        if (back.parent == null) {
          root = new Node<>(keys[index], values[index], priorities[index], null, back, comparator);
        } else {
          back.parent.setLeft(new Node<>(keys[index], values[index], priorities[index], null, back, comparator));
        }
      }
      ++index;
    }

    return true;
  }

  @Override
  public boolean putFront(K[] keys, V[] values) {
    double[] priorities = new double[keys.length];
    for (int i = 0; i < priorities.length; ++i) {
      priorities[i] = random.nextDouble();
    }

    return putFront(keys, values, priorities);
  }

  @Override
  public boolean contains(K value) {
    return get(value) != null;
  }

  @Override
  public V get(Object key) {
    if (isEmpty()) {
      return null;
    }

    //noinspection unchecked
    return root.get((K) key);
  }

  @Override
  public V remove(Object key) {
    if (isEmpty()) {
      return null;
    }

    Reference<V> removedValue = new Reference<>();
    //noinspection unchecked
    root = root.delete((K) key, removedValue);

    return removedValue.get();
  }

  @Override
  public boolean split(
      K value,
      Reference<ValueTreap<K, V>> left,
      Reference<ValueTreap<K, V>> right,
      boolean inclusive,
      boolean inclusiveLeft) {
    if (isEmpty()) {
      left.set(null);
      right.set(null);
      return false;
    }

    Reference<Node<K, V>> leftNode = new Reference<>();
    Reference<Node<K, V>> rightNode = new Reference<>();

    boolean contains = root.split(value, leftNode, rightNode, inclusive, inclusiveLeft);

    left.set(new TreapMap<>(leftNode.get()));
    right.set(new TreapMap<>(rightNode.get()));

    return contains;
  }

  @Override
  public TreapMap<K, V> merge(ValueTreap<K, V> right) {
    return new TreapMap<>(Node.merge(this.root, ((TreapMap<K, V>) right).root));
  }

  @Override
  public int size() {
    if (Objects.isNull(root)) {
      return 0;
    }

    return root.getSize();
  }

  @Override
  public int height() {
    if (Objects.isNull(root)) {
      return 0;
    }

    return root.getHeight();
  }

  Node<K, V> getRoot() {
    return root;
  }

  Random getRandom() {
    return random;
  }

  @Override
  public boolean isEmpty() {
    return root == null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean containsKey(Object key) {
    return contains((K) key);
  }

  @Override
  public boolean containsValue(Object value) {
    if (root == null) {
      return false;
    }

    Queue<Node<K, V>> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      Node<K, V> node = queue.poll();
      if (Objects.equals(node.getValue(), value)) {
        return true;
      }
      if (node.left != null) {
        queue.add(node.left);
      }

      if (node.right != null) {
        queue.add(node.right);
      }
    }

    return false;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    m.forEach(this::put);
  }

  @Override
  public void clear() {
    root = null;
  }

  @Override
  public Set<K> keySet() {
    return navigableKeySet();
  }

  @Override
  public Collection<V> values() {
    return new Values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return new EntrySet();
  }

  @Override
  public Node<K, V> lowerEntry(K key) {
    Node<K, V> node = root;
    Node<K, V> result = null;
    while (node != null) {
      if (node.compare(key) < 0) {
        result = node;
        node = node.right;
      } else {
        node = node.left;
      }
    }

    return result;
  }

  @Override
  public K lowerKey(K key) {
    Entry<K, V> entry = lowerEntry(key);
    if (entry != null) {
      return entry.getKey();
    }

    return null;
  }

  @Override
  public Node<K, V> floorEntry(K key) {
    Node<K, V> node = root;
    Node<K, V> result = null;
    while (node != null) {
      if (node.compare(key) == 0) {
        return node;
      } else if (node.compare(key) < 0) {
        result = node;
        node = node.right;
      } else {
        node = node.left;
      }
    }

    return result;
  }

  @Override
  public K floorKey(K key) {
    Entry<K, V> entry = floorEntry(key);
    if (entry != null) {
      return entry.getKey();
    }

    return null;
  }

  @Override
  public Node<K, V> ceilingEntry(K key) {
    Node<K, V> node = root;
    Node<K, V> result = null;
    while (node != null) {
      if (node.compare(key) == 0) {
        return node;
      } else if (node.compare(key) > 0) {
        result = node;
        node = node.left;
      } else {
        node = node.right;
      }
    }

    return result;
  }

  @Override
  public K ceilingKey(K key) {
    Entry<K, V> entry = ceilingEntry(key);
    if (entry != null) {
      return entry.getKey();
    }

    return null;
  }

  @Override
  public Node<K, V> higherEntry(K key) {
    Node<K, V> node = root;
    Node<K, V> result = null;
    while (node != null) {
      if (node.compare(key) > 0) {
        result = node;
        node = node.left;
      } else {
        node = node.right;
      }
    }

    return result;
  }

  @Override
  public K higherKey(K key) {
    Entry<K, V> entry = higherEntry(key);
    if (entry != null) {
      return entry.getKey();
    }

    return null;
  }

  @Override
  public Node<K, V> firstEntry() {
    if (root == null) {
      return null;
    }

    Node<K, V> node = root;
    while (node.left != null) {
      node = node.left;
    }

    return node;
  }

  @Override
  public Node<K, V> lastEntry() {
    if (root == null) {
      return null;
    }

    Node<K, V> node = root;
    while (node.right != null) {
      node = node.right;
    }

    return node;
  }

  @SuppressWarnings("Duplicates")
  @Override
  public Node<K, V> pollFirstEntry() {
    Node<K, V> node = firstEntry();
    if (node != null) {
      if (node == root) {
        root = root.right;
      } else {
        node.parent.setLeft(node.right);
        Node.updateParentInfo(node.parent);
      }
    }
    return node;
  }

  @SuppressWarnings("Duplicates")
  @Override
  public Node<K, V> pollLastEntry() {
    Node<K, V> node = lastEntry();
    if (node != null) {
      if (node == root) {
        root = root.left;
      } else {
        node.parent.setRight(node.getLeft());
        Node.updateParentInfo(node.parent);
      }
    }
    return node;
  }

  @Override
  public NavigableMap<K, V> descendingMap() {
    return new DescendingMap();
  }

  @Override
  public NavigableSet<K> navigableKeySet() {
    return new KeySet();
  }

  @Override
  public NavigableSet<K> descendingKeySet() {
    return new DescendingKeySet();
  }

  @Override
  public TreapMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
    return tailMap(fromKey, fromInclusive).headMap(toKey, toInclusive);
  }

  @Override
  public TreapMap<K, V> headMap(K toKey, boolean inclusive) {
    Reference<ValueTreap<K, V>> left = new Reference<>();
    split(toKey, left, new Reference<>(), inclusive, true);

    return (TreapMap<K, V>) left.get();
  }

  @Override
  public TreapMap<K, V> tailMap(K fromKey, boolean inclusive) {
    Reference<ValueTreap<K, V>> right = new Reference<>();
    split(fromKey, new Reference<>(), right, inclusive, false);

    return (TreapMap<K, V>) right.get();
  }

  @Override
  public TreapMap<K, V> subMap(K fromKey, K toKey) {
    return subMap(fromKey, true, toKey, false);
  }

  @Override
  public TreapMap<K, V> headMap(K toKey) {
    return headMap(toKey, false);
  }

  @Override
  public TreapMap<K, V> tailMap(K fromKey) {
    return tailMap(fromKey, true);
  }

  /**
   * Returns the comparator used to order the keys in this map, or {@code null} if this map uses the {@linkplain
   * Comparable natural ordering} of its keys.
   */
  @Override
  public Comparator<? super K> comparator() {
    return comparator;
  }

  @Override
  public K firstKey() {
    if (root == null) {
      throw new NoSuchElementException();
    }

    Node<K, V> node = root;
    while (node.left != null) {
      node = node.left;
    }
    return node.key;
  }

  @Override
  public K lastKey() {
    if (root == null) {
      throw new NoSuchElementException();
    }

    Node<K, V> node = root;
    while (node.right != null) {
      node = node.right;
    }
    return node.key;
  }

  static class Node<K, V> implements Entry<K, V> {

    private Comparator<K> comparator;

    private K key;
    private V value;
    private double priority;

    private Node<K, V> left;
    private Node<K, V> right;

    private Node<K, V> parent;

    private int size;
    private int height;

    Node(K key, V value, double priority, Comparator<K> comparator) {
      this.comparator = comparator;
      this.key = key;
      this.value = value;
      this.priority = priority;
      this.size = 1;
      this.height = 1;
    }

    Node(K key, V value, double priority, Node<K, V> left, Node<K, V> right, Comparator<K> comparator) {
      this(key, value, priority, comparator);
      setLeft(left);
      setRight(right);
    }

    private int compare(K key) {
      if (comparator == null) {
        //noinspection unchecked
        return ((Comparable<? super K>) this.key).compareTo(key);
      }
      return comparator.compare(this.key, key);
    }

    private V get(K key) {
      if (key.equals(this.getKey())) {
        return this.value;
      }

      if (this.compare(key) < 0 && Objects.nonNull(getRight())) {
        return getRight().get(key);
      } else if (Objects.nonNull(getLeft())) {
        return getLeft().get(key);
      }

      return null;
    }

    Node<K, V> getByIndex(int index) {
      if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException();
      }

      Node<K, V> currentNode = this;
      while (true) {
        @SuppressWarnings("ConstantConditions")
        int leftSize = currentNode.left == null ? 0 : currentNode.left.size;
        if (leftSize == index) {
          return currentNode;
        }
        currentNode = leftSize > index ? currentNode.left : currentNode.right;
        if (leftSize < index) {
          index -= leftSize + 1;
        }
      }
    }

    static <K, V> void updateParentInfo(Node<K, V> node) {
      while (Objects.nonNull(node.getParent())) {
        node.getParent().updateInfo();
        node = node.getParent();
      }
    }

    Node<K, V> delete(K key, Reference<V> removedValue) {
      if (Objects.equals(key, this.getKey())) {
        removedValue.set(this.value);
        return merge(this.getLeft(), this.getRight());
      } else if (this.compare(key) < 0 && Objects.nonNull(this.getRight())) {
        setRight(getRight().delete(key, removedValue));
      } else if (Objects.nonNull(this.getLeft())) {
        setLeft(getLeft().delete(key, removedValue));
      }

      return this;
    }

    static <K, V> Node<K, V> merge(Node<K, V> left, Node<K, V> right) {
      if (Objects.isNull(left)) {
        return right;
      } else if (Objects.isNull(right)) {
        return left;
      }

      if (left.getPriority() > right.getPriority()) {
        return new Node<>(
            left.getKey(),
            left.value,
            left.getPriority(),
            left.getLeft(),
            merge(left.getRight(), right),
            left.comparator);
      } else {
        return new Node<>(
            right.getKey(),
            right.value,
            right.getPriority(),
            merge(left, right.getLeft()),
            right.getRight(),
            left.comparator);
      }
    }

    boolean split(
        K key,
        Reference<Node<K, V>> leftRef,
        Reference<Node<K, V>> rightRef,
        boolean inclusive,
        boolean leftInclusive) {
      int comparison = this.compare(key);
      if (comparison < 0 || (inclusive && leftInclusive && comparison == 0)) {
        if (Objects.isNull(this.getRight())) {
          leftRef.set(this);
          return false;
        } else {
          Reference<Node<K, V>> leftRight = new Reference<>();
          Reference<Node<K, V>> right = new Reference<>();

          boolean result = this.getRight().split(key, leftRight, right, inclusive, leftInclusive);
          leftRef.set(
              new Node<>(this.getKey(), this.value, this.getPriority(), this.getLeft(), leftRight.get(), comparator));
          rightRef.set(right.get());
          return result;
        }
      } else if (comparison > 0 || inclusive) {
        if (Objects.isNull(this.getLeft())) {
          rightRef.set(this);
          return false;
        } else {
          Reference<Node<K, V>> left = new Reference<>();
          Reference<Node<K, V>> rightLeft = new Reference<>();

          boolean result = this.getLeft().split(key, left, rightLeft, inclusive, leftInclusive);
          leftRef.set(left.get());
          rightRef.set(
              new Node<>(this.getKey(), this.value, this.getPriority(), rightLeft.get(), this.getRight(), comparator));
          return result;
        }
      } else {
        leftRef.set(this.getLeft());
        rightRef.set(this.getRight());
        return true;
      }
    }

    private void updateInfo() {
      if (Objects.nonNull(left) && Objects.nonNull(right)) {
        this.size = left.getSize() + right.getSize() + 1;
        this.height = Math.max(left.getHeight(), right.getHeight()) + 1;
      } else if (Objects.nonNull(left)) {
        this.size = left.getSize() + 1;
        this.height = left.getHeight() + 1;
      } else if (Objects.nonNull(right)) {
        this.size = right.getSize() + 1;
        this.height = right.getHeight() + 1;
      } else {
        this.size = 1;
        this.height = 1;
      }
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V value) {
      return null;
    }

    double getPriority() {
      return priority;
    }

    Node<K, V> getLeft() {
      return left;
    }

    Node<K, V> getRight() {
      return right;
    }

    void setLeft(Node<K, V> left) {
      this.left = left;
      if (Objects.nonNull(left)) {
        getLeft().setParent(this);
      }
      updateInfo();
    }

    void setRight(Node<K, V> right) {
      this.right = right;
      if (Objects.nonNull(right)) {
        getRight().setParent(this);
      }

      updateInfo();
    }

    Node<K, V> getParent() {
      return parent;
    }

    void setParent(Node<K, V> parent) {
      this.parent = parent;
    }

    int getSize() {
      return size;
    }

    int getHeight() {
      return height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node<?, ?> node = (Node<?, ?>) o;
      return Objects.equals(key, node.key) && Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key);
    }
  }

  private class Values extends AbstractCollection<V> {

    @Override
    public Iterator<V> iterator() {
      return new ValueIterator();
    }

    @Override
    public int size() {
      return TreapMap.this.size();
    }
  }

  private class DescendingValues extends AbstractCollection<V> {

    @Override
    public Iterator<V> iterator() {
      return new DescendingValueIterator();
    }

    @Override
    public int size() {
      return TreapMap.this.size();
    }
  }

  private class EntryIterator implements Iterator<Entry<K, V>> {
    Node<K, V> lastElement;
    int visited = 0;

    @Override
    public boolean hasNext() {
      return size() > 0 && (visited < size());
    }

    @Override
    public Node<K, V> next() {
      lastElement = root.getByIndex(visited++);
      return lastElement;
    }
  }

  private class DescendingEntryIterator implements Iterator<Entry<K, V>> {
    Node<K, V> lastElement;
    int visited = 0;

    @Override
    public boolean hasNext() {
      return size() > 0 && (visited < size());
    }

    @Override
    public Node<K, V> next() {
      lastElement = root.getByIndex(size() - ++visited);
      return lastElement;
    }
  }

  private class ValueIterator implements Iterator<V> {

    private EntryIterator entryIterator = new EntryIterator();

    @Override
    public boolean hasNext() {
      return entryIterator.hasNext();
    }

    @Override
    public V next() {
      Node<K, V> next = entryIterator.next();
      if (next == null) {
        return null;
      }
      return next.value;
    }

    @Override
    public void remove() {
      TreapMap.this.remove(entryIterator.lastElement.key);
    }
  }

  private class DescendingValueIterator implements Iterator<V> {

    private DescendingEntryIterator entryIterator = new DescendingEntryIterator();

    @Override
    public boolean hasNext() {
      return entryIterator.hasNext();
    }

    @Override
    public V next() {
      Node<K, V> next = entryIterator.next();
      if (next == null) {
        return null;
      }
      return next.value;
    }

    @Override
    public void remove() {
      TreapMap.this.remove(entryIterator.lastElement.key);
    }
  }

  private class KeyIterator implements Iterator<K> {

    private EntryIterator entryIterator = new EntryIterator();

    @Override
    public boolean hasNext() {
      return entryIterator.hasNext();
    }

    @Override
    public K next() {
      Node<K, V> next = entryIterator.next();
      if (next == null) {
        return null;
      }
      return next.key;
    }
  }

  private class DescendingKeyIterator implements Iterator<K> {

    private DescendingEntryIterator nodeIterator = new DescendingEntryIterator();

    @Override
    public boolean hasNext() {
      return nodeIterator.hasNext();
    }

    @Override
    public K next() {
      Node<K, V> next = nodeIterator.next();
      if (next == null) {
        return null;
      }
      return next.key;
    }
  }

  private class KeySet implements NavigableSet<K> {

    @Override
    public K lower(K k) {
      return TreapMap.this.lowerKey(k);
    }

    @Override
    public K floor(K k) {
      return TreapMap.this.floorKey(k);
    }

    @Override
    public K ceiling(K k) {
      return TreapMap.this.ceilingKey(k);
    }

    @Override
    public K higher(K k) {
      return TreapMap.this.higherKey(k);
    }

    @Override
    public K pollFirst() {
      Node<K, V> node = pollFirstEntry();
      if (node == null) {
        return null;
      }
      return node.key;
    }

    @Override
    public K pollLast() {
      Node<K, V> node = pollLastEntry();
      if (node == null) {
        return null;
      }
      return node.key;
    }

    @Override
    public Iterator<K> iterator() {
      return new KeyIterator();
    }

    @Override
    public NavigableSet<K> descendingSet() {
      return descendingKeySet();
    }

    @Override
    public Iterator<K> descendingIterator() {
      return new DescendingKeyIterator();
    }

    @Override
    public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
      return subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
    }

    @Override
    public NavigableSet<K> headSet(K toElement, boolean inclusive) {
      return headMap(toElement, inclusive).navigableKeySet();
    }

    @Override
    public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
      return tailMap(fromElement, inclusive).navigableKeySet();
    }

    @Override
    public SortedSet<K> subSet(K fromElement, K toElement) {
      return subMap(fromElement, toElement).navigableKeySet();
    }

    @Override
    public SortedSet<K> headSet(K toElement) {
      return headMap(toElement).navigableKeySet();
    }

    @Override
    public SortedSet<K> tailSet(K fromElement) {
      return tailMap(fromElement).navigableKeySet();
    }

    @Override
    public Comparator<? super K> comparator() {
      return TreapMap.this.comparator;
    }

    @Override
    public K first() {
      return firstKey();
    }

    @Override
    public K last() {
      return lastKey();
    }

    @Override
    public int size() {
      return TreapMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return TreapMap.this.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
      return TreapMap.this.contains((K) o);
    }

    @Override
    public Object[] toArray() {
      return toArray(new Object[0]);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public <T> T[] toArray(T[] a) {
      int index = 0;
      int size = size();
      T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

      if (Objects.isNull(root)) {
        return array;
      }

      Stack<Node<K, V>> stack = new Stack<>();
      Node<K, V> node = root;
      while (!stack.empty() || Objects.nonNull(node)) {
        if (Objects.nonNull(node)) {
          stack.push(node);
          node = node.getLeft();
        } else {
          node = stack.pop();
          array[index++] = (T) node.getKey();
          node = node.getRight();
        }
      }

      return array;
    }

    /**
     * {@link KeySet} is tied to {@link TreapMap} therefore we cannot add elements to {@link KeySet} (otherwise value
     * would be undefined)
     *
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public boolean add(K k) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      return TreapMap.this.remove(o) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(Collection<?> c) {
      return c.stream().map(it -> TreapMap.this.contains((K) it)).reduce((bool1, bool2) -> bool1 && bool2).orElse(true);
    }

    /**
     * @see #add(Object)
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public boolean addAll(Collection<? extends K> c) {
      throw new UnsupportedOperationException();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean retainAll(Collection<?> c) {
      return Stream.of(toArray())
          .map(
              it -> {
                if (!c.contains(it)) {
                  return remove(it);
                }
                return false;
              })
          .reduce((bool1, bool2) -> bool1 || bool2)
          .orElse(false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return c.stream().map(this::remove).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @Override
    public void clear() {
      root = null;
    }
  }

  // TODO: Implement common parent for KeySet and DescendingKeySet
  private class DescendingKeySet implements NavigableSet<K> {

    @Override
    public K lower(K k) {
      return TreapMap.this.higherKey(k);
    }

    @Override
    public K floor(K k) {
      return TreapMap.this.ceilingKey(k);
    }

    @Override
    public K ceiling(K k) {
      return TreapMap.this.floorKey(k);
    }

    @Override
    public K higher(K k) {
      return TreapMap.this.lowerKey(k);
    }

    @Override
    public K pollFirst() {
      Node<K, V> node = pollLastEntry();
      if (node == null) {
        return null;
      }
      return node.key;
    }

    @Override
    public K pollLast() {
      Node<K, V> node = pollFirstEntry();
      if (node == null) {
        return null;
      }
      return node.key;
    }

    @Override
    public Iterator<K> iterator() {
      return new DescendingKeyIterator();
    }

    @Override
    public NavigableSet<K> descendingSet() {
      return new KeySet();
    }

    @Override
    public Iterator<K> descendingIterator() {
      return new KeyIterator();
    }

    @Override
    public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
      return subMap(toElement, toInclusive, fromElement, fromInclusive).descendingKeySet();
    }

    @Override
    public NavigableSet<K> headSet(K toElement, boolean inclusive) {
      return tailMap(toElement, inclusive).descendingKeySet();
    }

    @Override
    public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
      return headMap(fromElement, inclusive).descendingKeySet();
    }

    @Override
    public SortedSet<K> subSet(K fromElement, K toElement) {
      return subMap(fromElement, toElement).descendingKeySet();
    }

    @Override
    public SortedSet<K> headSet(K toElement) {
      return tailMap(toElement).descendingKeySet();
    }

    @Override
    public SortedSet<K> tailSet(K fromElement) {
      return headMap(fromElement).descendingKeySet();
    }

    @Override
    public Comparator<? super K> comparator() {
      if (comparator == null) {
        return null;
      }

      return comparator.reversed();
    }

    @Override
    public K first() {
      return lastKey();
    }

    @Override
    public K last() {
      return firstKey();
    }

    @Override
    public int size() {
      return TreapMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return TreapMap.this.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
      return TreapMap.this.contains((K) o);
    }

    @Override
    public Object[] toArray() {
      return toArray(new Object[0]);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public <T> T[] toArray(T[] a) {
      int index = 0;
      int size = size();
      T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

      if (Objects.isNull(root)) {
        return array;
      }

      Stack<Node<K, V>> stack = new Stack<>();
      Node<K, V> node = root;
      while (!stack.empty() || Objects.nonNull(node)) {
        if (Objects.nonNull(node)) {
          stack.push(node);
          node = node.getLeft();
        } else {
          node = stack.pop();
          array[index++] = (T) node.getValue();
          node = node.getRight();
        }
      }

      return array;
    }

    /**
     * {@link KeySet} is tied to {@link TreapMap} therefore we cannot add elements to {@link KeySet} (otherwise value
     * would be undefined)
     *
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public boolean add(K k) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      return TreapMap.this.remove(o) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(Collection<?> c) {
      return c.stream().map(it -> TreapMap.this.contains((K) it)).reduce((bool1, bool2) -> bool1 && bool2).orElse(true);
    }

    /**
     * @see #add(Object)
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public boolean addAll(Collection<? extends K> c) {
      throw new UnsupportedOperationException();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean retainAll(Collection<?> c) {
      return Stream.of(toArray())
          .map(
              it -> {
                if (!c.contains(it)) {
                  return remove(it);
                }
                return false;
              })
          .reduce((bool1, bool2) -> bool1 || bool2)
          .orElse(false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return c.stream().map(this::remove).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @Override
    public void clear() {
      root = null;
    }
  }

  private class DescendingMap implements NavigableMap<K, V> {

    @Override
    public Entry<K, V> lowerEntry(K key) {
      return TreapMap.this.higherEntry(key);
    }

    @Override
    public K lowerKey(K key) {
      return TreapMap.this.higherKey(key);
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
      return TreapMap.this.ceilingEntry(key);
    }

    @Override
    public K floorKey(K key) {
      return TreapMap.this.ceilingKey(key);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
      return TreapMap.this.floorEntry(key);
    }

    @Override
    public K ceilingKey(K key) {
      return TreapMap.this.floorKey(key);
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
      return TreapMap.this.lowerEntry(key);
    }

    @Override
    public K higherKey(K key) {
      return TreapMap.this.lowerKey(key);
    }

    @Override
    public Entry<K, V> firstEntry() {
      return TreapMap.this.lastEntry();
    }

    @Override
    public Entry<K, V> lastEntry() {
      return TreapMap.this.firstEntry();
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
      return TreapMap.this.pollLastEntry();
    }

    @Override
    public Entry<K, V> pollLastEntry() {
      return TreapMap.this.pollFirstEntry();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
      return TreapMap.this;
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
      return TreapMap.this.descendingKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
      return TreapMap.this.navigableKeySet();
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
      return TreapMap.this.subMap(toKey, toInclusive, fromKey, fromInclusive);
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
      return TreapMap.this.tailMap(toKey, inclusive);
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
      return TreapMap.this.headMap(fromKey, inclusive);
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
      return TreapMap.this.subMap(toKey, fromKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
      return TreapMap.this.tailMap(toKey, false);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
      return TreapMap.this.headMap(fromKey, true);
    }

    @Override
    public Comparator<? super K> comparator() {
      if (comparator == null) {
        return null;
      }
      return comparator.reversed();
    }

    @Override
    public K firstKey() {
      return TreapMap.this.lastKey();
    }

    @Override
    public K lastKey() {
      return TreapMap.this.firstKey();
    }

    @Override
    public Set<K> keySet() {
      return descendingKeySet();
    }

    @Override
    public Collection<V> values() {
      return new DescendingValues();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
      return new DescendingEntrySet();
    }

    @Override
    public int size() {
      return TreapMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return TreapMap.this.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
      return TreapMap.this.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
      return TreapMap.this.containsValue(value);
    }

    @Override
    public V get(Object key) {
      return TreapMap.this.get(key);
    }

    @Override
    public V put(K key, V value) {
      return TreapMap.this.put(key, value);
    }

    @Override
    public V remove(Object key) {
      return TreapMap.this.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
      TreapMap.this.putAll(m);
    }

    @Override
    public void clear() {
      TreapMap.this.clear();
    }
  }

  private class EntrySet implements Set<Entry<K, V>> {

    @Override
    public int size() {
      return TreapMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return TreapMap.this.isEmpty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean contains(Object o) {
      if (o instanceof Node) {
        Node node = (Node) o;
        V v = TreapMap.this.get(node.getKey());
        if (v == null) {
          return false;
        }

        return v.equals(node.value);
      } else {
        return false;
      }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
      return new EntryIterator();
    }

    @Override
    public Object[] toArray() {
      return toArray(new Object[0]);
    }

    @SuppressWarnings({"Duplicates", "unchecked"})
    @Override
    public <T> T[] toArray(T[] a) {
      int index = 0;
      int size = size();
      T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

      if (Objects.isNull(root)) {
        return array;
      }

      Stack<Node<K, V>> stack = new Stack<>();
      Node<K, V> node = root;
      while (!stack.empty() || Objects.nonNull(node)) {
        if (Objects.nonNull(node)) {
          stack.push(node);
          node = node.getLeft();
        } else {
          node = stack.pop();
          array[index++] = (T) node;
          node = node.getRight();
        }
      }

      return array;
    }

    @Override
    public boolean add(Entry<K, V> kvNode) {
      return TreapMap.this.put(kvNode.getKey(), kvNode.getValue()) == null;
    }

    @Override
    public boolean remove(Object o) {
      return TreapMap.this.remove(((Node) o).key) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      return c.stream().map(this::contains).reduce((bool1, bool2) -> bool1 && bool2).orElse(true);
    }

    @Override
    public boolean addAll(Collection<? extends Entry<K, V>> c) {
      return c.stream().map(this::add).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean retainAll(Collection<?> c) {
      Object[] values = toArray();
      return Stream.of(values)
          .map(
              it -> {
                if (!c.contains(it)) {
                  return remove(it);
                }
                return false;
              })
          .reduce((bool1, bool2) -> bool1 || bool2)
          .orElse(false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return c.stream().map(this::remove).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @Override
    public void clear() {
      TreapMap.this.clear();
    }
  }

  // TODO: Implement EntrySet and DescendingEntrySet parent
  private class DescendingEntrySet implements Set<Entry<K, V>> {

    @Override
    public int size() {
      return TreapMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return TreapMap.this.isEmpty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean contains(Object o) {
      if (o instanceof Node) {
        Node node = (Node) o;
        V v = TreapMap.this.get(node.getKey());
        if (v == null) {
          return false;
        }

        return v.equals(node.value);
      } else {
        return false;
      }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
      return new DescendingEntryIterator();
    }

    @Override
    public Object[] toArray() {
      return toArray(new Object[0]);
    }

    @SuppressWarnings({"Duplicates", "unchecked"})
    @Override
    public <T> T[] toArray(T[] a) {
      int index = 0;
      int size = size();
      T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

      if (Objects.isNull(root)) {
        return array;
      }

      Stack<Node<K, V>> stack = new Stack<>();
      Node<K, V> node = root;
      while (!stack.empty() || Objects.nonNull(node)) {
        if (Objects.nonNull(node)) {
          stack.push(node);
          node = node.getLeft();
        } else {
          node = stack.pop();
          array[index++] = (T) node;
          node = node.getRight();
        }
      }

      return array;
    }

    @Override
    public boolean add(Entry<K, V> kvNode) {
      return TreapMap.this.put(kvNode.getKey(), kvNode.getValue()) == null;
    }

    @Override
    public boolean remove(Object o) {
      return TreapMap.this.remove(o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      return c.stream().map(this::contains).reduce((bool1, bool2) -> bool1 && bool2).orElse(true);
    }

    @Override
    public boolean addAll(Collection<? extends Entry<K, V>> c) {
      return c.stream().map(this::add).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean retainAll(Collection<?> c) {
      Object[] values = toArray();
      return Stream.of(values)
          .map(
              it -> {
                if (!c.contains(it)) {
                  return remove(it);
                }
                return false;
              })
          .reduce((bool1, bool2) -> bool1 || bool2)
          .orElse(false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return c.stream().map(this::remove).reduce((bool1, bool2) -> bool1 || bool2).orElse(false);
    }

    @Override
    public void clear() {
      TreapMap.this.clear();
    }
  }
}
