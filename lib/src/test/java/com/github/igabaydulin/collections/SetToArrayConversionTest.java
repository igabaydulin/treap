package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.arguments.provider.TreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@SuppressWarnings("OverwrittenKey")
@DisplayName("Testing tree conversion to array")
class SetToArrayConversionTest {

  @ParameterizedTest
  @ArgumentsSource(TreapImplementationProvider.class)
  @DisplayName("Check ordering and non-duplication")
  void check_ordering_and_non_duplication(Treap<Integer> treap) {
    treap.add(3);
    treap.add(1);
    treap.add(4);
    treap.add(1);
    treap.add(5);
    treap.add(9);
    treap.add(2);
    treap.add(6);
    treap.add(5);

    Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 9}, treap.toArray(new Integer[] {}));
  }
}
