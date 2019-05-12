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

  @Test
  @DisplayName("Check if there is duplication for multiple values treap")
  public void check_duplications_for_multiple_values_treap() {
    TreapSet<Integer> treapSet = new TreapSet<>(10);
    treapSet.getTreap().add(3, 0.5);
    treapSet.getTreap().add(2, 0.4);
    Assertions.assertTrue(treapSet.getTreap().add(1, 0.3));
    Assertions.assertFalse(treapSet.getTreap().add(1, 0.45));
    Assertions.assertEquals(3, treapSet.size());
  }
}
