package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.ValueTreap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Test reversed entry set of value treap")
class DescendingKeySetValueTreapTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check key set clear")
  void test_clear(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Set<Integer> keys = valueTreap.descendingKeySet();
    keys.clear();
    Assertions.assertTrue(valueTreap.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check key set remove")
  void test_remove(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Set<Integer> keys = valueTreap.descendingKeySet();
    keys.remove(keys.iterator().next());
    Assertions.assertEquals(2, valueTreap.size());
    Assertions.assertFalse(valueTreap.contains(3));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check key set add")
  void test_add(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Assertions.assertThrows(UnsupportedOperationException.class, () -> valueTreap.descendingKeySet().add(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check key set array")
  void test_array(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Assertions.assertArrayEquals(new Object[] {3, 2, 1}, valueTreap.descendingKeySet().toArray());
  }
}
