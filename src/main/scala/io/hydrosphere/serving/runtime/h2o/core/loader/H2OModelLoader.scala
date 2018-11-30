package io.hydrosphere.serving.runtime.h2o.core.loader

import java.nio.file.{Files, Paths}

import cats.effect.Sync
import cats.{Monad, MonadError}
import cats.syntax.applicative._
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.typesafe.scalalogging.LazyLogging
import hex.ModelCategory
import hex.genmodel.MojoModel
import io.hydrosphere.serving.runtime.h2o.core.model._

import scala.collection.JavaConverters._
import scala.util.Try

object H2OModelLoader extends LazyLogging {
  def findModel[F[_] : Sync](dirPath: String): F[Model] = {
    for {
      files <- Files.list(Paths.get(dirPath)).iterator().asScala.toSeq.pure
      zipAr <- files.find(_.toString.endsWith(".zip")) match {
        case Some(file) => file.toString.pure[F]
        case None => MonadError[F, Throwable].raiseError[String](new IllegalArgumentException("Can't find a model zip archive"))
      }
      mojo <- load(zipAr)
    } yield mojo
  }

  def load[F[_] : Sync](modelPath: String): F[Model] = {
    for {
      _ <- Monad[F].pure(logger.info(s"Loading model $modelPath"))
      mojo <- Sync[F].fromTry(Try(MojoModel.load(modelPath)))
      wrapped <- wrapMojo(mojo)
    } yield wrapped
  }

  def wrapMojo[F[_] : Sync](mojo: MojoModel): F[Model] = Sync[F].delay {
    mojo.getModelCategory match {
      case ModelCategory.AnomalyDetection => AnomalyDetectionModel(mojo)
      case ModelCategory.AutoEncoder => AutoencoderModel(mojo)
      case ModelCategory.Binomial => BinomialModel(mojo)
      case ModelCategory.Clustering => ClusteringModel(mojo)
      case ModelCategory.DimReduction => DimReductionModel(mojo)
      case ModelCategory.Multinomial => MultinomialModel(mojo)
      case ModelCategory.Ordinal => OrdinalModel(mojo)
      case ModelCategory.Regression => RegressionModel(mojo)
      case ModelCategory.WordEmbedding => Word2VecModel(mojo)
      case x => throw new IllegalArgumentException(s"Unsupported model category: $x")
    }
  }
}