package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.prediction.RegressionModelPrediction
import hex.genmodel.easy.{EasyPredictModelWrapper, RowData}
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class RegressionModel(model: MojoModel) extends Model {
  override type Prediction = RegressionModelPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict: RowData => RegressionModelPrediction = predictor.predictRegression

  override def convertPrediction(prediction: RegressionModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("value", Option(prediction.value).map(Util.toTensor))
    fieldsMutMap.maybeAddField("leafNodeAssignmentIds", Option(prediction.leafNodeAssignmentIds).map(Util.toTensorInt))
    fieldsMutMap.maybeAddField("leafNodeAssignments", Option(prediction.leafNodeAssignments).map(Util.toTensorString))
    fieldsMutMap.toMap
  }
}



