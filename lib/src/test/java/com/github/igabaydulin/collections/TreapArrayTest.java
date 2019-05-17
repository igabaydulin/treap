package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("OverwrittenKey")
@DisplayName("Testing tree conversion to array")
class TreapArrayTest {

  @Test
  @DisplayName("Check ordering and non-duplication")
  void check_ordering_and_non_duplication() {
    TreapSet<Integer> treapSet = new TreapSet<>(10);

    treapSet.add(3);
    treapSet.add(1);
    treapSet.add(4);
    treapSet.add(1);
    treapSet.add(5);
    treapSet.add(9);
    treapSet.add(2);
    treapSet.add(6);
    treapSet.add(5);

    Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 9}, treapSet.toArray(new Integer[] {}));
  }
}
