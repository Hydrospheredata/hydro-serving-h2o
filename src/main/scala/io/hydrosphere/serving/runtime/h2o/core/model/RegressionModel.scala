package io.hydrosphere.serving.runtime.h2o.core.model

import cats.Monad
import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import io.hydrosphere.serving.runtime.h2o.core.data.RowDataConverter
import io.hydrosphere.serving.tensorflow.api.predict.{PredictRequest, PredictResponse}

case class RegressionModel[F[_]: Monad](model: MojoModel) extends Model[F] {
  val predictor = new EasyPredictModelWrapper(model)

  override def infer(data: PredictRequest): F[PredictResponse] = {
    val convertedData = RowDataConverter.fromRequest(data)
    val result = predictor.predictRegression(convertedData)

    Monad[F].pure(PredictResponse(outputs = data.inputs))
  }
}