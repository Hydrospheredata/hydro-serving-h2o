package io.hydrosphere.serving.runtime.h2o

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.scalalogging.StrictLogging
import io.hydrosphere.serving.runtime.h2o.config.ApplicationConfig
import io.hydrosphere.serving.runtime.h2o.core.loader.H2OModelLoader
import io.hydrosphere.serving.runtime.h2o.grpc.GrpcServer

import scala.concurrent.ExecutionContext

object Main extends IOApp with StrictLogging {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- IO(logger.info("Hydroserving H2O model runtime"))
      config <- IO {
        logger.info("Loading configuration")
        val c = ApplicationConfig.fromEnv
        logger.info(s"Configuration: $c")
        c
      }
      model <- H2OModelLoader.findModel[IO](config.model.modelPath)
      server <- IO {
        logger.info("Starting GRPC service")
        val server = GrpcServer.build[IO](ExecutionContext.global, config.grpc, model)
        server.start()
        logger.info("Ready")
        server
      }
    } yield {
      server.awaitTermination()
      ExitCode.Success
    }
  }
}