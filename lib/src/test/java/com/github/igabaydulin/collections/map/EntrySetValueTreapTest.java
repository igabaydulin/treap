package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.ValueTreap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Test entry set of value treap")
class EntrySetValueTreapTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check entry set clear")
  void test_clear(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Set<Entry<Integer, Integer>> entries = valueTreap.entrySet();
    entries.clear();
    Assertions.assertTrue(valueTreap.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check entry set remove")
  void test_remove(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Set<Entry<Integer, Integer>> entries = valueTreap.entrySet();
    entries.remove(entries.iterator().next());
    Assertions.assertEquals(2, valueTreap.size());
    Assertions.assertFalse(valueTreap.contains(1));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check entry set add")
  void test_add(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    Set<Entry<Integer, Integer>> entries = valueTreap.entrySet();
    entries.add(
        new Entry<Integer, Integer>() {
          @Override
          public Integer getKey() {
            return 4;
          }

          @Override
          public Integer getValue() {
            return 5;
          }

          @Override
          public Integer setValue(Integer value) {
            return null;
          }
        });
    Assertions.assertEquals(4, valueTreap.size());
    Assertions.assertTrue(valueTreap.contains(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check entry set array")
  void test_array(ValueTreap<Integer, Integer> valueTreap) {
    valueTreap.put(3, 1);
    valueTreap.put(1, 3);
    valueTreap.put(2, 2);

    int[] keys = new int[] {1, 2, 3};
    int[] values = new int[] {3, 2, 1};
    int index = 0;
    for (Entry entry : valueTreap.entrySet().toArray(new Entry[] {})) {
      Assertions.assertEquals(keys[index], entry.getKey());
      Assertions.assertEquals(values[index++], entry.getValue());
    }
  }
}
