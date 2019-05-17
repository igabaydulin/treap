package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.state.TreeSetState;
import org.openjdk.jmh.annotations.Benchmark;

public class TreeSetBenchmark {

  @Benchmark
  public void add(TreeSetState state) {
    state.getSet().add(state.getValue());
  }
}
