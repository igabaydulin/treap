package com.github.igabaydulin.collections;

import java.util.Random;
import java.util.Set;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class SetToArrayBenchmark {

  private static final int VALUES_LIMIT = 100;
  private static final Random random = new Random();

  @State(Scope.Benchmark)
  public static class TreapState {

    private final Set<Integer> set = new TreapSet<>();

    public TreapState() {
      for (int i = 0; i < VALUES_LIMIT; ++i) {
        set.add(random.nextInt(VALUES_LIMIT));
      }
    }

    public Set<Integer> getSet() {
      return set;
    }
  }

  @State(Scope.Benchmark)
  public static class TreeState {

    private final Set<Integer> set = new TreapSet<>();

    public TreeState() {
      for (int i = 0; i < VALUES_LIMIT; ++i) {
        set.add(random.nextInt(VALUES_LIMIT));
      }
    }

    public Set<Integer> getSet() {
      return set;
    }
  }

  @Benchmark
  public void treap_set(TreapState state, Blackhole blackhole) {
    blackhole.consume(state.getSet().toArray());
  }

  @Benchmark
  public void tree_set(TreeState state, Blackhole blackhole) {
    blackhole.consume(state.getSet().toArray());
  }
}
