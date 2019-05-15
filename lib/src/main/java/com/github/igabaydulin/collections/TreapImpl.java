package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.utils.Reference;
import java.util.Objects;
import java.util.function.Supplier;

public class TreapImpl<T extends Comparable<T>> extends AbstractTreap<T> {

  TreapImpl(long seed) {
    super(seed);
  }

  TreapImpl() {}

  private TreapImpl(AbstractTreap.Node<T> node) {
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

  public boolean add(T value, double priority) {
    return update(() -> setRoot(new Node<>(value, priority)), () -> setRoot(getRoot().add(value, priority)));
  }

  public boolean contains(T value) {
    return eval(false, () -> getRoot().contains(value));
  }

  public boolean delete(T value) {
    return update(() -> setRoot(getRoot().delete(value)));
  }

  public boolean split(T value, Reference<Treap<T>> left, Reference<Treap<T>> right) {
    return update(
        () -> {
          Reference<Node<T>> leftNode = new Reference<>();
          Reference<Node<T>> rightNode = new Reference<>();

          boolean contains = getRoot().split(value, leftNode, rightNode);

          left.set(new TreapImpl<>(leftNode.get()));
          right.set(new TreapImpl<>(rightNode.get()));

          return contains;
        });
  }

  /**
   * @throws ClassCastException if right is not {@link TreapImpl}
   * @see Treap#merge(Treap)
   */
  public Treap<T> merge(Treap<T> right) {
    return new TreapImpl<>(Node.merge(this.getRoot(), ((TreapImpl<T>) right).getRoot()));
  }

  public int size() {
    return eval(0, () -> getRoot().getSize());
  }

  public int height() {
    return eval(0, () -> getRoot().getHeight());
  }

  @Override
  public Node<T> getRoot() {
    return (Node<T>) super.getRoot();
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
}
