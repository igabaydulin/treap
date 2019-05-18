package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Testing iterator and foreach usage")
class TreapIteratorTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check foreach for a single value treap")
  void iterator_test1(Treap<Integer> treap) {
    treap.add(3);
    for (int value : treap) {
      Assertions.assertEquals(3, value);
    }
  }

  @SuppressWarnings("OverwrittenKey")
  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check foreach for multiple values in a treap")
  void iterator_test2(Treap<Integer> treap) {
    treap.add(3);
    treap.add(1);
    treap.add(4);
    treap.add(1);
    treap.add(5);
    treap.add(9);
    treap.add(2);
    treap.add(6);
    treap.add(5);
    int[] sortedArray = new int[] {1, 2, 3, 4, 5, 6, 9};
    int index = 0;
    for (int value : treap) {
      Assertions.assertEquals(sortedArray[index++], value);
    }
  }
}
