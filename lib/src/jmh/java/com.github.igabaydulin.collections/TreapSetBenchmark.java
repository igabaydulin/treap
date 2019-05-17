package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.state.TreapSetState;
import org.openjdk.jmh.annotations.Benchmark;

public class TreapSetBenchmark {

  @Benchmark
  public void add(TreapSetState state) {
    state.getSet().add(state.getValue());
  }
}
