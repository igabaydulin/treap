package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.TreapMap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Test descending NavigableMap")
class DescengingNavigableMapTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first entry")
  void test_first_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.firstEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last entry")
  void test_last_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.lastEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first key")
  void test_first_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.firstKey();
    Assertions.assertEquals(9, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last key")
  void test_last_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.lastKey();
    Assertions.assertEquals(3, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check lower entry")
  void test_lower_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.lowerEntry(4);
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check floor entry")
  void test_floor_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.floorEntry(4);
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check ceiling entry")
  void test_ceiling_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.ceilingEntry(4);
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check higher entry")
  void test_higher_entry(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.higherEntry(4);
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check lower key")
  void test_lower_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.lowerKey(4);
    Assertions.assertEquals(6, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check floor key")
  void test_floor_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.floorKey(4);
    Assertions.assertEquals(4, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check ceiling key")
  void test_ceiling_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.ceilingKey(4);
    Assertions.assertEquals(4, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check higher key")
  void test_higher_key(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Integer key = descendingMap.higherKey(4);
    Assertions.assertEquals(3, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map")
  void test_head_map(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> headMap = descendingMap.headMap(6);
    Assertions.assertEquals(1, headMap.size());
    Assertions.assertTrue(headMap.containsKey(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (false inclusive)")
  void test_head_map_inclusive_false(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> headMap = descendingMap.headMap(6, false);
    Assertions.assertEquals(1, headMap.size());
    Assertions.assertTrue(headMap.containsKey(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (true inclusive)")
  void test_head_map_inclusive_true(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> headMap = descendingMap.headMap(6, true);
    Assertions.assertEquals(2, headMap.size());
    Assertions.assertTrue(headMap.containsKey(6));
    Assertions.assertTrue(headMap.containsKey(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check tail map")
  void test_tail_map(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> tailMap = descendingMap.tailMap(6);
    Assertions.assertEquals(3, tailMap.size());
    Assertions.assertTrue(tailMap.containsKey(3));
    Assertions.assertTrue(tailMap.containsKey(4));
    Assertions.assertTrue(tailMap.containsKey(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check tail map (false inclusive)")
  void test_tail_map_inclusive_false(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> tailMap = descendingMap.tailMap(6, false);
    Assertions.assertEquals(2, tailMap.size());
    Assertions.assertTrue(tailMap.containsKey(3));
    Assertions.assertTrue(tailMap.containsKey(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (true inclusive)")
  void test_tail_map_inclusive_true(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> tailMap = descendingMap.tailMap(6, true);
    Assertions.assertEquals(3, tailMap.size());
    Assertions.assertTrue(tailMap.containsKey(3));
    Assertions.assertTrue(tailMap.containsKey(4));
    Assertions.assertTrue(tailMap.containsKey(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: false, inclusive: false)")
  void test_sub_map1(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> subMap = descendingMap.subMap(9, false, 3, false);
    Assertions.assertEquals(2, subMap.size());
    Assertions.assertTrue(subMap.containsKey(4));
    Assertions.assertTrue(subMap.containsKey(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: true, inclusive: false))")
  void test_sub_map2(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> subMap = descendingMap.subMap(9, true, 3, false);
    Assertions.assertEquals(3, subMap.size());
    Assertions.assertTrue(subMap.containsKey(4));
    Assertions.assertTrue(subMap.containsKey(6));
    Assertions.assertTrue(subMap.containsKey(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: false, inclusive: true))")
  void test_sub_map3(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> subMap = descendingMap.subMap(9, false, 3, true);
    Assertions.assertEquals(3, subMap.size());
    Assertions.assertTrue(subMap.containsKey(3));
    Assertions.assertTrue(subMap.containsKey(4));
    Assertions.assertTrue(subMap.containsKey(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: true, inclusive: true))")
  void test_sub_map4(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    SortedMap<Integer, Integer> subMap = descendingMap.subMap(9, true, 3, true);
    Assertions.assertEquals(4, subMap.size());
    Assertions.assertTrue(subMap.containsKey(3));
    Assertions.assertTrue(subMap.containsKey(4));
    Assertions.assertTrue(subMap.containsKey(6));
    Assertions.assertTrue(subMap.containsKey(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first entry and poll")
  void test_first_entry_and_poll(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.pollFirstEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
    Assertions.assertEquals(3, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(9));

    entry = descendingMap.pollFirstEntry();
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
    Assertions.assertEquals(2, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(6));

    entry = descendingMap.pollFirstEntry();
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
    Assertions.assertEquals(1, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(4));

    entry = descendingMap.pollFirstEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
    Assertions.assertEquals(0, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(3));

    Assertions.assertTrue(descendingMap.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last entry and poll")
  void test_last_entry_and_poll(TreapMap<Integer, Integer> treapMap) {
    NavigableMap<Integer, Integer> descendingMap = treapMap.descendingMap();
    descendingMap.put(3, 8);
    descendingMap.put(4, 1);
    descendingMap.put(9, 2);
    descendingMap.put(6, 5);

    Entry<Integer, Integer> entry = descendingMap.pollLastEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
    Assertions.assertEquals(3, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(3));

    entry = descendingMap.pollLastEntry();
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
    Assertions.assertEquals(2, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(4));

    entry = descendingMap.pollLastEntry();
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
    Assertions.assertEquals(1, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(6));

    entry = descendingMap.pollLastEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
    Assertions.assertEquals(0, descendingMap.size());
    Assertions.assertFalse(descendingMap.containsKey(9));

    Assertions.assertTrue(descendingMap.isEmpty());
  }
}
