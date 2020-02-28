package edu.neu.ccs.survival.experiment;

import edu.neu.ccs.survival.cox_hazards.COXClassifier;
import edu.neu.ccs.survival.cox_hazards.COXOptimizer;
import edu.neu.ccs.survival.cox_hazards.DataFormat;
import edu.neu.ccs.survival.data_formatter.brfss.Format;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rainicy on 1/18/17.
 */
public class Exp251 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify a properties file.");
        }

        Config config = new Config(args[0]);

        System.out.println(config);

        String input = config.getString("input");

        // loading data
        DataFormat data = null;
        System.out.println("loading data...");
        switch (config.getString("load")) {
            case "DSenrollee": data = Format.loadDSenrolleeByThreshold(input, config.getString("group"), config.getDouble("threshold"));
                break;
            default: throw new RuntimeException("wrong load data given: " + config.getString("load"));
        }
        System.out.println("done with loading data...");

        long startTime = System.nanoTime();
        COXClassifier classifier = new COXClassifier(data.getNumFeatures());
        COXOptimizer loss = new COXOptimizer(classifier, data);
        loss.setTies(config.getBoolean("isTies"));
//        GradientDescent gradientDescent = new GradientDescent(optimizer);
        LBFGS optimizer = new LBFGS(loss);
        optimizer.optimize();
//        for (int i=0; i<config.getInt("iterations"); i++){
//            System.out.println(i + ": " + loss.getValue());
//            optimizer.iterate();
//        }

        System.out.println("History: \n" + optimizer.getTerminator().getHistory());
        System.out.println("\nFinal result:\n"  + "coefficients: " + classifier.getCoefficient() + "\tstandard errors: " + loss.getStandardError());
        long stopTime = System.nanoTime();
        long difference = stopTime - startTime;
        System.out.println("costing time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");
    }
}
