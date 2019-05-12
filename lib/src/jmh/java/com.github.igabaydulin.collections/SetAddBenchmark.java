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

  @State(Scope.Benchmark)
  public abstract static class SetState {

    private Set<Integer> set;
    private @Param({"1", "10", "100", "1000", "10000"}) int size;
    private int value;

    public SetState(Set<Integer> set) {
      this.set = set;
    }

    @Setup(Level.Invocation)
    public void setUp() {
      value = random.nextInt(size);
      for (int i = 0; i < size; ++i) {
        if (value != i) {
          getSet().add(random.nextInt(size));
        }
      }
    }

    @TearDown
    public void tearDown() {
      getSet().clear();
    }

    public Set<Integer> getSet() {
      return set;
    }

    public int getSize() {
      return size;
    }

    public int getValue() {
      return value;
    }
  }

  public static class TreeState extends SetState {

    public TreeState() {
      super(new TreeSet<>());
    }
  }

  public static class TreapState extends SetState {

    public TreapState() {
      super(new TreapSet<>());
    }
  }

  @Benchmark
  public void treap_add(TreapState state, Blackhole blackhole) {
    Set<Integer> set = state.getSet();
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
