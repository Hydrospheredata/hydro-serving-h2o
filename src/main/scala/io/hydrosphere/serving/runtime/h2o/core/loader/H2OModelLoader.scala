package io.hydrosphere.serving.runtime.h2o.core.loader

import cats.{Applicative, Monad}
import io.hydrosphere.serving.runtime.h2o.core.model.{H2OModel, Model}

class H2OModelLoader[L[_]: Applicative, F[_]: Monad] extends ModelLoader[L, F] {
  override def load(modelPath: String): L[Model[F]] = {
    Applicative[L].pure(H2OModel[F](modelPath))
  }
}
