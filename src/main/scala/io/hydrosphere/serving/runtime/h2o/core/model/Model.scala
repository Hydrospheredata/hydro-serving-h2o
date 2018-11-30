package io.hydrosphere.serving.runtime.h2o.core.model

import cats.effect.Sync
import cats.syntax.functor._
import cats.syntax.flatMap._
import hex.genmodel.easy.RowData
import hex.genmodel.easy.prediction.AbstractPrediction
import io.hydrosphere.serving.runtime.h2o.core.data.RowDataOps
import io.hydrosphere.serving.tensorflow.api.predict.{PredictRequest, PredictResponse}
import io.hydrosphere.serving.tensorflow.tensor.TensorProto

import scala.util.Try

trait Model extends Product with Serializable {
  type Prediction <: AbstractPrediction

  def infer[F[_]: Sync](data: PredictRequest): F[PredictResponse] = {
    for {
      converted <- Sync[F].fromTry(Try(RowDataOps.fromTensorMap(data.inputs)))
      inferResult <- Sync[F].delay(predict(converted))
      tensorResults <- Sync[F].delay(convertPrediction(inferResult))
    } yield PredictResponse(outputs = tensorResults)
  }

  def predict: RowData => Prediction

  def convertPrediction(prediction: Prediction): Map[String, TensorProto]
}