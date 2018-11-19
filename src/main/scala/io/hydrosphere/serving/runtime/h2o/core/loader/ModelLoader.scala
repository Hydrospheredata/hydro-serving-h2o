package io.hydrosphere.serving.runtime.h2o.core.loader

import io.hydrosphere.serving.runtime.h2o.core.model.Model

trait ModelLoader[L[_], F[_]] {
  def load(modelPath: String): L[Model[F]]
}
