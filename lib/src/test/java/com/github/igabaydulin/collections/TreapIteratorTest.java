package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing iterator and foreach usage")
public class TreapIteratorTest {

  @Test
  @DisplayName("Check foreach for a single value treap")
  public void iterator_test1() {
    TreapSet<Integer> set = new TreapSet<>();
    set.add(3);
    for (int value : set) {
      Assertions.assertEquals(3, value);
    }
  }

  @Test
  @DisplayName("Check foreach for multiple values in a treap")
  public void iterator_test2() {
    TreapSet<Integer> set = new TreapSet<>();
    Treap<Integer> treap = set.getTreap();
    treap.add(3, 0.3);
    treap.add(1, 0.1);
    treap.add(4, 0.4);
    treap.add(1, 0.1);
    treap.add(5, 0.5);
    treap.add(9, 0.9);
    treap.add(2, 0.2);
    treap.add(6, 0.6);
    treap.add(5, 0.5);
    int[] sortedArray = new int[] {1, 2, 3, 4, 5, 6, 9};
    int index = 0;
    for (int value : set) {
      Assertions.assertEquals(sortedArray[index++], value);
    }
  }
}
