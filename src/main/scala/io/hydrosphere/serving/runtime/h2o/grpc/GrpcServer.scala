package io.hydrosphere.serving.runtime.h2o.grpc

import io.grpc.netty.NettyServerBuilder
import io.hydrosphere.serving.runtime.h2o.config.GrpcConfig
import io.hydrosphere.serving.tensorflow.api.prediction_service.PredictionServiceGrpc

import scala.concurrent.ExecutionContext

object GrpcServer {
  def build(executionContext: ExecutionContext, grpcConfig: GrpcConfig) = {
    val grpcImpl = new GRPCServiceImpl()
    val grpcService = PredictionServiceGrpc.bindService(grpcImpl, executionContext)
    NettyServerBuilder
      .forPort(grpcConfig.port)
      .maxMessageSize(grpcConfig.maxMessageSize)
      .addService(grpcService)
      .build()
  }
}