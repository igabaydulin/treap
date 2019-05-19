package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.ValueTreap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Testing TreapMap#descendingMap#values")
class DescendingValueTreapValuesTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check empty value treap does not contains anything")
  void test_empty_value_treap(ValueTreap<Integer, Integer> valueTreap) {
    Collection<Integer> values = valueTreap.descendingMap().values();
    Assertions.assertEquals(0, values.size());
    Assertions.assertFalse(values.contains(3));
    Assertions.assertTrue(values.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check single value treap")
  void test_single_value_contains(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);

    Collection<Integer> values = valueTreap.descendingMap().values();
    Assertions.assertEquals(1, values.size());
    Assertions.assertFalse(values.isEmpty());
    Assertions.assertTrue(values.contains(1));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check single value treap removal")
  void test_single_value_remove(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);

    Collection<Integer> values = valueTreap.descendingMap().values();
    values.remove(1);
    Assertions.assertFalse(values.contains(1));
    Assertions.assertEquals(0, values.size());
    Assertions.assertTrue(values.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check single value treap conditional removal")
  void test_single_value_remove_if(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);

    Collection<Integer> values = valueTreap.descendingMap().values();
    values.removeIf(value -> value == 1);
    Assertions.assertFalse(values.contains(1));
    Assertions.assertEquals(0, values.size());
    Assertions.assertTrue(values.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check single value treap adding")
  void test_single_value_add(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);

    Collection<Integer> values = valueTreap.descendingMap().values();
    Assertions.assertThrows(UnsupportedOperationException.class, () -> values.add(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check single value treap clear")
  void test_single_value_clear(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);

    Collection<Integer> values = valueTreap.descendingMap().values();
    values.clear();
    Assertions.assertFalse(values.contains(1));
    Assertions.assertEquals(0, values.size());
    Assertions.assertTrue(values.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check array conversion")
  void test_to_array(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 6);
    valueTreap.put(1, 3);
    valueTreap.put(2, 5);
    valueTreap.put(4, 2);
    valueTreap.put(6, 1);
    valueTreap.put(5, 4);

    Collection<Integer> values = valueTreap.descendingMap().values();
    Assertions.assertArrayEquals(new Object[] {1, 4, 2, 6, 5, 3}, values.toArray());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check order is correct")
  void test_value_order(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(4, 1);
    valueTreap.put(9, 2);
    valueTreap.put(6, 5);

    int[] expected = new int[] {2, 5, 1, 1};
    int i = 0;
    for (int value : valueTreap.descendingMap().values()) {
      Assertions.assertEquals(expected[i++], value);
    }
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check reversed order is correct")
  void test_reversed_value_order(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(4, 1);
    valueTreap.put(9, 2);
    valueTreap.put(6, 5);

    int[] expected = new int[] {1, 1, 5, 2};
    int i = 0;
    for (int value : valueTreap.descendingMap().descendingMap().values()) {
      Assertions.assertEquals(expected[i++], value);
    }
  }
}
