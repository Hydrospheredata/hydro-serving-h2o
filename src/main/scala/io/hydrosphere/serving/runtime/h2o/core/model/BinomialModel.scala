package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction.BinomialModelPrediction
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class BinomialModel(model: MojoModel) extends Model {
  override type Prediction = BinomialModelPrediction

  val predictor = new EasyPredictModelWrapper(model)

  override def predict = predictor.predictBinomial

  override def convertPrediction(prediction: BinomialModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("label", Option(prediction.label).map(Util.toTensor))
    fieldsMutMap.maybeAddField("labelIndex", Option(prediction.labelIndex).map(Util.toTensor))
    fieldsMutMap.maybeAddField("leafNodeAssignmentIds", Option(prediction.leafNodeAssignmentIds).map(Util.toTensorInt))
    fieldsMutMap.maybeAddField("leafNodeAssignments", Option(prediction.leafNodeAssignments).map(Util.toTensorString))
    fieldsMutMap.maybeAddField("calibratedClassProbabilities", Option(prediction.calibratedClassProbabilities).map(Util.toTensorDouble))
    fieldsMutMap.maybeAddField("classProbabilities", Option(prediction.classProbabilities).map(Util.toTensorDouble))
    fieldsMutMap.toMap
  }
}


