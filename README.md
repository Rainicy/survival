# Survival Analysis

## A Java Survival Analysis Library

This package highly depends on [Pyramid Package](https://github.com/cheng-li/pyramid) (version 0.8.4), a Java Machine Learning Library.

This library includes the following algorithms (keep updating):

* Cox Proportional Hazards (CoxPH) Linear model
  * [A Note on Cox PH](http://rainicy.github.io/docs/survival/Cox.pdf)

* Cox Proportional Hazards Non-linear model (RCS-CoxPH)
  * [Supplementary on Restricted Cubic Splines](https://static-content.springer.com/esm/art%3A10.1186%2Fs12940-020-00575-0/MediaObjects/12940_2020_575_MOESM1_ESM.docx)
  
* Poisson Log-Linear model 

The package implements the Cox PH for both linear and non-linear models described in the papper, especially in the Supplementary Information part.

[**The impact of long-term PM2.5 exposure on specific causes of death: exposure-response curves and effect modification among 53 million U.S. Medicare beneficiaries**](https://link.springer.com/article/10.1186/s12940-020-00575-0)

Bingyu Wang, Ki-Do Eum, Fatemeh Kazemiparkouhi, Cheng Li, Justin Manjourides, Virgil Pavlu & Helen Suh 
Environ Health (2020)

#### To cite this article
```
Wang, B., Eum, K., Kazemiparkouhi, F. et al. The impact of long-term PM2.5 exposure on specific causes of death: exposure-response curves and effect modification among 53 million U.S. Medicare beneficiaries. Environ Health 19, 20 (2020). https://doi.org/10.1186/s12940-020-00575-0
```

## Requirements
[**Application Only**] if you just want to use survival as a tool on your dataset, you need [Java 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

[**Development**] if you need modify the code and extend for your own application, you need [Java 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) and [Maven](https://maven.apache.org/).

## Setup
In order to run survival as an application, please download the latest pre-complied package (with a name like **survival-x.x.x.zip**) and unzip it. Go to the unzipped forlder. You are ready to go now!

## Cox PH Linear Model Application
