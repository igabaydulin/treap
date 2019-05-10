package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreapTest {

  @Test
  void test1() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);

    Assertions.assertTrue(treap.contains(4));
  }

  @Test
  void test2() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);

    Assertions.assertTrue(treap.contains(4));
  }

  @Test
  void test3() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);

    Assertions.assertTrue(treap.contains(5));
  }

  @Test
  void test4() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);

    Assertions.assertFalse(treap.contains(2));
  }

  @Test
  void test5() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.delete(4);

    Assertions.assertFalse(treap.contains(4));
  }

  @Test
  void test6() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);
    treap.delete(4);

    Assertions.assertTrue(treap.contains(5));
  }

  @Test
  void test7() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);
    treap.delete(4);

    Assertions.assertFalse(treap.contains(4));
  }

  @Test
  void test8() {
    Treap<Integer> treap = new Treap<>();

    treap.add(4);
    treap.add(5);
    treap.delete(4);
    treap.delete(5);

    Assertions.assertFalse(treap.contains(2));
  }
}
