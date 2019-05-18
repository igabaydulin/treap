package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import com.github.igabaydulin.collections.utils.Reference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

// TODO: Add @ParameterizedTest
@DisplayName("Treap size tests")
class TreapSizeTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check size for empty treap")
  void check_treap_size_in_empty_treap(Treap<Integer> treap) {
    Assertions.assertEquals(0, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check treap size with 1 element")
  void check_treap_size_in_single_value_treap(Treap<Integer> treap) {
    treap.add(1);
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check treap size with multiple elements")
  void check_treap_size_in_multiples_value_treap_without_duplications(Treap<Integer> treap) {
    treap.add(3, 0.1);
    treap.add(1, 0.2);
    treap.add(4, 0.5);
    treap.add(5, 0.4);
    treap.add(9, 0.3);
    Assertions.assertEquals(5, treap.size());
  }

  @SuppressWarnings("OverwrittenKey")
  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check treap size with multiple duplicated elements")
  void check_treap_size_in_multiple_values_treap_with_duplications(Treap<Integer> treap) {
    treap.add(3);
    treap.add(1);
    treap.add(1);
    treap.add(5);
    treap.add(1);
    Assertions.assertEquals(3, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check treaps size after split")
  void check_treap_size_after_split(Treap<Integer> treap) {
    treap.add(3, 0.3);
    treap.add(1, 0.1);
    treap.add(4, 0.4);
    treap.add(2, 0.2);
    treap.add(6, 0.6);
    treap.add(5, 0.5);
    treap.add(8, 0.8);
    treap.add(0, 0.0);
    treap.add(9, 0.9);
    treap.add(7, 0.7);

    Reference<Treap<Integer>> left = new Reference<>();
    Reference<Treap<Integer>> right = new Reference<>();

    treap.split(5, left, right);

    Assertions.assertEquals(6, left.get().size());
    Assertions.assertEquals(4, right.get().size());
  }
}
