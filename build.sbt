enablePlugins(ScalaJSPlugin)

name := "scala-uri scala-js example"
scalaVersion := "2.12.2"

libraryDependencies ++=
  "org.scala-js" %%% "scalajs-dom" % "0.9.1" ::
  "io.lemonlabs" %%% "scala-uri" % "0.5.0" :: Nil

scalaJSUseMainModuleInitializer := true

lazy val compileJsTask = taskKey[Unit]("Copy index.html")

compileJsTask := {
  val from = target.value / "scala-2.12" / "scala-uri-scala-js-example-fastopt.js"
  val to = baseDirectory.value / "scala-uri-scala-js-example.js"
  IO.copyFile(from, to)
}

fastOptJS in Compile := {
  compileJsTask.value
  (fastOptJS in Compile).value
}
