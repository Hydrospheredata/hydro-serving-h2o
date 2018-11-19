package io.hydrosphere.serving.runtime.h2o.config

import scala.concurrent.duration.Duration

case class GrpcConfig(
  port: Int = 9090,
  deadlineDuration: Duration,
  maxMessageSize: Int
)
