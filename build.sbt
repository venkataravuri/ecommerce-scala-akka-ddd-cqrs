import sbt.Keys._

name := "ecommerce-scala-akka-ddd-cqrs"

organization in ThisBuild := "pl.newicom"

version in ThisBuild := "0.1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

scalacOptions in ThisBuild := Seq("-encoding", "utf8", "-feature", "-language:postfixOps", "-language:implicitConversions", "-Xlog-implicits")

sourcesInBase in ThisBuild := false

lazy val root = project.in(file(".")).aggregate(commons, cart)

// Define individual projects, the directories they reside in, and other projects they depend on

// Commons is a shared project that does not depend on any of our projects
lazy val commons = project.in(file("commons"))

// DB project depends on commons
lazy val cart = project.in(file("cart")).dependsOn(commons)

libraryDependencies in ThisBuild ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.9"
)