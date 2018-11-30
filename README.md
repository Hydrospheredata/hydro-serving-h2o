# Hydroserving H2O model runtime

This runtime reads MOJO models exported from H2O and serves it
as GRPC PredictionService.

The list of supported model categories (from h2o-genmodel:3.22.0.1): 
```java
public enum ModelCategory {
  Unknown,
  Binomial,
  Multinomial,
  Ordinal,
  Regression,
  Clustering,
  AutoEncoder,
  DimReduction,
  WordEmbedding,
  CoxPH,
  AnomalyDetection
}
```

More about MOJO and Productionizing H2O - http://docs.h2o.ai/h2o/latest-stable/h2o-docs/productionizing.html