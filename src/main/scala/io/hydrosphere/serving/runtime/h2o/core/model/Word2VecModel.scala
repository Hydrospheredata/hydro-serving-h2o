package io.hydrosphere.serving.runtime.h2o.core.model

import hex.genmodel.MojoModel
import hex.genmodel.easy.prediction.Word2VecPrediction
import hex.genmodel.easy.{EasyPredictModelWrapper, RowData}
import io.hydrosphere.serving.runtime.h2o.core.Util
import io.hydrosphere.serving.runtime.h2o.core.Util.HashMap.Implicits._
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable

case class Word2VecModel(model: MojoModel) extends Model {
  override type Prediction = Word2VecPrediction
  val predictor = new EasyPredictModelWrapper(model)

  override def predict: RowData => Word2VecPrediction = predictor.predictWord2Vec

  override def convertPrediction(prediction: Word2VecPrediction): Map[String, TensorProto] = {
    val fieldsMutMap = mutable.HashMap.empty[String, TensorProto]
    fieldsMutMap.maybeAddField("wordEmbeddings", Option(prediction.wordEmbeddings).map(Util.toTensor))
    fieldsMutMap.toMap
  }
}