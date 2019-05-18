package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Testing duplications in treap")
class TreapDuplicationTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check if there is duplication for single value treap")
  void check_duplications_for_single_value_treap(Treap<Integer> treap) {
    Assertions.assertTrue(treap.add(4));
    Assertions.assertFalse(treap.add(4));
    Assertions.assertEquals(1, treap.size());
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check if there is duplication for multiple values treap")
  void check_duplications_for_multiple_values_treap(Treap<Integer> treap) {
    treap.add(3, 0.5);
    treap.add(2, 0.4);
    Assertions.assertTrue(treap.add(1, 0.3));
    Assertions.assertFalse(treap.add(1, 0.45));
    Assertions.assertEquals(3, treap.size());
  }
}
