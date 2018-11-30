package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction.AnomalyDetectionPrediction
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class AnomalyDetectionModel(model: MojoModel) extends Model {
  override type Prediction = AnomalyDetectionPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict = predictor.predictAnomalyDetection

  override def convertPrediction(prediction: AnomalyDetectionPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("score", Option(prediction.score).map(Util.toTensor))
    fieldsMutMap.maybeAddField("normalizedScore", Option(prediction.normalizedScore).map(Util.toTensor))
    fieldsMutMap.maybeAddField("leafNodeAssignmentIds", Option(prediction.leafNodeAssignmentIds).map(Util.toTensorInt))
    fieldsMutMap.maybeAddField("leafNodeAssignments", Option(prediction.leafNodeAssignments).map(Util.toTensorString))
    fieldsMutMap.toMap
  }
}