import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.atleastonce",
      scalaVersion := "2.12.6",
      version      := "0.1.0"
    )),
    name := "avro-ivr-spikes",
    libraryDependencies ++= Seq(
      "mysql" % "mysql-connector-java" % "5.1.38",
      "io.getquill" %% "quill-jdbc" % "1.3.0",
      "com.github.pathikrit" %% "better-files" % "3.4.0",
      "com.typesafe" % "config" % "1.3.1",
      "org.apache.avro" % "avro" % "1.8.2"
    )
  )
