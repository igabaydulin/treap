workflow "New workflow" {
  on = "push"
  resolves = [
    "JMH",
    "JUnit",
  ]
}

action "JMH" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  args = "jmh -p ./lib"
  runs = "./lib/gradlew"
}

action "JUnit" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  runs = "./lib/gradlew"
  args = "test -p ./lib"
}
