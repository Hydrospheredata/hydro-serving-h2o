package io.hydrosphere.serving.runtime.h2o

import cats.effect.IO
import io.hydrosphere.serving.runtime.h2o.core.loader.H2OModelLoader
import io.hydrosphere.serving.tensorflow.api.predict.PredictRequest
import org.scalatest.FunSpec

import scala.concurrent.ExecutionContext.Implicits._

class ModelTests extends FunSpec {
  describe("Models") {
    it("should load and test") {
      val f = for {
        model <- H2OModelLoader.load[IO]("./models/gbm_pojo_test.zip")
        result <- model.infer[IO](PredictRequest.defaultInstance)
      } yield {
        println(model)
        println(result.outputs("label").stringVal.head.toStringUtf8)
      }
      f.unsafeToFuture()
    }
  }
}