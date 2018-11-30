package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.EasyPredictModelWrapper
import hex.genmodel.easy.prediction.AutoEncoderModelPrediction
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class AutoencoderModel(model: MojoModel) extends Model {
  override type Prediction = AutoEncoderModelPrediction

  val predictor = new EasyPredictModelWrapper(model)

  override def predict = predictor.predictAutoEncoder

  override def convertPrediction(prediction: AutoEncoderModelPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("original", Option(prediction.original).map(Util.toTensorDouble))
    fieldsMutMap.maybeAddField("reconstructed", Option(prediction.reconstructed).map(Util.toTensorDouble))
    fieldsMutMap.toMap
  }
}
