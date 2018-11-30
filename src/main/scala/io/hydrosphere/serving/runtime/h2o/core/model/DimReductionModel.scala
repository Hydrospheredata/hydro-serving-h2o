package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction.DimReductionModelPrediction
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class DimReductionModel(model: MojoModel) extends Model {
  override type Prediction = DimReductionModelPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict = predictor.predictDimReduction

  override def convertPrediction(prediction: DimReductionModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("dimensions", Option(prediction.dimensions).map(Util.toTensorDouble))
    fieldsMutMap.maybeAddField("reconstructed", Option(prediction.reconstructed).map(Util.toTensorDouble))
    fieldsMutMap.toMap
  }
}