enablePlugins(ScalaJSPlugin)

name := "scala-uri scala-js example"
scalaVersion := "2.12.4"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++=
  "org.scala-js" %%% "scalajs-dom" % "0.9.4" ::
  "io.lemonlabs" %%% "scala-uri" % "2.0.0-M3" :: Nil

scalaJSUseMainModuleInitializer := true

lazy val compileJs = taskKey[Unit]("Compile the project")
lazy val copyIndex = taskKey[Unit]("Copy index.html")

copyIndex := {
  val from = target.value / "scala-2.12" / "scala-uri-scala-js-example-fastopt.js"
  val to = baseDirectory.value / "scala-uri-scala-js-example.js"
  IO.copyFile(from, to)
}

compileJs := Def.sequential(
  fastOptJS in Compile,
  copyIndex
).value
