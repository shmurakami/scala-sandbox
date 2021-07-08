import sbt._
import Keys._

val myScalaVersion = "2.13.6"

val akkaVersion = "2.6.14"
val akkaHttpVersion = "10.2.4"

val app = (project in file("app"))
  .settings(
    scalaVersion := myScalaVersion,
    name := "app",
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %% "airframe" % "21.5.4",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-testkit" % "2.6.14" % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.14" % Test,
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
),
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
    )
  )

val fp = (project in file("fp"))
  .settings(
    scalaVersion := myScalaVersion,
    name := "fp",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.9",
      "org.scalatest" %% "scalatest" % "3.2.9" % "test"
    )
  )


val root = (project in file("."))
  .settings(
    name := "scala-sandbox",
    scalaVersion := myScalaVersion
  )
  .aggregate(
    app,
    fp
  )
