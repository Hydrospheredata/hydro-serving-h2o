name := "serving-h2o-runtime"

version := "0.1"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-Ypartial-unification", 
  "-language:higherKinds"
)

libraryDependencies ++= Dependencies.all
