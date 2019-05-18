package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.Collection;
import java.util.Comparator;
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

    Node<K, V> currentNode = root;
    while (true) {
      @SuppressWarnings("ConstantConditions")
      int leftSize = currentNode.left == null ? 0 : currentNode.left.size;
      if (leftSize == index) {
        return currentNode.value;
      }
      currentNode = leftSize > index ? currentNode.left : currentNode.right;
      if (leftSize < index) {
        index -= leftSize + 1;
      }
    }
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
  public boolean split(K value, Reference<ValueTreap<K, V>> left, Reference<ValueTreap<K, V>> right, boolean keep) {
    if (isEmpty()) {
      left.set(null);
      right.set(null);
      return false;
    }

    Reference<Node<K, V>> leftNode = new Reference<>();
    Reference<Node<K, V>> rightNode = new Reference<>();

    boolean contains = root.split(value, leftNode, rightNode, keep);

    left.set(new TreapMap<>(leftNode.get()));
    right.set(new TreapMap<>(rightNode.get()));

    return contains;
  }

  @Override
  public boolean split(K value, Reference<ValueTreap<K, V>> left, Reference<ValueTreap<K, V>> right) {
    return split(value, left, right, true);
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

  // TODO: implement
  @Override
  public Collection<V> values() {
    throw new UnsupportedOperationException();
  }

  // TODO: implement
  @Override
  public Set<Entry<K, V>> entrySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Node<K, V> lowerEntry(K key) {
    Node<K, V> node = root;
    while (node != null) {
      if (node.compare(key) < 0) {
        return node;
      }
      node = node.left;
    }

    return null;
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
    while (node != null) {
      if (node.compare(key) <= 0) {
        return node;
      }
      node = node.left;
    }

    return null;
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
    while (node != null) {
      if (node.compare(key) >= 0) {
        return node;
      }
      node = node.left;
    }

    return null;
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
    while (node != null) {
      if (node.compare(key) > 0) {
        return node;
      }
      node = node.left;
    }

    return null;
  }

  @Override
  public K higherKey(K key) {
    Entry<K, V> entry = ceilingEntry(key);
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

  @Override
  public Node<K, V> pollFirstEntry() {
    Node<K, V> node = firstEntry();
    if (node != null) {
      node.delete(node.key, new Reference<>());
    }
    return node;
  }

  @Override
  public Node<K, V> pollLastEntry() {
    Node<K, V> node = lastEntry();
    if (node != null) {
      node.delete(node.key, new Reference<>());
    }
    return node;
  }

  @Override
  public NavigableMap<K, V> descendingMap() {
    throw new UnsupportedOperationException();
  }

  @Override
  public NavigableSet<K> navigableKeySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public NavigableSet<K> descendingKeySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public TreapMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
    TreapMap<K, V> from = tailMap(fromKey, fromInclusive);
    return from.headMap(toKey, toInclusive);
  }

  @Override
  public TreapMap<K, V> headMap(K toKey, boolean inclusive) {
    Node<K, V> node;
    if (inclusive) {
      node = floorEntry(toKey);
    } else {
      node = lowerEntry(toKey);
    }

    return new TreapMap<>(node, random, comparator);
  }

  @Override
  public TreapMap<K, V> tailMap(K fromKey, boolean inclusive) {
    Node<K, V> node;
    if (inclusive) {
      node = ceilingEntry(fromKey);
    } else {
      node = higherEntry(fromKey);
    }

    return new TreapMap<>(node, random, comparator);
  }

  @Override
  public SortedMap<K, V> subMap(K fromKey, K toKey) {
    return subMap(fromKey, true, toKey, false);
  }

  @Override
  public SortedMap<K, V> headMap(K toKey) {
    return headMap(toKey, false);
  }

  @Override
  public SortedMap<K, V> tailMap(K fromKey) {
    return tailMap(fromKey, true);
  }

  // TODO: Implement Comparator usage
  /**
   * Returns the comparator used to order the keys in this map, or {@code null} if this map uses the {@linkplain
   * Comparable natural ordering} of its keys.
   */
  @Override
  public Comparator<? super K> comparator() {
    return null;
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

    private static <K, V> void updateParentInfo(Node<K, V> node) {
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

    boolean split(K key, Reference<Node<K, V>> leftRef, Reference<Node<K, V>> rightRef, boolean keepValue) {
      int comparison = this.compare(key);
      if (comparison < 0 || (keepValue && comparison == 0)) {
        if (Objects.isNull(this.getRight())) {
          leftRef.set(this);
          return false;
        } else {
          Reference<Node<K, V>> leftRight = new Reference<>();
          Reference<Node<K, V>> right = new Reference<>();

          boolean result = this.getRight().split(key, leftRight, right, keepValue);
          leftRef.set(
              new Node<>(this.getKey(), this.value, this.getPriority(), this.getLeft(), leftRight.get(), comparator));
          rightRef.set(right.get());
          return result;
        }
      } else if (comparison > 0) {
        if (Objects.isNull(this.getLeft())) {
          rightRef.set(this);
          return false;
        } else {
          Reference<Node<K, V>> left = new Reference<>();
          Reference<Node<K, V>> rightLeft = new Reference<>();

          boolean result = this.getLeft().split(key, left, rightLeft, keepValue);
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

    boolean split(K value, Reference<Node<K, V>> leftRef, Reference<Node<K, V>> rightRef) {
      return split(value, leftRef, rightRef, true);
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
  }
}
