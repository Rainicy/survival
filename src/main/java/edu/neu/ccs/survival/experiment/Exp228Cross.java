package edu.neu.ccs.survival.experiment;

import edu.neu.ccs.survival.cox_hazards.COXClassifier;
import edu.neu.ccs.survival.cox_hazards.COXOptimizer;
import edu.neu.ccs.survival.cox_hazards.DataFormat;
import edu.neu.ccs.survival.data_formatter.brfss.DSFormat;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rainicy on 1/25/18.
 *
 * Cross validation on DSenrollee data to choose the best result.
 */
public class Exp228Cross {

    public static void main(String[] args) throws IOException {


        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify a properties file.");
        }

        Config config = new Config(args[0]);

        System.out.println(config);

        String inputTrain = config.getString("input.train");
        String inputTest = config.getString("input.test");

        long startTime = System.nanoTime();
        // loading data
        DataFormat trainSet = null, testSet = null;
        System.out.println("loading data...");
        switch (config.getString("load")) {
            case "cleanDS": {
                trainSet = DSFormat.loadCleanDS(inputTrain, config.getString("group"));
                testSet = DSFormat.loadCleanDS(inputTest, config.getString("group"));
                break;
            }
            case "Ozone":
                trainSet = DSFormat.loadO3(inputTrain, config.getString("group"));
                testSet = DSFormat.loadO3(inputTrain, config.getString("group"));
                break;
            default: throw new RuntimeException("wrong load data given: " + config.getString("load"));
        }
        System.out.println("done with loading data...");
        List<Double> penalties = config.getDoubles("l2Strength");
        for (double p1 : penalties) {
            for (double p2 : penalties) {
                System.out.println("p1: " + p1 + "\t" + "p2: " + p2);
                COXClassifier classifier = new COXClassifier(trainSet.getNumFeatures());
                COXOptimizer loss = new COXOptimizer(classifier, trainSet);
                loss.setTies(config.getBoolean("isTies"));
                loss.setParallelism(config.getBoolean("isParallelism"));
                loss.setL2(true);
                loss.setL2Strength(Arrays.asList(p1, p2));
                LBFGS optimizer = new LBFGS(loss);
                optimizer.optimize();
                System.out.println("final average negative log likelihood: " + optimizer.getTerminator().getHistory().get(optimizer.getTerminator().getHistory().size()-1)/loss.getDeathCount());
                double testLoss = loss.getObjectives(testSet);
                System.out.println("log of the objectives on test: \t" + Math.log(testLoss));
                try {
                    double[][] var = loss.getVar();
                    double[] ses = loss.getStandardError(var);
                    System.out.println("Results");
                    System.out.println("\tcoef \t se(ceof)");
                    for (int i=0; i<ses.length; i++) {
                        System.out.println("coef-" + i + "\t" + String.format("%.6f", classifier.getCoefficient().get(i)) + "\t" + String.format("%.6f", ses[i]));
                    }
                } catch (Exception e) {
                    System.out.println("capture exception in singular matrix");
                    System.out.println("Results");
                    System.out.println("\tcoef");
                    for (int i=0; i<classifier.getCoefficient().size(); i++) {
                        System.out.println("coef-" + i + "\t" + String.format("%.6f", classifier.getCoefficient().get(i)));
                    }
                }
                System.out.println("========================================================");
                System.out.println("********************************************************");
                System.out.println("========================================================");
            }
        }

        long stopTime = System.nanoTime();
        long difference = stopTime - startTime;
        System.out.println("costing time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");
    }
}
