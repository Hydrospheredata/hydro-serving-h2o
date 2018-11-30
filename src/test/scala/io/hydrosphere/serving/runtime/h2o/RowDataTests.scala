package io.hydrosphere.serving.runtime.h2o

import io.hydrosphere.serving.runtime.h2o.core.data.RowDataOps
import io.hydrosphere.serving.tensorflow.TensorShape
import io.hydrosphere.serving.tensorflow.api.predict.PredictRequest
import io.hydrosphere.serving.tensorflow.tensor.{Int32Tensor, StringTensor}
import org.scalatest.FunSpec

class RowDataTests extends FunSpec {
  describe("Conversions") {
    it("should convert Map[String, TensorProto] to RowData") {
      val request = PredictRequest(
        inputs = Map(
          "input-a" -> Int32Tensor(TensorShape.scalar, Seq(1)).toProto,
          "input-b" -> StringTensor(TensorShape.vector(-1), Seq("hello", "world")).toProto,
        )
      )
      val rowData = RowDataOps.fromTensorMap(request.inputs)
      assert(rowData.containsKey("input-a"))
      assert(rowData.containsKey("input-b"))
    }
  }
}
