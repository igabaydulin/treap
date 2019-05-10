package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreapIteratorTest {

  @Test
  public void iterator_test1() {
    TreapSet<Integer> set = new TreapSet();
    set.add(3);

    for (int value : set) {
      Assertions.assertEquals(3, value);
    }
  }

  @Test
  public void iterator_test2() {
    TreapSet<Integer> set = new TreapSet();
    set.add(3);
    set.add(1);
    set.add(4);
    set.add(1);
    set.add(5);
    set.add(9);
    set.add(2);
    set.add(6);
    set.add(5);

    int[] sortedArray = new int[] {1, 2, 3, 4, 5, 6, 9};
    int index = 0;
    for (int value : set) {
      Assertions.assertEquals(sortedArray[index++], value);
    }
  }
}
