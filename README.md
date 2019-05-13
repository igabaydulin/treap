# Treap [![Version](https://img.shields.io/badge/Version-0.1.M1-color.svg)](https://github.com/igabaydulin/treap) [![Version](https://img.shields.io/badge/Java-OpenJDK%2012.0.1-dd0000.svg?logo=java)](https://jdk.java.net/12/) [![Version](https://img.shields.io/badge/Gradle-5.4.1-1ba8cb.svg)](https://docs.gradle.org/5.4.1/release-notes.html)

**Treap** is a randomized binary search tree
Each treap's node contains two parameters: *key* (which is used for binary search) and *priority* (which is chosen randomly). While
*keys* are used as BST operator *priorities* are used as Heap operator (hence the name treap: tree + heap)

<p align="center">
  <img src="https://github.com/igabaydulin/treap/blob/master/resources/treap_illustration.svg">
</p>

Sources:
* https://cp-algorithms.com/data_structures/treap.html
* https://e-maxx.ru/algo/treap
* https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree
* https://en.wikipedia.org/wiki/Treap

## Table of Contents
* [Project Hierarchy](#project-hierarchy)
* [Testing](#testing)
* [JMH](#jmh)
  * [Configuration](#configuration)
  * [Execution](#execution)

## Project Hierarchy
```bash
.
└── lib
    ├── build.gradle
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradlew
    ├── gradlew.bat
    ├── jmh.gradle                         # JMH configuration
    ├── settings.gradle
    └── src
        ├── jmh                            # JMH sources
        ├── main                           # library sources
        └── test                           # test sources
```

## Testing
```bash
./lib/gradlew clean test -p ./lib
```

## JMH ([jmh-gradle-plugin](https://github.com/melix/jmh-gradle-plugin))
### Configuration
To configure JMH execution `jmh.gradle` can be used:
```gradle
jmh {
    warmupForks = 2
    warmupIterations = 4
    iterations = 4
    fork = 4
}
```
List of all configuration options is [here](https://github.com/melix/jmh-gradle-plugin#configuration-options)

### Execution
```bash
./lib/gradlew --no-daemon clean jmh -p ./lib
```
