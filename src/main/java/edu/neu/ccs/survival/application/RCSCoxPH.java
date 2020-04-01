package edu.neu.ccs.survival.application;

import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;
import edu.neu.ccs.survival.cox_hazards.COXClassifier;
import edu.neu.ccs.survival.cox_hazards.COXOptimizer;
import edu.neu.ccs.survival.cox_hazards.DataFormat;
import edu.neu.ccs.survival.data_formatter.Loader;
import edu.neu.ccs.survival.data_formatter.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Non-linear Cox PH model, which implements the Restricted Cubic Splines.
 * More restricted cubic splines can be found in the supplementary from paper:
 * ```
 * The impact of long-term PM2.5 exposure on specific causes of death:
 * exposure-response curves and effect modification among 53 million U.S. Medicare beneficiaries
 * ```
 */
public class RCSCoxPH {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify a properties file.");
        }

        Config config = new Config(args[0]);

        System.out.println(config);

        // the data set path, in csv format
        String input = config.getString("data_path");
        // predictors names included in the model
        List<String> predictors = config.getStrings("predictors");
        // strats names included in the model
        List<String> stratas = config.getStrings("stratas");
        // death name, e.g. Yall
        String death = config.getString("death");
        // life name, e.g. N
        String life = config.getString("life");
        // separated symbol
        String sep = config.getString("sep");

        // load the data by assigning the predictors, strata, death and life column names.
        DataFormat data = Loader.loadPredictorsAndStrata(input,
                Util.getColumnIndexByName(input, predictors, sep),
                Util.getColumnIndexByName(input, stratas, sep),
                Util.getColumnIndexByName(input, death, sep),
                Util.getColumnIndexByName(input, life, sep),
                sep);
        System.out.println("done with loading data.");

        // knots
        List<Double> knots = config.getDoubles("knots");
        // normalization
        int norm = config.getInt("norm");
        data.cubicSplines(knots, norm);
        System.out.println("data is expanded by the given knots.");

        long startTime = System.nanoTime();
        // Cox PH model
        COXClassifier classifier = new COXClassifier(data.getNumFeatures());
        // Optimizer for training Cox PH model
        COXOptimizer loss = new COXOptimizer(classifier, data);
        loss.setTies(config.getBoolean("isTies"));
        loss.setParallelism(config.getBoolean("isParallelism"));
        loss.setL2(config.getBoolean("l2"));
        List<Double> listStrength = config.getDoubles("l2Strength");
        loss.setL2Strength(listStrength);
        LBFGS optimizer = new LBFGS(loss);
        optimizer.optimize();
        System.out.println("final average negative log likelihood: " +
                optimizer.getTerminator().getHistory().get(
                        optimizer.getTerminator().getHistory().size()-1)/loss.getTotalCount());

        try {
            double[][] var = loss.getVar();
            double[] ses = loss.getStandardError(var);
            System.out.println("Negative Log-likelihood History: \n" + optimizer.getTerminator().getHistory());
            System.out.println("Means");
            System.out.println(Arrays.toString(data.getMeans()));

            System.out.println("Var");
            for (int i=0; i<var.length; i++) {
                System.out.println(i + "\t" + Arrays.toString(var[i]));
            }

            System.out.println("Results");
            System.out.println("predictor\tcoefficient\tstandard_error");
            for (int i=0; i<ses.length; i++) {
                String predictor = "";
                if (i < predictors.size()) {
                    predictor = predictors.get(i);
                } else {
                    predictor = predictors.get(0) + "-" + (i+1-predictors.size());
                }
                System.out.println(predictor+"\t" +
                        String.format("%.6f", classifier.getCoefficient().get(i)) + "\t" + String.format("%.6f", ses[i]));
            }
        } catch (Exception e) {
            System.out.println("Capture exception in the singular matrix.");
            System.out.println("Results with standard error");
            System.out.println("predictor\tcoefficient");
            for (int i=0; i<classifier.getCoefficient().size(); i++) {
                String predictor = "";
                if (i < predictors.size()) {
                    predictor = predictors.get(i);
                } else {
                    predictor = predictors.get(0) + "-" + (i+1-predictors.size());
                }
                System.out.println(predictor+"\t" +
                        String.format("%.6f", classifier.getCoefficient().get(i)));
            }
        }
        long stopTime = System.nanoTime();
        long difference = stopTime - startTime;
        System.out.println("Training time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");
    }
}
