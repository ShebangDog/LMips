name := "LMips"

version := "0.1"

scalaVersion := "2.13.4"

scalacOptions += "-Xfatal-warnings"

libraryDependencies ++= Seq(
// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-parser-combinators
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.scalatest" %% "scalatest-funspec" % "3.2.16" % "test"
)


