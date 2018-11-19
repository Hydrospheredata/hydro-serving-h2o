package io.hydrosphere.serving.runtime.h2o.config

import scala.concurrent.duration._

case class ApplicationConfig(grpc: GrpcConfig, model: ModelConfig)

object ApplicationConfig {
  def fromEnv: ApplicationConfig = {
    ApplicationConfig(
      grpc = GrpcConfig(
        port = sys.env.get("APP_PORT").map(_.toInt).getOrElse(9090),
        deadlineDuration = sys.env.get("APP_DEADLINE").map(Duration.apply).getOrElse(5.seconds),
        maxMessageSize = sys.env.get("APP_MAX_MSG_SIZE").map(_.toInt).getOrElse(512 * 1024 * 1024)
      ),
      model = ModelConfig()
    )
  }
}