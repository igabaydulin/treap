workflow "New workflow" {
  on = "push"
  resolves = ["JMH"]
}

action "JMH" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  args = "jmh -p ./lib"
  runs = "./lib/gradlew"
}
