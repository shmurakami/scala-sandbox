val myScalaVersion = "2.13.6"

val app = (project in file("app"))
  .settings(
    name := "app",
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %% "airframe" % "21.5.4",
      "com.typesafe.akka" %% "akka-actor" % "2.6.14",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",
      "com.typesafe.akka" %% "akka-testkit" % "2.6.14" % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.14" % Test,
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    )
  )

val root = (project in file("."))
  .settings(
    name := "learning-airframe",
    scalaVersion := myScalaVersion
  )
  .aggregate(
    app
  )
