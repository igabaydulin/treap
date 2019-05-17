package com.github.igabaydulin.collections.state.addall;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class AddAllState {

  private @Param("100") int size;
  private Integer[] values;

  @Setup
  public void setUp() {
    values = new Integer[size];
    for (int i = 0; i < size; ++i) {
      values[i] = i;
    }
  }

  public Integer[] getValues() {
    return values;
  }

  public SortedSet<Integer> getCollection() {
    return new TreeSet<>(Arrays.asList(values));
  }
}
