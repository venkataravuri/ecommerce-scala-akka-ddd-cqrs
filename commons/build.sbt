import sbt.Keys._

// Project name
name := "commons"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.9",
  "com.typesafe.akka" % "akka-stream_2.11" % "2.4.9",
  "com.typesafe.akka" % "akka-persistence_2.11" % "2.4.9",
  "com.typesafe.akka" % "akka-contrib_2.11" % "2.4.9",
  "com.typesafe.akka" % "akka-remote_2.11" % "2.4.9"
)