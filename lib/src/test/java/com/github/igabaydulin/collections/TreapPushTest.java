package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Testing push (back and front) tests")
class TreapPushTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing single value treap")
  void single_value_treap_test(Treap<Integer> treap) {
    treap.addBack(new Integer[] {4});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing double value treap")
  void double_value_treap_test(Treap<Integer> treap) {
    treap.addBack(new Integer[] {4, 5}, new double[] {0.1, 0.2});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing double value treap (reversed priority)")
  void double_value_treap_test_reversed_priority(Treap<Integer> treap) {
    treap.addBack(new Integer[] {4, 5}, new double[] {0.2, 0.1});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing single value treap (#addFront)")
  void single_value_treap_test_push_front(Treap<Integer> treap) {
    treap.addFront(new Integer[] {4});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing double value treap (#addFront)")
  void double_value_treap_test_push_front(Treap<Integer> treap) {
    treap.addFront(new Integer[] {5, 4}, new double[] {0.2, 0.1});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing double value treap (#addFront, reversed priority)")
  void double_value_treap_test_push_front_reversed_priority(Treap<Integer> treap) {
    treap.addFront(new Integer[] {5, 4}, new double[] {0.2, 0.1});
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }
}
