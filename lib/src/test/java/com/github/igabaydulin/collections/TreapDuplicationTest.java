package com.github.igabaydulin.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing duplications in treap")
public class TreapDuplicationTest {

  @Test
  @DisplayName("Check if there is duplication for single value treap")
  public void check_duplications_for_single_value_treap() {
    TreapSet<Integer> treapSet = new TreapSet<>(10);
    Assertions.assertTrue(treapSet.add(4));
    Assertions.assertFalse(treapSet.add(4));
    Assertions.assertEquals(1, treapSet.size());
  }
}
