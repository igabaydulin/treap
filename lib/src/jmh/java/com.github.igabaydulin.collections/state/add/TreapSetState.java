package com.github.igabaydulin.collections.state.add;

import com.github.igabaydulin.collections.TreapSet;
import java.util.Random;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
public class TreapSetState {

  private Random random = new Random();
  private @Param("100") int size;
  private @Param("false") boolean contains;
  private @Param("false") boolean balanced;
  private Integer value;
  private TreapSet<Integer> set = new TreapSet<>();

  private void fill(double[] priorities, int pow) {
    for (int i = (int) (priorities.length / Math.pow(2, pow));
        i < priorities.length;
        i += (int) (priorities.length / Math.pow(2, pow)) + 1) {
      if (priorities[i] == 0) {
        priorities[i] = 1d / Math.pow(2, pow);
      }
    }

    if (priorities[0] == 0) {
      fill(priorities, ++pow);
    }
  }

  @Setup(Level.Trial)
  public void setUp() {
    value = random.nextInt(size);

    if (balanced) {
      double[] priorities = new double[size];
      fill(priorities, 1);

      for (int i = 0; i < size; ++i) {
        if (contains || value != i) {
          set.add(i, priorities[i]);
        }
      }
    } else {
      for (int i = 0; i < size; ++i) {
        if (contains || value != i) {
          set.add(i);
        }
      }
    }
  }

  @TearDown(Level.Invocation)
  public void tearDown() {
    if (set.size() != size) {
      throw new IllegalStateException(String.format("Expected: %s elements; Actual: %s elements", size, set.size()));
    } else if (!set.contains(value)) {
      throw new IllegalStateException(String.format("Set does not contains %s", value));
    }

    set.remove(value);
  }

  public TreapSet<Integer> getSet() {
    return set;
  }

  public Integer getValue() {
    return value;
  }
}
