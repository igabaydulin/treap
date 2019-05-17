package com.github.igabaydulin.collections.state;

import java.util.Random;
import java.util.TreeSet;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
public class TreeSetState {

  private Random random = new Random();
  private TreeSet<Integer> set;
  private @Param("100") int size;
  private @Param("false") boolean contains;
  private Integer value;

  @Setup(Level.Invocation)
  public void setUp() {
    set = new TreeSet<>();
    value = random.nextInt(size);
    for (int i = 0; i < size; ++i) {
      if (contains || value != i) {
        set.add(i);
      }
    }
  }

  @TearDown(Level.Invocation)
  public void tearDown() {
    set.clear();
  }

  public int getSize() {
    return size;
  }

  public Integer getValue() {
    return value;
  }

  public TreeSet<Integer> getSet() {
    return set;
  }
}
