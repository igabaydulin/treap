package com.github.igabaydulin.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.infra.Blackhole;

public class SetCreationBenchmark {

  @org.openjdk.jmh.annotations.State(Scope.Benchmark)
  public static class State {

    private static final Random random = new Random();
    private static final int VALUES_LIMIT = 100;

    private final List<Integer> values = new ArrayList<>();

    public State() {
      for (int i = 0; i < VALUES_LIMIT; ++i) {
        values.add(random.nextInt(VALUES_LIMIT));
      }
    }

    public List<Integer> getValues() {
      return values;
    }
  }

  @Benchmark
  public void create_treap_set_by_add(State state, Blackhole blackhole) {
    TreapSet<Integer> set = new TreapSet<>();
    state.getValues().forEach(set::add);
    blackhole.consume(set);
  }

  @Benchmark
  public void create_tree_set_by_add(State state, Blackhole blackhole) {
    TreeSet<Integer> set = new TreeSet<>();
    state.getValues().forEach(set::add);
    blackhole.consume(set);
  }

  @Benchmark
  public void create_treap_set_by_add_all(State state, Blackhole blackhole) {
    TreapSet<Integer> set = new TreapSet<>();
    set.addAll(state.getValues());
    blackhole.consume(set);
  }

  @Benchmark
  public void create_tree_set_by_add_all(State state, Blackhole blackhole) {
    TreeSet<Integer> set = new TreeSet<>();
    set.addAll(state.getValues());
    blackhole.consume(set);
  }

  @Benchmark
  public void create_tree_set_by_add_constructor(State state, Blackhole blackhole) {
    TreeSet<Integer> set = new TreeSet<>(state.getValues());
    blackhole.consume(set);
  }
}
