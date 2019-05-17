package com.github.igabaydulin.collections;

import com.github.igabaydulin.collections.state.add.TreapSetState;
import com.github.igabaydulin.collections.state.addall.AddAllState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class TreapSetBenchmark {

  @Benchmark
  public void add(TreapSetState state) {
    state.getSet().add(state.getValue());
  }

  @Benchmark
  public void addAll(AddAllState state, Blackhole blackhole) {
    TreapSet<Integer> treapSet = new TreapSet<>();
    treapSet.addAll(state.getCollection());
    blackhole.consume(treapSet);
  }

  @Benchmark
  public void addBack(AddAllState state, Blackhole blackhole) {
    TreapSet<Integer> treapSet = new TreapSet<>();
    treapSet.addBack(state.getValues());
    blackhole.consume(treapSet);
  }
}
