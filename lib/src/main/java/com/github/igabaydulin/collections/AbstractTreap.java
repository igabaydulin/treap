package com.github.igabaydulin.collections;

import java.util.Objects;
import java.util.Random;

public abstract class AbstractTreap<T extends Comparable<T>> implements Treap<T> {

  private final Random random;
  private Node<T> root;

  AbstractTreap(long seed) {
    this.random = new Random(seed);
  }

  AbstractTreap() {
    this.random = new Random();
  }

  AbstractTreap(Node<T> node) {
    this.random = new Random();
    this.root = node;
  }

  @Override
  public boolean add(T value) {
    return add(value, random.nextDouble());
  }

  @Override
  public boolean isEmpty() {
    return Objects.isNull(root);
  }

  protected Node<T> getRoot() {
    return root;
  }

  void setRoot(Node<T> node) {
    this.root = node;
  }

  static class Node<T extends Comparable<T>> {

    private T value;
    private double priority;

    private Node<T> left;
    private Node<T> right;

    public Node(T value, double priority) {
      this.value = value;
      this.priority = priority;
    }

    public Node(T value, double priority, Node<T> left, Node<T> right) {
      this.value = value;
      this.priority = priority;
      this.left = left;
      this.right = right;
    }

    public Node<T> getLeft() {
      return left;
    }

    public void setLeft(Node<T> left) {
      this.left = left;
    }

    public Node<T> getRight() {
      return right;
    }

    public void setRight(Node<T> right) {
      this.right = right;
    }

    public T getValue() {
      return value;
    }

    public double getPriority() {
      return priority;
    }
  }
}
