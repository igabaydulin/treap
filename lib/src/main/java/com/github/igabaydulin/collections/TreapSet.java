package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class TreapSet<T extends Comparable<T>> implements Treap<T>, Set<T> {

  private final Random random;
  private Node<T> root;

  public TreapSet(long seed) {
    this.random = new Random(seed);
  }

  public TreapSet() {
    this.random = new Random();
  }

  private TreapSet(Node<T> node) {
    this();
    this.root = node;
  }

  @Override
  public T get(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException();
    }

    Node<T> currentNode = root;
    while (true) {
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
  public boolean add(T value) {
    return add(value, random.nextDouble());
  }

  @Override
  public boolean add(T value, double priority) {
    int size = size();
    if (isEmpty()) {
      root = new Node<>(value, priority);
    } else {
      root = root.add(value, priority);
    }

    return size < size();
  }

  @Override
  public boolean addBack(T[] values, double[] priorities) {
    if (values.length == 0) {
      return false;
    }

    int index = 0;
    Node<T> back;
    if (root == null) {
      add(values[0], priorities[0]);
      back = root;
      ++index;
    } else {
      back = root;
      while (back.right != null) {
        back = back.right;
      }
    }

    while (index < values.length) {
      if (back.priority > priorities[index]) {
        back.setRight(new Node<>(values[index], priorities[index]));
        back = back.right;
      } else {
        while (back.parent != null && back.priority < priorities[index]) {
          back = back.parent;
        }

        if (back.parent == null) {
          root = new Node<>(values[index], priorities[index], back, null);
        } else {
          back.parent.setRight(new Node<>(values[index], priorities[index], back, null));
        }
      }
      ++index;
    }

    return true;
  }

  @Override
  public boolean addBack(T[] values) {
    double[] priorities = new double[values.length];
    for (int i = 0; i < priorities.length; ++i) {
      priorities[i] = random.nextDouble();
    }

    return addBack(values, priorities);
  }

  // TODO: #addBack and #addFront looks very familiar
  //       should implement #push(T[], double[], boolean asc)
  @Override
  public boolean addFront(T[] values, double[] priorities) {
    if (values.length == 0) {
      return false;
    }

    int index = 0;
    Node<T> back;
    if (root == null) {
      add(values[0], priorities[0]);
      back = root;
      ++index;
    } else {
      back = root;
      while (back.left != null) {
        back = back.left;
      }
    }

    while (index < values.length) {
      if (back.priority > priorities[index]) {
        back.setLeft(new Node<>(values[index], priorities[index]));
        back = back.left;
      } else {
        while (back.parent != null && back.priority < priorities[index]) {
          back = back.parent;
        }

        if (back.parent == null) {
          root = new Node<>(values[index], priorities[index], null, back);
        } else {
          back.parent.setLeft(new Node<>(values[index], priorities[index], null, back));
        }
      }
      ++index;
    }

    return true;
  }

  @Override
  public boolean addFront(T[] values) {
    double[] priorities = new double[values.length];
    for (int i = 0; i < priorities.length; ++i) {
      priorities[i] = random.nextDouble();
    }

    return addFront(values, priorities);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    int currentSize = size();
    c.forEach(this::add);

    return currentSize != size();
  }

  @Override
  public boolean contains(T value) {
    if (isEmpty()) {
      return false;
    }

    return root.contains(value);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object value) {
    try {
      return contains((T) value);
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return c.stream().map(this::contains).reduce((op1, op2) -> op1 && op2).orElse(true);
  }

  @Override
  public boolean delete(T value) {
    if (isEmpty()) {
      return false;
    }

    int size = size();
    root = root.delete(value);

    return size > size();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object value) {
    try {
      return delete((T) value);
    } catch (ClassCastException ex) {
      return false;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll(Collection<?> c) {
    int currentSize = size();
    c.forEach(it -> delete((T) it));

    return currentSize != size();
  }

  // TODO: Always returns true, should be fixed to return true if any element were removed only
  @Override
  @SuppressWarnings("unchecked")
  public boolean retainAll(Collection<?> c) {
    root = null;
    c.forEach(it -> add((T) it));
    return true;
  }

  @Override
  public void clear() {
    root = null;
  }

  @Override
  public boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right) {
    if (isEmpty()) {
      left.set(null);
      right.set(null);
      return false;
    }

    Reference<Node<T>> leftNode = new Reference<>();
    Reference<Node<T>> rightNode = new Reference<>();

    boolean contains = root.split(value, leftNode, rightNode);

    left.set(new TreapSet<>(leftNode.get()));
    right.set(new TreapSet<>(rightNode.get()));

    return contains;
  }

  /**
   * @throws ClassCastException if right is not {@link TreapSet}
   * @see Treap#merge(Treap)
   */
  @Override
  public Treap<T> merge(Treap<T> right) {
    return new TreapSet<>(Node.merge(this.root, ((TreapSet<T>) right).root));
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

  @Override
  public boolean isEmpty() {
    return root == null;
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
    Node<T> root = this.root;
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

  public static class Node<T extends Comparable<T>> {

    private T value;
    private double priority;

    private Node<T> left;
    private Node<T> right;

    private Node<T> parent;

    private int size;
    private int height;

    Node(T value, double priority) {
      this.value = value;
      this.priority = priority;
      this.size = 1;
      this.height = 1;
    }

    Node(T value, double priority, Node<T> left, Node<T> right) {
      this(value, priority);
      setLeft(left);
      setRight(right);
    }

    private boolean contains(T value) {
      if (value.equals(this.getValue())) {
        return true;
      }

      if (value.compareTo(this.getValue()) > 0 && Objects.nonNull(getRight())) {
        return getRight().contains(value);
      } else if (Objects.nonNull(getLeft())) {
        return getLeft().contains(value);
      }

      return false;
    }

    private void updateParentInfo(Node<T> node) {
      while (Objects.nonNull(node.getParent())) {
        node.getParent().updateInfo();
        node = node.getParent();
      }
    }

    Node<T> add(T value, double priority) {
      Node<T> node = this;

      while (node.getPriority() > priority) {
        if (node.getValue().compareTo(value) == 0) {
          return this;
        } else if (node.getValue().compareTo(value) > 0) {
          if (Objects.isNull(node.getLeft())) {
            node.setLeft(new Node<>(value, priority));
            updateParentInfo(node);
            return this;
          }

          node = node.left;
        } else {
          if (Objects.isNull(node.getRight())) {
            node.setRight(new Node<>(value, priority));
            updateParentInfo(node);
            return this;
          }

          node = node.right;
        }
      }

      while (true) {
        if (node.getValue().compareTo(value) == 0) {
          return this;
        } else {
          if (node.getValue().compareTo(value) > 0) {
            if (Objects.isNull(node.getLeft())) {
              Node<T> left = new Node<>(value, priority);
              node.setLeft(left);
              node = left;
              break;
            } else {
              node = node.left;
            }
          } else {
            if (Objects.isNull(node.getRight())) {
              Node<T> right = new Node<>(value, priority);
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
        Node<T> parent = node.getParent();

        if (node.getPriority() > parent.getPriority()) {
          node.setParent(parent.getParent());
          if (node.getValue().compareTo(parent.getValue()) < 0) {
            parent.setLeft(node.getRight());
            node.setRight(parent);
          } else {
            parent.setRight(node.getLeft());
            node.setLeft(parent);
          }

          if (Objects.nonNull(node.getParent())) {
            if (node.getParent().getValue().compareTo(node.getValue()) > 0) {
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
      return node;
    }

    Node<T> delete(T value) {
      if (Objects.equals(value, this.getValue())) {
        return merge(this.getLeft(), this.getRight());
      } else if (value.compareTo(this.getValue()) > 0 && Objects.nonNull(this.getRight())) {
        setRight(getRight().delete(value));
      } else if (Objects.nonNull(this.getLeft())) {
        setLeft(getLeft().delete(value));
      }

      return this;
    }

    static <T extends Comparable<T>> Node<T> merge(Node<T> left, Node<T> right) {
      if (Objects.isNull(left)) {
        return right;
      } else if (Objects.isNull(right)) {
        return left;
      }

      if (left.getPriority() > right.getPriority()) {
        return new Node<>(left.getValue(), left.getPriority(), left.getLeft(), merge(left.getRight(), right));
      } else {
        return new Node<>(right.getValue(), right.getPriority(), merge(left, right.getLeft()), right.getRight());
      }
    }

    boolean split(T value, Reference<Node<T>> leftRef, Reference<Node<T>> rightRef, boolean keepValue) {
      if (value.compareTo(this.getValue()) > 0 || (keepValue && value.compareTo(this.getValue()) == 0)) {
        if (Objects.isNull(this.getRight())) {
          leftRef.set(this);
          return false;
        } else {
          Reference<Node<T>> leftRight = new Reference<>();
          Reference<Node<T>> right = new Reference<>();

          boolean result = this.getRight().split(value, leftRight, right, keepValue);
          leftRef.set(new Node<>(this.getValue(), this.getPriority(), this.getLeft(), leftRight.get()));
          rightRef.set(right.get());
          return result;
        }
      } else if (value.compareTo(this.getValue()) < 0) {
        if (Objects.isNull(this.getLeft())) {
          rightRef.set(this);
          return false;
        } else {
          Reference<Node<T>> left = new Reference<>();
          Reference<Node<T>> rightLeft = new Reference<>();

          boolean result = this.getLeft().split(value, left, rightLeft, keepValue);
          leftRef.set(left.get());
          rightRef.set(new Node<>(this.getValue(), this.getPriority(), rightLeft.get(), this.getRight()));
          return result;
        }
      } else {
        leftRef.set(this.getLeft());
        rightRef.set(this.getRight());
        return true;
      }
    }

    boolean split(T value, Reference<Node<T>> leftRef, Reference<Node<T>> rightRef) {
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

    public T getValue() {
      return value;
    }

    public double getPriority() {
      return priority;
    }

    Node<T> getLeft() {
      return left;
    }

    Node<T> getRight() {
      return right;
    }

    void setLeft(Node<T> left) {
      this.left = left;
      if (Objects.nonNull(left)) {
        getLeft().setParent(this);
      }
      updateInfo();
    }

    void setRight(Node<T> right) {
      this.right = right;
      if (Objects.nonNull(right)) {
        getRight().setParent(this);
      }

      updateInfo();
    }

    Node<T> getParent() {
      return parent;
    }

    void setParent(Node<T> parent) {
      this.parent = parent;
    }

    int getSize() {
      return size;
    }

    int getHeight() {
      return height;
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(toArray());
  }
}
