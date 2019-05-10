package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreapSizeTest {

  @Test
  void test0() {
    Treap<Integer> treap = new Treap<>();

    Assertions.assertEquals(0, treap.size());
  }

  @Test
  void test1() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);

    Assertions.assertEquals(1, treap.size());
  }

  @Test
  void test2() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);

    Assertions.assertEquals(2, treap.size());
  }

  @Test
  void test3() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(4);

    Assertions.assertEquals(1, treap.size());
  }

  @Test
  void test4() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);
    treap.delete(4);

    Assertions.assertEquals(1, treap.size());
  }
}
