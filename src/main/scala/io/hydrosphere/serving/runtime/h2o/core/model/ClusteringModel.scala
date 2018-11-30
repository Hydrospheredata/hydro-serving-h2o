package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction.ClusteringModelPrediction
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class ClusteringModel(model: MojoModel) extends Model {
  override type Prediction = ClusteringModelPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict = predictor.predictClustering

  override def convertPrediction(prediction: ClusteringModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("cluster", Option(prediction.cluster).map(Util.toTensor))
    fieldsMutMap.maybeAddField("labelIndex", Option(prediction.distances).map(Util.toTensorDouble))
    fieldsMutMap.toMap
  }
}
