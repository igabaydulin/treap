workflow "New workflow" {
  on = "push"
  resolves = ["JMH"]
}

action "JUnit" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  runs = "./lib/gradlew"
  args = "test -p ./lib"
}

action "JMH" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  needs = ["JUnit"]
  runs = "./lib/gradlew"
  args = "jmh -p ./lib"
}
