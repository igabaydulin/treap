package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("General treap tests")
class TreapCommonTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing single value treap")
  void single_value_treap_test(Treap<Integer> treap) {
    treap.add(4);
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing double value treap")
  void double_value_treap_test(Treap<Integer> treap) {
    treap.add(4);
    treap.add(5);
    Assertions.assertTrue(treap.contains(4));
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertEquals(2, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing value does not exist in empty treap")
  void empty_treap_test(Treap<Integer> treap) {
    Assertions.assertFalse(treap.contains(2));
    Assertions.assertEquals(0, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing deletion from single value treap")
  void deletion_from_single_value_treap_test(Treap<Integer> treap) {
    treap.add(4);
    treap.remove(4);
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertEquals(0, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing deletion from double value treap")
  void deletion_from_double_value_treap_test(Treap<Integer> treap) {
    treap.add(4);
    treap.add(5);
    treap.remove(4);
    Assertions.assertTrue(treap.contains(5));
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Testing full deletion from double value treap")
  void full_deletion_from_double_value_treap_test(Treap<Integer> treap) {
    treap.add(4);
    treap.add(5);
    treap.remove(4);
    treap.remove(5);
    Assertions.assertFalse(treap.contains(4));
    Assertions.assertFalse(treap.contains(5));
    Assertions.assertEquals(0, treap.size());
  }
}
