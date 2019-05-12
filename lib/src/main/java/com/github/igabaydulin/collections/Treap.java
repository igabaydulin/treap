package com.github.igabaydulin.collections;

import java.util.Objects;
import java.util.Random;

public class Treap<T extends Comparable<T>> {

  private final Random random;
  private Node<T> root;

  public Treap() {
    this.random = new Random();
  }

  // For testing purpose only
  public Treap(long seed) {
    this.random = new Random(seed);
  }

  public Treap(Node<T> root) {
    this();
    this.root = root;
  }

  public boolean contains(T value) {
    if (Objects.isNull(root)) {
      return false;
    }

    return root.contains(value);
  }

  public boolean add(T value, double priority) {
    if (Objects.isNull(root)) {
      root = new Node<>(value, priority);
      return true;
    }

    Reference<Node<T>> nodeRef = new Reference<>(root);
    boolean result = Node.add(nodeRef, value, priority);

    if (result) {
      root = nodeRef.get();
    }

    return result;
  }

  public boolean add(T value) {
    return add(value, random.nextDouble());
  }

  public boolean delete(T value) {
    if (Objects.isNull(root)) {
      return false;
    }

    Reference<Node<T>> rootReference = new Reference<>(root);
    boolean result = Node.delete(rootReference, value);

    root = rootReference.get();

    return result;
  }

  public boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right) {
    if (Objects.isNull(root)) {
      return false;
    }

    Reference<Node<T>> leftNode = new Reference<>();
    Reference<Node<T>> rightNode = new Reference<>();

    boolean contains = root.split(value, leftNode, rightNode);

    left.set(new Treap<>(leftNode.get()));
    right.set(new Treap<>(rightNode.get()));

    return contains;
  }

  public static <T extends Comparable<T>> Treap<T> merge(Treap<T> left, Treap<T> right) {
    if (Objects.isNull(left)) {
      return right;
    } else if (Objects.isNull(right)) {
      return left;
    }

    return new Treap<>(Node.merge(left.getRoot(), right.getRoot()));
  }

  public int size() {
    if (Objects.isNull(root)) {
      return 0;
    }

    return root.getSize();
  }

  public int height() {
    if (Objects.isNull(root)) {
      return 0;
    }

    return root.getHeight();
  }

  public Node<T> getRoot() {
    return root;
  }

  public static class Node<T extends Comparable<T>> {

    private T value;
    private double priority;
    private int size;
    private int height;

    private Node<T> left;
    private Node<T> right;

    public Node(T value, double priority) {
      this.value = value;
      this.priority = priority;
      this.size = 1;
      this.height = 1;
    }

    public Node(T value, double priority, Node<T> left, Node<T> right) {
      this(value, priority);
      this.left = left;
      this.right = right;

      if (Objects.nonNull(left) && Objects.nonNull(right)) {
        this.size += left.getSize() + right.getSize();
        this.height += Math.max(left.getHeight(), right.getHeight());
      } else if (Objects.nonNull(left)) {
        this.size += left.getSize();
        this.height += left.getHeight();
      } else if (Objects.nonNull(right)) {
        this.size += right.getSize();
        this.height += right.getHeight();
      }
    }

    public boolean contains(T value) {
      if (value.equals(this.value)) {
        return true;
      }

      if (value.compareTo(this.value) > 0 && Objects.nonNull(right)) {
        return right.contains(value);
      } else if (Objects.nonNull(left)) {
        return left.contains(value);
      }

      return false;
    }

    private static <T extends Comparable<T>> void rotateRight(Reference<Node<T>> nodeRef) {
      Node<T> currentRoot = nodeRef.get();
      Node<T> newRight =
          new Node<>(
              currentRoot.getValue(),
              currentRoot.getPriority(),
              currentRoot.getLeft().getRight(),
              currentRoot.getRight());
      Node<T> newRoot =
          new Node<>(
              currentRoot.getLeft().getValue(),
              currentRoot.getLeft().getPriority(),
              currentRoot.getLeft().getLeft(),
              newRight);
      nodeRef.set(newRoot);
    }

    private static <T extends Comparable<T>> void rotateLeft(Reference<Node<T>> nodeRef) {
      Node<T> currentRoot = nodeRef.get();
      Node<T> newLeft =
          new Node<>(
              currentRoot.getValue(),
              currentRoot.getPriority(),
              currentRoot.getLeft(),
              currentRoot.getRight().getLeft());
      Node<T> newRoot =
          new Node<>(
              currentRoot.getRight().getValue(),
              currentRoot.getRight().getPriority(),
              newLeft,
              currentRoot.getRight().getRight());
      nodeRef.set(newRoot);
    }

    public static <T extends Comparable<T>> boolean add(Reference<Node<T>> nodeRef, T value, double priority) {
      Node<T> node = nodeRef.get();
      if (Objects.isNull(node)) {
        nodeRef.set(new Node<>(value, priority));
        return true;
      } else if (node.getValue().compareTo(value) > 0) {
        Reference<Node<T>> leftRef = new Reference<>(node.getLeft());
        boolean contains = add(leftRef, value, priority);
        nodeRef.set(new Node<>(node.getValue(), node.getPriority(), leftRef.get(), node.getRight()));
        if (leftRef.get().getPriority() > node.getPriority()) {
          rotateRight(nodeRef);
        }
        return contains;
      } else if (node.getValue().compareTo(value) < 0) {
        Reference<Node<T>> rightRef = new Reference<>(node.getRight());
        boolean contains = add(rightRef, value, priority);
        nodeRef.set(new Node<>(node.getValue(), node.getPriority(), node.getLeft(), rightRef.get()));
        if (rightRef.get().getPriority() > node.getPriority()) {
          rotateLeft(nodeRef);
        }
        return contains;
      } else {
        return false;
      }
    }

    public static <T extends Comparable<T>> boolean delete(Reference<Node<T>> node, T value) {
      if (Objects.equals(value, node.get().getValue())) {
        node.set(merge(node.get().getLeft(), node.get().getRight()));
        return true;
      } else if (value.compareTo(node.get().getValue()) > 0 && Objects.nonNull(node.get().getRight())) {
        Reference<Node<T>> right = new Reference<>(node.get().getRight());
        boolean result = delete(right, value);
        node.set(new Node<>(node.get().getValue(), node.get().getPriority(), node.get().getLeft(), right.get()));
        return result;
      } else if (Objects.nonNull(node.get().getLeft())) {
        Reference<Node<T>> left = new Reference<>(node.get().getLeft());
        boolean result = delete(left, value);
        node.set(new Node<>(node.get().getValue(), node.get().getPriority(), left.get(), node.get().getRight()));
        return result;
      }

      return false;
    }

    public static <T extends Comparable<T>> Node<T> merge(Node<T> left, Node<T> right) {
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

    public boolean split(T value, Reference<Node<T>> leftRef, Reference<Node<T>> rightRef, boolean keepValue) {
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

    public boolean split(T value, Reference<Node<T>> leftRef, Reference<Node<T>> rightRef) {
      return split(value, leftRef, rightRef, true);
    }

    public T getValue() {
      return value;
    }

    public double getPriority() {
      return priority;
    }

    public Node<T> getLeft() {
      return left;
    }

    public Node<T> getRight() {
      return right;
    }

    public int getSize() {
      return size;
    }

    public int getHeight() {
      return height;
    }
  }

  public static class Reference<T> {

    private T value;

    public Reference() {}

    public Reference(T value) {
      this.value = value;
    }

    public T get() {
      return value;
    }

    public void set(T value) {
      this.value = value;
    }
  }
}
