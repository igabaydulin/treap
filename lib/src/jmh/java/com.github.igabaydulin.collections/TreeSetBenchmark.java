package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.state.add.TreeSetState;
import com.github.igabaydulin.collections.state.addall.AddAllState;
import java.util.TreeSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class TreeSetBenchmark {

  @Benchmark
  public void add(TreeSetState state) {
    state.getSet().add(state.getValue());
  }

  @Benchmark
  public void addAll(AddAllState state, Blackhole blackhole) {
    blackhole.consume(new TreeSet<>(state.getCollection()));
  }
}
