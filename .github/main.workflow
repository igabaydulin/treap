workflow "JMH Workflow" {
  resolves = ["JMH"]
  on = "push"
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
