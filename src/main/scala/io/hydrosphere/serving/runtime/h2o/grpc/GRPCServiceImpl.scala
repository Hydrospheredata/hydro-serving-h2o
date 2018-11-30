package io.hydrosphere.serving.runtime.h2o.grpc

import cats.effect.Effect
import cats.effect.syntax.effect._
import com.typesafe.scalalogging.StrictLogging
import io.hydrosphere.serving.runtime.h2o.core.model.Model
import io.hydrosphere.serving.tensorflow.api.predict.{PredictRequest, PredictResponse}
import io.hydrosphere.serving.tensorflow.api.prediction_service.PredictionServiceGrpc.PredictionService

import scala.concurrent.Future

class GRPCServiceImpl[F[_]: Effect](
  val model: Model
) extends PredictionService with StrictLogging {
  override def predict(request: PredictRequest): Future[PredictResponse] = {
    logger.info(s"Got request modelSpec=${request.modelSpec}")
    model.infer(request).toIO.unsafeToFuture()
  }
}