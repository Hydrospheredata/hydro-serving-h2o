package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.prediction.OrdinalModelPrediction
import hex.genmodel.easy.{EasyPredictModelWrapper, RowData}
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class OrdinalModel(model: MojoModel) extends Model {
  override type Prediction = OrdinalModelPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict: RowData => OrdinalModelPrediction = predictor.predictOrdinal

  override def convertPrediction(prediction: OrdinalModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("label", Option(prediction.label).map(Util.toTensor))
    fieldsMutMap.maybeAddField("labelIndex", Option(prediction.labelIndex).map(Util.toTensor))
    fieldsMutMap.maybeAddField("classProbabilities", Option(prediction.classProbabilities).map(Util.toTensorDouble))
    fieldsMutMap.toMap
  }
}

