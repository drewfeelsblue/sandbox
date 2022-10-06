import Dependencies._

inThisBuild(
  List(
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.13.9",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
  )
)

lazy val app = (project in file("app"))
  .settings(
    name := "app",
    Compile / mainClass := Some("drew.feels.blue.Main"),
    scalacOptions ++= Seq(
      "-Ywarn-unused"
    ),
    libraryDependencies := Seq(
      "org.typelevel" %% "cats-effect"     % "3.3.14",
      "co.fs2"        %% "fs2-core"        % "3.3.0",
      "ch.qos.logback" % "logback-classic" % "1.4.5"
    ) ++ KafkaJournal.modules,
    libraryDependencySchemes ++= Seq(
      "org.scala-lang.modules" %% "scala-java8-compat" % "always"
    ),
  )
