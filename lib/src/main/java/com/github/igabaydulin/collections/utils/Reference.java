package com.github.igabaydulin.collections.utils;

import java.util.Objects;

/**
 * An object wrapper to pass and change reference to the object.
 *
 * <p>by default Java does not allow to pass object's reference and change it, instead the copy of reference is passed
 *
 * <p>Example:
 *
 * <pre>{@code
 * public static void swap(Integer number1, Integer number2) {
 *   Integer temp = number1;
 *   number1 = number2;
 *   number2 = temp;
 * }
 *
 * public static void main(String[] args) {
 *   Integer number1 = 0;
 *   Integer number2 = 1;
 *
 *   System.out.println(String.format("%s %s", number1, number2));
 *
 *   swap(number1, number2);
 *   System.out.println(String.format("%s %s", number1, number2));
 * }
 * }</pre>
 *
 * <p>Proposed fix:
 *
 * <pre>{@code
 * public static void swap(Reference<Integer> number1, Reference<Integer> number2) {
 *   Integer temp = number1.get();
 *   number1.set(number2.get());
 *   number2.set(temp);
 * }
 *
 * public static void main(String[] args) {
 *   Reference<Integer> number1 = new Reference<>(0);
 *   Reference<Integer> number2 = new Reference<>(1);
 *
 *   System.out.println(String.format("%s %s", number1, number2));
 *
 *   swap(number1, number2);
 *   System.out.println(String.format("%s %s", number1, number2));
 * }
 * }</pre>
 *
 * <p>this code will not swap numbers, because only copy of references will be swapped
 *
 * <p>{@link java.util.concurrent.atomic.AtomicReference} can be used instead but take notice to volatile read/write
 * overhead
 */
public class Reference<T> {

  private T value;

  public Reference() {}

  public Reference(T value) {
    this.value = value;
  }

  public T get() {
    return value;
  }

  public void set(T value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reference<?> reference = (Reference<?>) o;
    return Objects.equals(value, reference.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
