package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreapArrayTest {

  @Test
  public void test1() {
    TreapSet<Integer> treapSet = new TreapSet<>();
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
