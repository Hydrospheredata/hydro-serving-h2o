package io.hydrosphere.serving.runtime.h2o.core

import java.util

import io.hydrosphere.serving.tensorflow.TensorShape
import io.hydrosphere.serving.tensorflow.tensor._

import scala.collection.mutable
import scala.collection.JavaConverters._
import scala.reflect._

object Util {

  object HashMap {
    def addField[T](accMap: mutable.HashMap[String, T], name: String, s: Option[T]) = {
      s match {
        case Some(v) =>
          accMap += name -> v
          accMap
        case None =>
          accMap
      }
    }

    object Implicits {

      implicit final class HashMapOps[T](val accMap: mutable.HashMap[String, T]) extends AnyVal {
        def maybeAddField(name: String, s: Option[T]) = {
          addField(accMap, name, s)
        }
      }

    }

  }

  def toTensor(data: Int): TensorProto = {
    Int32Tensor(TensorShape.scalar, Seq(data)).toProto
  }

  def toTensor(data: Double): TensorProto = {
    DoubleTensor(TensorShape.scalar, Seq(data)).toProto
  }

  def toTensor(data: String): TensorProto = {
    StringTensor(TensorShape.scalar, Seq(data)).toProto
  }

  def toTensor(hmap: util.HashMap[String, Array[Float]]): TensorProto = {
    val converted = hmap.asScala.toMap.mapValues(x => FloatTensor(TensorShape.vector(x.length), x))
    MapTensor(TensorShape.scalar, Seq(converted)).toProto
  }

  def toTensorInt(data: Array[Int]): TensorProto = {
    Int32Tensor(TensorShape.vector(data.length), data).toProto
  }


  def toTensorDouble(data: Array[Double]): TensorProto = {
    DoubleTensor(TensorShape.vector(data.length), data).toProto
  }

  def toTensorString(data: Array[String]): TensorProto = {
    StringTensor(TensorShape.vector(data.length), data).toProto
  }
}