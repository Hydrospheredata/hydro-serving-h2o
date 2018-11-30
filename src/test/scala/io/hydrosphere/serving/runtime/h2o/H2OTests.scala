package io.hydrosphere.serving.runtime.h2o

import hex.genmodel.MojoModel
import hex.genmodel.easy.prediction.BinomialModelPrediction
import hex.genmodel.easy.{EasyPredictModelWrapper, RowData}
import org.scalatest.FunSpec


class H2OTests extends FunSpec {
  describe("h2o") {
    it("load binomial MOJO and predict something") {
      val model = MojoModel.load("models/gbm_pojo_test.zip")
      val predictor = new EasyPredictModelWrapper(model)
      println(predictor.getModelCategory)

      val data = new RowData()
      data.put("kek", Array(1, 2, 3, 4))
      val result = predictor.predict(data).asInstanceOf[BinomialModelPrediction]
      println(result)
    }
  }
}