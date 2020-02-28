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
In order to run survival as an application, please download the latest [pre-complied package](https://github.com/Rainicy/survival/releases/) (with a name like **survival-x.x.x.zip**) and unzip it. Go to the unzipped forlder. You are ready to go now!

## Cox PH Linear Model Application

#### Data Sample
A sample data is located in [**sample_data/test.csv**](https://github.com/Rainicy/survival/blob/master/sample_data/test.csv), which has the following format:
```
location,date,pm,age,sex,race,total,dead,ratiodead
1,1,12.7045,71,0,1,2,0,0
1,1,12.7045,73,0,1,1,0,0
1,1,12.7045,78,0,1,1,0,0
1,1,12.7045,85,1,1,1,0,0
```

#### Config file
A Cox PH config file for training the model is included in [**config/coxph**](https://github.com/Rainicy/survival/blob/master/config/coxph): (You need assign the data_path, and predictors, stratas, death and life column names based on your data format).
```
# data path
data_path=sample_data/test.csv

# loader settings
# separated symbol for the given data
sep=,
# the predictors are included in the model
predictors=pm
# stratas
stratas=age,sex,race
# death
death=dead
# life
life=total

# optimizer settings
# whether considering ties for death, default false
isTies=false
# if given multiple cores, set isParallelism as true
isParallelism=true
# whether penalizing the weight
l2=false
# if pernalizing the weight, set the strength, otherwise it is useless.
l2Strength=1

# the application class name
survival.class=CoxPH
```

#### Train Cox PH linear model
Once the data and config file is ready, you could run the command to train a Cox PH linear model, e.g. 
```
./survival config/coxph
```
wherein __survival__ is a launcher script for the Cox PH linear model application, and __config/coxph__ is the configuration file specifying the data path, format and algorithm setup. 

#### Estimated Result
Once the model is trained from the previous step, the console outputs the estimated coefficients and standard errors. An example of the output is shown as:
```
done with loading data...
updating death
done with updating death
death/total: 2965/1011945
final average negative log likelihood: 0.027289124009878832
number of features: 1
Negative Log-likelihood History:
[27664.565091730812, 27615.097594555747, 27615.092596347815, 27615.092596176833, 27615.092596176833, 27615.092596176833, 27615.092596176833, 27615.092596176833]
Means
[11.094297994752171]
Var
0       [7.677454251439562E-5]

Results
predictor       coefficient     standard_error
pm              0.011026        0.008762
Training time: 19 sec.
```
Wherein the estimated predictor is __pm__, the estimated coefficient is __0.011026__ and the SE is __0.008762__.
