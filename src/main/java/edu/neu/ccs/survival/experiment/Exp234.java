package experiment;

import cox_hazards.COXClassifier;
import cox_hazards.COXOptimizer;
import cox_hazards.DataFormat;
import data_formatter.brfss.DSFormat;
import data_formatter.brfss.Format;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rainicy on 7/7/16.
 */
public class Exp234 {
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
            case "DSenrollee":
                data = Format.loadDSenrollee(input, config.getString("group"));
                break;
            case "cleanDS": data = DSFormat.loadCleanDS(input, config.getString("group"));
                break;
            case "O3":
                data = Format.loadO3(input, config.getString("group"));
                break;
            case "Ozone":
                data = DSFormat.loadO3(input, config.getString("group"));
                break;
            case "BRFSSPM":
                data = Format.loadBRFSSPM(input);
                break;
            case "testData8":
                data = Format.loadtestData8(input);
                break;
        }
        System.out.println("done with loading data...");
        System.out.println("Original data.");

        try {
            List<Double> knots = config.getDoubles("knots");
            System.out.println("knots is loaded by the given knots");
            data.cubicSplines(knots, config.getInt("norm"));
        } catch (Exception e) {
            System.out.println("knots is loaded by the number of knots(default quantiles)");
            data.cubicSplines(config.getInt("numKnots"),  config.getInt("norm"));
        }
        System.out.println("After spline.");
//        System.out.println(data.toString());

        long startTime = System.nanoTime();
        COXClassifier classifier = new COXClassifier(data.getNumFeatures());
        COXOptimizer loss = new COXOptimizer(classifier, data);
        loss.setTies(config.getBoolean("isTies"));
        loss.setParallelism(config.getBoolean("isParallelism"));
        LBFGS optimizer = new LBFGS(loss);
        optimizer.optimize();
        System.out.println("final average negative log likelihood: " + optimizer.getTerminator().getHistory().get(optimizer.getTerminator().getHistory().size()-1)/loss.getDeathCount());

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
        System.out.println("\tcoef \t se(ceof)");
        for (int i=0; i<ses.length; i++) {
            System.out.println("coef-" + i + "\t" + String.format("%.6f", classifier.getCoefficient().get(i)) + "\t" + String.format("%.6f", ses[i]));
        }
        long stopTime = System.nanoTime();
        long difference = stopTime - startTime;
        System.out.println("costing time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");
    }
}
