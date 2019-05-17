package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing get operator")
class TreapGetTest {

  @Test
  @DisplayName("Get from empty treap")
  void get_from_empty_treap() {
    Assertions.assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          TreapSet<Integer> treapSet = new TreapSet<>();
          treapSet.get(0);
        });
  }

  @Test
  @DisplayName("Get from single value treap by negative index")
  void get_from_single_value_treap_negative_index() {
    Assertions.assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          TreapSet<Integer> treapSet = new TreapSet<>();
          treapSet.add(4);
          Assertions.assertNull(treapSet.get(-1)); // assert should not happen, exception must be raised
        });
  }

  @Test
  @DisplayName("Get from single value treap by oversized index")
  void get_from_single_value_treap_oversized_index() {
    Assertions.assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          TreapSet<Integer> treapSet = new TreapSet<>();
          treapSet.add(4);
          Assertions.assertNull(treapSet.get(1)); // assert should not happen, exception must be raised
        });
  }

  @Test
  @DisplayName("Get from single value treap")
  void get_from_single_value_treap() {
    TreapSet<Integer> treapSet = new TreapSet<>();
    treapSet.add(4);
    Assertions.assertEquals(4, treapSet.get(0));
  }

  @Test
  @DisplayName("Get from double value treap")
  void get_from_double_value_treap() {
    TreapSet<Integer> treapSet = new TreapSet<>();
    treapSet.add(4, 0.1);
    treapSet.add(5, 0.2);
    Assertions.assertEquals(4, treapSet.get(0));
    Assertions.assertEquals(5, treapSet.get(1));
  }

  @Test
  @DisplayName("Get from double value treap (reversed priority)")
  void get_from_double_value_treap_reversed_priority() {
    TreapSet<Integer> treapSet = new TreapSet<>();
    treapSet.add(4, 0.2);
    treapSet.add(5, 0.1);
    Assertions.assertEquals(4, treapSet.get(0));
    Assertions.assertEquals(5, treapSet.get(1));
  }

  @Test
  @DisplayName("Get from multiple value treap")
  void get_from_multiple_value_treap() {
    TreapSet<Integer> treapSet = new TreapSet<>();
    for (int i = 0; i < 100; ++i) {
      treapSet.add(i);
    }

    for (int i = 0; i < 100; ++i) {
      Assertions.assertEquals(i, treapSet.get(i));
    }
  }
}
