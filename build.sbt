enablePlugins(ScalaJSPlugin)

name := "Scala.js Tutorial"

scalaVersion := "2.11.8"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

libraryDependencies += "com.thoughtworks.binding" %%% "futurebinding" % "latest.release"

libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

persistLauncher in Compile := true
