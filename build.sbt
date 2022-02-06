enablePlugins(ScalaJSPlugin)

name := "scala-uri Scala.js example"
scalaVersion := "3.1.1"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++=
  "org.scala-js" %%% "scalajs-dom" % "2.1.0" ::
  "io.lemonlabs" %%% "scala-uri" % "4.0.0-M4" :: Nil

scalaJSUseMainModuleInitializer := true

lazy val compileJs = taskKey[Unit]("Compile the project")
lazy val copyIndex = taskKey[Unit]("Copy index.html")

copyIndex := {
  val from = target.value / s"scala-${scalaVersion.value}" / "scala-uri-scala-js-example-fastopt.js"
  val to = baseDirectory.value / "scala-uri-scala-js-example.js"
  IO.copyFile(from, to)
}

compileJs := Def.sequential(
  Compile / fastOptJS,
  copyIndex
).value
