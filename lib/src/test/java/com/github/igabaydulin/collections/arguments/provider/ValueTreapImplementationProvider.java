package com.github.igabaydulin.collections.arguments.provider;

import com.github.igabaydulin.collections.TreapMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class ValueTreapImplementationProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(Arguments.of(new TreapMap<Integer, Integer>()));
  }
}
