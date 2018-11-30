package io.hydrosphere.serving.runtime.h2o.core.data

import hex.genmodel.easy.RowData
import io.hydrosphere.serving.tensorflow.tensor.{TensorProto, TypedTensorFactory}

import scala.collection.JavaConverters._

object RowDataOps {
  def fromTensorMap(map: Map[String, TensorProto]): RowData = {
    val row = new RowData()

    val rowNewData = map
      .mapValues(TypedTensorFactory.create)
      .mapValues(_.data)

    row.putAll(rowNewData.asJava)
    row
  }
}