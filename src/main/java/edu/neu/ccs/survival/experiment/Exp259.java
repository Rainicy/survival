package experiment;

import data_formatter.poisson.Format;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;
import poisson.DataFormat;
import poisson.PoissonClassifier;
import poisson.PoissonOptimizer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rainicy on 6/20/17.
 */
public class Exp259 {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify a properties file.");
        }

        Config config = new Config(args[0]);
        System.out.println(config);

        String input = config.getString("input");
        int numData = config.getInt("numData");
        int numFeatures = config.getInt("numFeatures");

        DataFormat data = null;
        System.out.println("loading data...");
        switch (config.getString("load")) {
            case "testData8":
                data = Format.loadTestData8(input,numData,numFeatures);
                break;
            case "testPrev":
                data = Format.loadPrev(input, numData, numFeatures);
                break;
            case "testPrev1":
                data = Format.loadPrev1(input, numData, numFeatures);
                break;
            case "o3c":
                data = Format.loadO3(input, numData, numFeatures);
                break;
            case "o3cms":
                data = Format.loadO3cms(input, numData, numFeatures);
                break;
            default: throw new RuntimeException("wrong load data given: " + config.getString("load"));
        }

        System.out.println("done with loading data...");

        long startTime = System.nanoTime();
        PoissonClassifier classifier = new PoissonClassifier(numFeatures);
        PoissonOptimizer loss = new PoissonOptimizer(classifier, data);
        System.out.println("initial loss: " + loss.getValue());
        System.out.println("initial gradient: " + loss.getGradient());
        LBFGS optimizer = new LBFGS(loss);
//        optimizer.optimize();

        long stopTime = System.nanoTime();

        for (int i=0; i<config.getInt("iter"); i++) {
            optimizer.iterate();
            System.out.println(i + "\t" + "obj: " + optimizer.getFinalObjective() + "\tbeta: " + classifier.getCoefficient());
        }

        System.out.println("Results");
        System.out.println("objectives: " + optimizer.getFinalObjective() + "\tcoefs: " + classifier.getCoefficient());
        long difference = stopTime - startTime;
        System.out.println("costing time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");


    }

}
