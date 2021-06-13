enablePlugins(ScalaJSPlugin)

name := "scala-uri Scala.js example"
scalaVersion := "2.13.6"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++=
  "org.scala-js" %%% "scalajs-dom" % "1.1.0" ::
  "io.lemonlabs" %%% "scala-uri" % "3.4.0" :: Nil

scalaJSUseMainModuleInitializer := true

lazy val compileJs = taskKey[Unit]("Compile the project")
lazy val copyIndex = taskKey[Unit]("Copy index.html")

copyIndex := {
  val from = target.value / "scala-2.13" / "scala-uri-scala-js-example-fastopt.js"
  val to = baseDirectory.value / "scala-uri-scala-js-example.js"
  IO.copyFile(from, to)
}

compileJs := Def.sequential(
  fastOptJS in Compile,
  copyIndex
).value
