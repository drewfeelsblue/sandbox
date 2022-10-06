import sbt._

object Dependencies {
  abstract class Dependency(organization: String, version: String)(artifactNames: String*) {
    final def modules: Set[ModuleID] = artifactNames.toSet[String].map(organization %% _ % version)
  }

  object KafkaJournal
      extends Dependency("com.evolutiongaming", "1.0.7")(
        "kafka-journal",
        "kafka-journal-persistence",
        "kafka-journal-replicator",
        "kafka-journal-eventual-cassandra"
      )
}
