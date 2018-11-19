package io.hydrosphere.serving.runtime.h2o.core.model

import io.hydrosphere.serving.tensorflow.api.predict.{PredictRequest, PredictResponse}

trait Model[F[_]] {
  def infer(data: PredictRequest): F[PredictResponse]
}
