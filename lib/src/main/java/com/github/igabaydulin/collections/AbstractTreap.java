package com.github.igabaydulin.collections;

import java.util.Random;

public abstract class AbstractTreap<T extends Comparable<T>> implements Treap<T> {

  private final Random random;

  AbstractTreap(long seed) {
    this.random = new Random(seed);
  }

  AbstractTreap() {
    this.random = new Random();
  }

  @Override
  public boolean add(T value) {
    return add(value, random.nextDouble());
  }

  static class Node<T extends Comparable<T>> {

    private T value;
    private double priority;

    Node(T value, double priority) {
      this.value = value;
      this.priority = priority;
    }

    public T getValue() {
      return value;
    }

    double getPriority() {
      return priority;
    }
  }
}
