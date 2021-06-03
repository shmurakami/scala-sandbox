val myScalaVersion = "2.13.6"

libraryDependencies := Seq(
  "org.wvlet.airframe" %% "airframe" % "21.5.4"
)

val app = (project in file("app"))
  .settings(
    name := "app"
  )

val root = (project in file("."))
  .settings(
    name := "learning-airframe",
    scalaVersion := myScalaVersion
  )
  .aggregate(
    app
  )
