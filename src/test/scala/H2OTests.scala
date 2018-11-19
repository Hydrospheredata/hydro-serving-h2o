import org.scalatest.FunSpec

import hex.genmodel.easy.RowData
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction._
import hex.genmodel.MojoModel


class H2OTests extends FunSpec {
  describe("h2o") {
    it("should perform simple load workflow") {
      val model = MojoModel.load("asdasd")
      val predictor = new EasyPredictModelWrapper(model)
      val data = new RowData()
      data.put("kek", "asd")
      model.getModelCategory
      val result = predictor.predict(data)
      result
    }
  }
}
