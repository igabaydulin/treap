package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.Treap.Reference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: Add @ParameterizedTest
@DisplayName("Treap size tests")
public class TreapSizeTest {

  @Test
  @DisplayName("Check size for empty treap")
  public void check_treap_size_in_empty_treap() {
    Treap<Integer> treap = new Treap<>();
    Assertions.assertEquals(0, treap.size());
  }

  @Test
  @DisplayName("Check treap size with 1 element")
  public void check_treap_size_in_single_value_treap() {
    Treap<Integer> treap = new Treap<>();
    treap.add(1);
    Assertions.assertEquals(1, treap.size());
  }

  @Test
  @DisplayName("Check treap size with multiple elements")
  public void check_treap_size_in_multiples_value_treap_without_duplications() {
    Treap<Integer> treap = new Treap<>();
    treap.add(3);
    treap.add(1);
    treap.add(4);
    treap.add(5);
    treap.add(9);
    Assertions.assertEquals(5, treap.size());
  }

  @Test
  @DisplayName("Check treap size with multiple duplicated elements")
  public void check_treap_size_in_multiple_values_treap_with_duplications() {
    Treap<Integer> treap = new Treap<>();
    treap.add(3);
    treap.add(1);
    treap.add(1);
    treap.add(5);
    treap.add(1);
    Assertions.assertEquals(3, treap.size());
  }

  @Test
  @DisplayName("Check treaps size after split")
  public void check_treap_size_after_split() {
    Treap<Integer> treap = new Treap<>();

    treap.add(3);
    treap.add(1);
    treap.add(4);
    treap.add(2);
    treap.add(6);
    treap.add(5);
    treap.add(8);
    treap.add(0);
    treap.add(9);
    treap.add(7);

    Reference<Treap<Integer>> left = new Reference<>();
    Reference<Treap<Integer>> right = new Reference<>();

    treap.split(5, left, right);

    Assertions.assertEquals(6, left.get().size());
    Assertions.assertEquals(4, right.get().size());
  }
}