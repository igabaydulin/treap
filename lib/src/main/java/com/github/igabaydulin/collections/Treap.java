package com.github.igabaydulin.collections;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Treap<T extends Comparable<T>> {

  private final Random random;
  private Node<T> root;

  public Treap() {
    this(0);
  }

  public Treap(long seed) {
    this.random = new Random(seed);
  }

  public boolean contains(T value) {
    if (Objects.isNull(root)) {
      return false;
    }

    return root.contains(value);
  }

  public boolean add(T value) {
    if (Objects.isNull(root)) {
      root = new Node<>(value, random.nextDouble());
      return true;
    }

    AtomicReference<Node<T>> nodeRef = new AtomicReference<>();
    boolean result = root.add(nodeRef, value, random.nextDouble());

    if (result) {
      root = nodeRef.get();
    }

    return result;
  }

  public boolean delete(T value) {
    if (Objects.isNull(root)) {
      return false;
    }

    AtomicReference<Node<T>> rootReference = new AtomicReference<>(root);
    boolean result = Node.delete(rootReference, value);

    root = rootReference.get();

    return result;
  }

  public int size() {
    if (Objects.isNull(root)) {
      return 0;
    }

    return root.getSize();
  }

  public Node<T> getRoot() {
    return root;
  }

  public static class Node<T extends Comparable<T>> {

    private T value;
    private double priority;
    private int size;

    private Node<T> left;
    private Node<T> right;

    public Node(T value, double priority) {
      this.value = value;
      this.priority = priority;
      this.size = 1;
    }

    public Node(T value, double priority, Node<T> left, Node<T> right) {
      this(value, priority);
      this.left = left;
      this.right = right;

      if (Objects.nonNull(left)) {
        this.size += left.getSize();
      }

      if (Objects.nonNull(right)) {
        this.size += right.getSize();
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

    public boolean add(AtomicReference<Node<T>> node, T value, double priority) {
      if (priority < this.getPriority()) {
        if (this.getValue().compareTo(value) < 0) {
          if (Objects.isNull(this.getRight())) {
            node.set(new Node<>(this.value, this.priority, this.left, new Node<>(value, priority)));
            return true;
          }

          AtomicReference<Node<T>> right = new AtomicReference<>(this.getRight());
          boolean isAdded = this.getRight().add(right, value, priority);
          node.set(new Node<>(this.value, this.priority, this.left, right.get()));
          return isAdded;
        } else if (this.getValue().compareTo(value) > 0) {
          if (Objects.isNull(this.getLeft())) {
            node.set(new Node<>(this.value, this.priority, new Node<>(value, priority), this.right));
            return true;
          }

          AtomicReference<Node<T>> left = new AtomicReference<>(this.getLeft());
          boolean isAdded = this.getLeft().add(left, value, priority);
          node.set(new Node<>(this.value, this.priority, left.get(), this.right));
          return isAdded;
        } else {
          return false;
        }
      } else {
        if (this.getValue().compareTo(value) == 0) {
          return false;
        }

        AtomicReference<Node<T>> left = new AtomicReference<>();
        AtomicReference<Node<T>> right = new AtomicReference<>();
        split(value, left, right);

        node.set(new Node<>(value, priority, left.get(), right.get()));
        return true;
      }
    }

    public static <T extends Comparable<T>> boolean delete(AtomicReference<Node<T>> node, T value) {
      if (Objects.equals(value, node.get().getValue())) {
        node.set(merge(node.get().getLeft(), node.get().getRight()));
        return true;
      } else if (value.compareTo(node.get().getValue()) > 0 && Objects.nonNull(node.get().getRight())) {
        AtomicReference<Node<T>> right = new AtomicReference<>(node.get().getRight());
        boolean result = delete(right, value);
        node.set(new Node<>(node.get().getValue(), node.get().getPriority(), node.get().getLeft(), right.get()));
        return result;
      } else if (Objects.nonNull(node.get().getLeft())) {
        AtomicReference<Node<T>> left = new AtomicReference<>(node.get().getLeft());
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

    public void split(T value, AtomicReference<Node<T>> leftRef, AtomicReference<Node<T>> rightRef) {
      if (value.compareTo(this.getValue()) > 0) {
        if (Objects.isNull(this.getRight())) {
          leftRef.set(this);
        } else {
          AtomicReference<Node<T>> leftRight = new AtomicReference<>();
          AtomicReference<Node<T>> right = new AtomicReference<>();

          this.getRight().split(value, leftRight, right);
          leftRef.set(new Node<>(this.getValue(), this.getPriority(), this.getLeft(), leftRight.get()));
          rightRef.set(right.get());
        }
      } else if (value.compareTo(this.getValue()) < 0) {
        if (Objects.isNull(this.getLeft())) {
          rightRef.set(this);
        } else {
          AtomicReference<Node<T>> left = new AtomicReference<>();
          AtomicReference<Node<T>> rightLeft = new AtomicReference<>();

          this.getLeft().split(value, left, rightLeft);
          leftRef.set(left.get());
          rightRef.set(new Node<>(this.getValue(), this.getPriority(), rightLeft.get(), this.getRight()));
        }
      } else {
        leftRef.set(this.getLeft());
        rightRef.set(this.getRight());
      }
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
  }
}
