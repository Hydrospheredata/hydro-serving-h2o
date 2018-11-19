package io.hydrosphere.serving.runtime.h2o

import com.typesafe.scalalogging.StrictLogging
import io.hydrosphere.serving.runtime.h2o.config.ApplicationConfig
import io.hydrosphere.serving.runtime.h2o.grpc.GrpcServer

import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

object Main extends App with StrictLogging {
  try {
    logger.info("Hydroserving H2O model runtime")
    logger.info("Loading configuration")
    val config = ApplicationConfig.fromEnv
    logger.info(s"Configuration: $config")

    logger.info("Starting GRPC service")
    val server = GrpcServer.build(ExecutionContext.global, config.grpc)
    server.start()

    sys.addShutdownHook {
      logger.info("Shutting down...")
      server.shutdownNow()
    }

    logger.info("Ready")
    server.awaitTermination()
  } catch {
    case NonFatal(ex) =>
      logger.error("Can't start.", ex)
  }
}
