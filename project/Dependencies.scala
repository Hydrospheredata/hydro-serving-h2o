import sbt._

object Dependencies {
  lazy val h2oV = "3.22.0.1"

  lazy val h2oDeps = Seq(
    "ai.h2o" % "h2o-algos" % h2oV,
    "ai.h2o" % "h2o-app" % h2oV,
    "ai.h2o" % "h2o-core" % h2oV,
    "ai.h2o" % "h2o-genmodel" % h2oV,
    "ai.h2o" % "h2o-persist-hdfs" % h2oV,
    "ai.h2o" % "h2o-web" % h2oV
  )
  lazy val catsDeps = Seq(
    "org.typelevel" %% "cats-core" % "1.4.0",
    "org.typelevel" %% "cats-effect" % "1.0.0"
  )
  lazy val grpcDeps = Seq(
    "io.hydrosphere" %% "serving-grpc-scala" % "0.1.23"
  )
  lazy val logDeps = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  )
  lazy val testDeps = Seq(
    "org.scalactic" %% "scalactic" % "3.0.5",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

  lazy val all = h2oDeps ++ catsDeps ++ grpcDeps ++ logDeps ++ testDeps
}
