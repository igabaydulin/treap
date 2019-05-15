package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;

public class TreapSet<T extends Comparable<T>> extends AbstractTreap<T> implements Set<T> {

  TreapSet(long seed) {
    super(seed);
  }

  TreapSet() {}

  private TreapSet(AbstractTreap.Node<T> node) {
    super(node);
  }

  private <R> R eval(R defaultResult, Supplier<R> supplier) {
    if (isEmpty()) {
      return defaultResult;
    }
    return supplier.get();
  }

  private boolean update(Runnable action) {
    return update(() -> false, action);
  }

  private boolean update(Runnable defaultAction, Runnable action) {
    return update(
        () -> {
          defaultAction.run();
          return true;
        },
        action);
  }

  private boolean update(Supplier<Boolean> defaultAction, Runnable action) {
    if (isEmpty()) {
      return defaultAction.get();
    }
    return checkUpdate(action);
  }

  private boolean update(Supplier<Boolean> defaultAction, Supplier<Boolean> action) {
    if (isEmpty()) {
      return defaultAction.get();
    }
    return action.get();
  }

  private boolean update(Supplier<Boolean> action) {
    return update(() -> false, action);
  }

  private boolean checkUpdate(Runnable runnable) {
    int currentSize = size();
    runnable.run();
    return currentSize < size();
  }

  @Override
  public boolean add(T value, double priority) {
    return update(() -> setRoot(new Node<>(value, priority)), () -> setRoot(getRoot().add(value, priority)));
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    int currentSize = size();
    c.forEach(this::add);

    return currentSize != size();
  }

  @Override
  public boolean contains(T value) {
    return eval(false, () -> getRoot().contains(value));
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
    return update(() -> setRoot(getRoot().delete(value)));
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
    setRoot(null);
    c.forEach(it -> add((T) it));
    return true;
  }

  @Override
  public void clear() {
    setRoot(null);
  }

  @Override
  public boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right) {
    return update(
        () -> {
          Reference<Node<T>> leftNode = new Reference<>();
          Reference<Node<T>> rightNode = new Reference<>();

          boolean contains = getRoot().split(value, leftNode, rightNode);

          left.set(new TreapSet<>(leftNode.get()));
          right.set(new TreapSet<>(rightNode.get()));

          return contains;
        });
  }

  /**
   * @throws ClassCastException if right is not {@link TreapSet}
   * @see Treap#merge(Treap)
   */
  @Override
  public Treap<T> merge(Treap<T> right) {
    return new TreapSet<>(Node.merge(this.getRoot(), ((TreapSet<T>) right).getRoot()));
  }

  @Override
  public int size() {
    return eval(0, () -> getRoot().getSize());
  }

  @Override
  public int height() {
    return eval(0, () -> getRoot().getHeight());
  }

  @Override
  public Node<T> getRoot() {
    return (Node<T>) super.getRoot();
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
    Node<T> root = getRoot();
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

  public static class Node<T extends Comparable<T>> extends AbstractTreap.Node<T> {

    private int size;
    private int height;

    private Node<T> parent;

    Node(T value, double priority) {
      super(value, priority);
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

          node = node.getLeft();
        } else {
          if (Objects.isNull(node.getRight())) {
            node.setRight(new Node<>(value, priority));
            updateParentInfo(node);
            return this;
          }

          node = node.getRight();
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
              node = node.getLeft();
            }
          } else {
            if (Objects.isNull(node.getRight())) {
              Node<T> right = new Node<>(value, priority);
              node.setRight(right);
              node = right;
              break;
            } else {
              node = node.getRight();
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
      if (Objects.nonNull(getLeft()) && Objects.nonNull(getRight())) {
        this.size = getLeft().getSize() + getRight().getSize() + 1;
        this.height = Math.max(getLeft().getHeight(), getRight().getHeight()) + 1;
      } else if (Objects.nonNull(getLeft())) {
        this.size = getLeft().getSize() + 1;
        this.height = getLeft().getHeight() + 1;
      } else if (Objects.nonNull(getRight())) {
        this.size = getRight().getSize() + 1;
        this.height = getRight().getHeight() + 1;
      } else {
        this.size = 1;
        this.height = 1;
      }
    }

    @Override
    public Node<T> getLeft() {
      return (Node<T>) super.getLeft();
    }

    @Override
    public Node<T> getRight() {
      return (Node<T>) super.getRight();
    }

    @Override
    public void setLeft(AbstractTreap.Node<T> left) {
      super.setLeft(left);
      if (Objects.nonNull(left)) {
        getLeft().setParent(this);
      }
      updateInfo();
    }

    @Override
    public void setRight(AbstractTreap.Node<T> right) {
      super.setRight(right);
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
