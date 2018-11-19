name := "serving-h2o-runtime"

version := IO.read(file("version"))

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-Ypartial-unification", 
  "-language:higherKinds"
)

libraryDependencies ++= Dependencies.all
