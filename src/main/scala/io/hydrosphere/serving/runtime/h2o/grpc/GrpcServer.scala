package io.hydrosphere.serving.runtime.h2o.grpc

import cats.effect.Effect
import io.grpc.netty.NettyServerBuilder
import io.hydrosphere.serving.runtime.h2o.config.GrpcConfig
import io.hydrosphere.serving.runtime.h2o.core.model.Model
import io.hydrosphere.serving.tensorflow.api.prediction_service.PredictionServiceGrpc

import scala.concurrent.ExecutionContext

object GrpcServer {
  def build[F[_]: Effect](executionContext: ExecutionContext, grpcConfig: GrpcConfig, model: Model) = {
    val grpcImpl = new GRPCServiceImpl[F](model)
    val grpcService = PredictionServiceGrpc.bindService(grpcImpl, executionContext)
    NettyServerBuilder
      .forPort(grpcConfig.port)
      .maxMessageSize(grpcConfig.maxMessageSize)
      .addService(grpcService)
      .build()
  }
}