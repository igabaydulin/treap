package com.github.igabaydulin.collections.map;

import com.github.igabaydulin.collections.ValueTreap;
import com.github.igabaydulin.collections.arguments.provider.ValueTreapImplementationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@DisplayName("Test descending value treap")
class DescendingValueTreapEqualsTest {

  @ParameterizedTest
  @ArgumentsSource(ValueTreapImplementationProvider.class)
  @DisplayName("Check descending map of descending map is original map")
  void test_descending_map_of_descending_map(ValueTreap<Integer, Integer> valueTreap) {
    Assertions.assertSame(valueTreap, valueTreap.descendingMap().descendingMap());
  }
}
