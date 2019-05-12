package com.github.igabaydulin.collections;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

public class SetAddBenchmark {

  private static final Random random = new Random();

  @State(Scope.Thread)
  public abstract static class SetState {

    private @Param({"1", "10", "100", "1000", "10000"}) int size;
    private int value;

    @Setup(Level.Invocation)
    public void setUp() {
      value = random.nextInt(size);
      for (int i = 0; i < size; ++i) {
        if (value != i) {
          getSet().add(random.nextInt(size));
        }
      }
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
      getSet().clear();
    }

    public int getSize() {
      return size;
    }

    public int getValue() {
      return value;
    }

    public abstract Set<Integer> getSet();
  }

  public static class TreeState extends SetState {

    private TreeSet<Integer> set = new TreeSet<>();

    @Override
    public TreeSet<Integer> getSet() {
      return set;
    }
  }

  public static class TreapState extends SetState {

    private double priority = random.nextDouble();
    private TreapSet<Integer> set = new TreapSet<>();

    @Override
    public TreapSet<Integer> getSet() {
      return set;
    }

    public double getPriority() {
      return priority;
    }
  }

  @Benchmark
  public void treap_add_with_priority(TreapState state, Blackhole blackhole) {
    TreapSet<Integer> set = state.getSet();
    set.getTreap().add(state.getValue(), state.getPriority());
    blackhole.consume(set);
  }

  @Benchmark
  public void treap_add(TreapState state, Blackhole blackhole) {
    TreapSet<Integer> set = state.getSet();
    set.add(state.getValue());
    blackhole.consume(set);
  }

  @Benchmark
  public void tree_add(TreeState state, Blackhole blackhole) {
    Set<Integer> set = state.getSet();
    set.add(state.getValue());
    blackhole.consume(set);
  }
}
