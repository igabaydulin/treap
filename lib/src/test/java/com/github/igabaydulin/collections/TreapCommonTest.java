package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: Maybe split tests by methods?
/**
 * Tests general methods: {@link Treap#add(Comparable)}, {@link Treap#delete(Comparable)}, {@link Treap#size()}
 *
 * @see Treap
 */
@DisplayName("General treap tests")
class TreapCommonTest {

  @Test
  @DisplayName("Testing single value treap")
  void single_value_treap_test() {
    Treap<Integer> treap = new Treap<>();
    treap.add(4);
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @Test
  @DisplayName("Testing double value treap")
  void double_value_treap_test() {
    Treap<Integer> treap = new Treap<>();
    treap.add(4);
    treap.add(5);
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }

  @Test
  @DisplayName("Testing value does not exist in empty treap")
  void empty_treap_test() {
    Treap<Integer> treap = new Treap<>();
    Assertions.assertFalse(treap.contains(2));
    Assertions.assertEquals(0, treap.size());
  }

  @Test
  @DisplayName("Testing deletion from single value treap")
  void deletion_from_single_value_treap_test() {
    Treap<Integer> treap = new Treap<>();
    treap.add(4);
    treap.delete(4);
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertEquals(0, treap.size());
  }

  @Test
  @DisplayName("Testing deletion from double value treap")
  void deletion_from_double_value_treap_test() {
    Treap<Integer> treap = new Treap<>();
    treap.add(4);
    treap.add(5);
    treap.delete(4);
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @Test
  @DisplayName("Testing full deletion from double value treap")
  void full_deletion_from_double_value_treap_test() {
    Treap<Integer> treap = new Treap<>();
    treap.add(4);
    treap.add(5);
    treap.delete(4);
    treap.delete(5);
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertFalse(treap.contains(5));
    Assertions.assertEquals(0, treap.size());
  }
}
