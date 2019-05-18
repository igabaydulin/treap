package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Testing get operator")
class TreapGetTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from empty treap")
  void get_from_empty_treap(Treap<Integer> treap) {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> treap.get(0));
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from single value treap by negative index")
  void get_from_single_value_treap_negative_index(Treap<Integer> treap) {
    Assertions.assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          treap.add(4);
          Assertions.assertNull(treap.get(-1)); // assert should not happen, exception must be raised
        });
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from single value treap by out of boundary index")
  void get_from_single_value_treap_out_of_boundary_index(Treap<Integer> treap) {
    Assertions.assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          treap.add(4);
          Assertions.assertNull(treap.get(1)); // assert should not happen, exception must be raised
        });
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from single value treap")
  void get_from_single_value_treap(Treap<Integer> treap) {
    treap.add(4);
    Assertions.assertEquals(4, treap.get(0));
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from double value treap")
  void get_from_double_value_treap(Treap<Integer> treap) {
    treap.add(4, 0.1);
    treap.add(5, 0.2);
    Assertions.assertEquals(4, treap.get(0));
    Assertions.assertEquals(5, treap.get(1));
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from double value treap (reversed priority)")
  void get_from_double_value_treap_reversed_priority(Treap<Integer> treap) {
    treap.add(4, 0.2);
    treap.add(5, 0.1);
    Assertions.assertEquals(4, treap.get(0));
    Assertions.assertEquals(5, treap.get(1));
  }

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Get from multiple value treap")
  void get_from_multiple_value_treap(Treap<Integer> treap) {
    for (int i = 0; i < 100; ++i) {
      treap.add(i);
    }

    for (int i = 0; i < 100; ++i) {
      Assertions.assertEquals(i, treap.get(i));
    }
  }
}
