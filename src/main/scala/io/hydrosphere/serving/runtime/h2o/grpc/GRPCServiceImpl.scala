package io.hydrosphere.serving.runtime.h2o.grpc

import com.typesafe.scalalogging.StrictLogging
import io.hydrosphere.serving.tensorflow.api.predict.{PredictRequest, PredictResponse}
import io.hydrosphere.serving.tensorflow.api.prediction_service.PredictionServiceGrpc.PredictionService

import scala.concurrent.Future

class GRPCServiceImpl extends PredictionService with StrictLogging {
  override def predict(request: PredictRequest): Future[PredictResponse] = {
    logger.info(s"Got request modelSpec=${request.modelSpec}")
    Future.failed(???)
  }
}
