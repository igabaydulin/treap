package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.TreapMap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import java.util.Map.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Test NavigableMap interface")
class NavigableMapTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first entry")
  void test_first_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.firstEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last entry")
  void test_last_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.lastEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first key")
  void test_first_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.firstKey();
    Assertions.assertEquals(3, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last key")
  void test_last_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.lastKey();
    Assertions.assertEquals(9, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check lower entry")
  void test_lower_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.lowerEntry(4);
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check floor entry")
  void test_floor_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.floorEntry(4);
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check ceiling entry")
  void test_ceiling_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.ceilingEntry(4);
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check higher entry")
  void test_higher_entry(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.higherEntry(4);
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check lower key")
  void test_lower_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.lowerKey(4);
    Assertions.assertEquals(3, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check floor key")
  void test_floor_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.floorKey(4);
    Assertions.assertEquals(4, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check ceiling key")
  void test_ceiling_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.ceilingKey(4);
    Assertions.assertEquals(4, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check higher key")
  void test_higher_key(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Integer key = treapMap.higherKey(4);
    Assertions.assertEquals(6, key);
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map")
  void test_head_map(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> headMap = treapMap.headMap(6);
    Assertions.assertEquals(2, headMap.size());
    Assertions.assertTrue(headMap.contains(3));
    Assertions.assertTrue(headMap.contains(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (false inclusive)")
  void test_head_map_inclusive_false(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> headMap = treapMap.headMap(6, false);
    Assertions.assertEquals(2, headMap.size());
    Assertions.assertTrue(headMap.contains(3));
    Assertions.assertTrue(headMap.contains(4));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (true inclusive)")
  void test_head_map_inclusive_true(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> headMap = treapMap.headMap(6, true);
    Assertions.assertEquals(3, headMap.size());
    Assertions.assertTrue(headMap.contains(3));
    Assertions.assertTrue(headMap.contains(4));
    Assertions.assertTrue(headMap.contains(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check tail map")
  void test_tail_map(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> tailMap = treapMap.tailMap(6);
    Assertions.assertEquals(2, tailMap.size());
    Assertions.assertTrue(tailMap.contains(6));
    Assertions.assertTrue(tailMap.contains(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check tail map (false inclusive)")
  void test_tail_map_inclusive_false(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> tailMap = treapMap.tailMap(6, false);
    Assertions.assertEquals(1, tailMap.size());
    Assertions.assertTrue(tailMap.contains(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check head map (true inclusive)")
  void test_tail_map_inclusive_true(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> tailMap = treapMap.tailMap(6, true);
    Assertions.assertEquals(2, tailMap.size());
    Assertions.assertTrue(tailMap.contains(6));
    Assertions.assertTrue(tailMap.contains(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: false, inclusive: false)")
  void test_sub_map1(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> subMap = treapMap.subMap(3, false, 9, false);
    Assertions.assertEquals(2, subMap.size());
    Assertions.assertTrue(subMap.contains(4));
    Assertions.assertTrue(subMap.contains(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: true, inclusive: false))")
  void test_sub_map2(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> subMap = treapMap.subMap(3, true, 9, false);
    Assertions.assertEquals(3, subMap.size());
    Assertions.assertTrue(subMap.contains(3));
    Assertions.assertTrue(subMap.contains(4));
    Assertions.assertTrue(subMap.contains(6));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: false, inclusive: true))")
  void test_sub_map3(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> subMap = treapMap.subMap(3, false, 9, true);
    Assertions.assertEquals(3, subMap.size());
    Assertions.assertTrue(subMap.contains(4));
    Assertions.assertTrue(subMap.contains(6));
    Assertions.assertTrue(subMap.contains(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check sub map (inclusive: true, inclusive: true))")
  void test_sub_map4(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    TreapMap<Integer, Integer> subMap = treapMap.subMap(3, true, 9, true);
    Assertions.assertEquals(4, subMap.size());
    Assertions.assertTrue(subMap.contains(3));
    Assertions.assertTrue(subMap.contains(4));
    Assertions.assertTrue(subMap.contains(6));
    Assertions.assertTrue(subMap.contains(9));
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check first entry and poll")
  void test_first_entry_and_poll(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.pollFirstEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
    Assertions.assertEquals(3, treapMap.size());
    Assertions.assertFalse(treapMap.contains(3));

    entry = treapMap.pollFirstEntry();
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
    Assertions.assertEquals(2, treapMap.size());
    Assertions.assertFalse(treapMap.contains(4));

    entry = treapMap.pollFirstEntry();
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
    Assertions.assertEquals(1, treapMap.size());
    Assertions.assertFalse(treapMap.contains(6));

    entry = treapMap.pollFirstEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
    Assertions.assertEquals(0, treapMap.size());
    Assertions.assertFalse(treapMap.contains(9));

    Assertions.assertTrue(treapMap.isEmpty());
  }

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check last entry and poll")
  void test_last_entry_and_poll(TreapMap<Integer, Integer> treapMap) {
    treapMap.put(3, 8);
    treapMap.put(4, 1);
    treapMap.put(9, 2);
    treapMap.put(6, 5);

    Entry<Integer, Integer> entry = treapMap.pollLastEntry();
    Assertions.assertEquals(9, entry.getKey());
    Assertions.assertEquals(2, entry.getValue());
    Assertions.assertEquals(3, treapMap.size());
    Assertions.assertFalse(treapMap.contains(9));

    entry = treapMap.pollLastEntry();
    Assertions.assertEquals(6, entry.getKey());
    Assertions.assertEquals(5, entry.getValue());
    Assertions.assertEquals(2, treapMap.size());
    Assertions.assertFalse(treapMap.contains(6));

    entry = treapMap.pollLastEntry();
    Assertions.assertEquals(4, entry.getKey());
    Assertions.assertEquals(1, entry.getValue());
    Assertions.assertEquals(1, treapMap.size());
    Assertions.assertFalse(treapMap.contains(4));

    entry = treapMap.pollLastEntry();
    Assertions.assertEquals(3, entry.getKey());
    Assertions.assertEquals(8, entry.getValue());
    Assertions.assertEquals(0, treapMap.size());
    Assertions.assertFalse(treapMap.contains(3));

    Assertions.assertTrue(treapMap.isEmpty());
  }
}
