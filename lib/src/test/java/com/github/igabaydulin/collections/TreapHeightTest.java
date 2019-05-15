package com.github.igabaydulin.collections;

import java.util.Random;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

// TODO: Looks like the depth complexity constant most of the time is not larger than 3;
//       need to create report showing depth percentile
// TODO: Cannot combine @RepeatedTest and @ParameterizedTest;
//       see https://github.com/junit-team/junit5/issues/1224
@DisplayName("Treap height measurements")
public class TreapHeightTest {

  private static final double DEPTH_COMPLEXITY_CONSTANT = 3;
  private static final Random random = new Random();

  // TODO: Feeling dummy, might delete later :)
  @RepeatedTest(100)
  @DisplayName("Check treap depth for 1 element")
  public void check_treap_height_1() {
    int size = 1;
    Treap<Integer> treap = new TreapSet<>();
    for (int i = 0; i < size; ++i) {
      treap.add(random.nextInt(size));
    }
    check(treap);
  }

  @RepeatedTest(100)
  @DisplayName("Check treap depth for 10 elements")
  public void check_treap_height_10() {
    int size = 10;
    Treap<Integer> treap = new TreapSet<>();
    for (int i = 0; i < size; ++i) {
      treap.add(random.nextInt(size));
    }
    check(treap);
  }

  @RepeatedTest(100)
  @DisplayName("Check treap depth for 100 elements")
  public void check_treap_height_100() {
    int size = 100;
    Treap<Integer> treap = new TreapSet<>();
    for (int i = 0; i < size; ++i) {
      treap.add(random.nextInt(size));
    }

    check(treap);
  }

  @RepeatedTest(100)
  @DisplayName("Check treap depth for 1000 elements")
  public void check_treap_height_1000() {
    int size = 1000;
    Treap<Integer> treap = new TreapSet<>();
    for (int i = 0; i < size; ++i) {
      treap.add(random.nextInt(size));
    }

    check(treap);
  }

  @RepeatedTest(100)
  @DisplayName("Check treap depth for 10000 elements")
  public void check_treap_height_10000() {
    int size = 10000;
    Treap<Integer> treap = new TreapSet<>();
    for (int i = 0; i < size; ++i) {
      treap.add(random.nextInt(size));
    }

    check(treap);
  }

  public void check(Treap treap) {
    int height = treap.height();

    double actualConstant = height / (Math.log(treap.size() + 1) / Math.log(2));

    int expectedHeight = (int) (DEPTH_COMPLEXITY_CONSTANT * Math.log(treap.size() + 1) / Math.log(2));

    Assumptions.assumeTrue(
        height <= expectedHeight,
        String.format(
            "Treap height[value: %s, C: %s] must be less than [value: %s, C: %s]",
            height, actualConstant, expectedHeight, DEPTH_COMPLEXITY_CONSTANT));
  }
}
