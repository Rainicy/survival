package experiment;

import cox_hazards.COXClassifier;
import cox_hazards.COXOptimizer;
import cox_hazards.DataFormat;
import data_formatter.brfss.DSFormat;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rainicy on 1/25/18.
 */
public class Exp228Test {

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
            case "cleanDS": data = DSFormat.loadCleanDS(input, config.getString("group"));
                break;
            default: throw new RuntimeException("wrong load data given: " + config.getString("load"));
        }
        System.out.println("done with loading data...");
//        System.out.println(data);

        long startTime = System.nanoTime();
        COXClassifier classifier = new COXClassifier(data.getNumFeatures());
        // just test and initialize it with the best coefficients:
        System.out.println("Initialize the model by the given values: pmc: 0.029105;  no2c: 0.025913");
        classifier.getCoefficient().set(0, 0.029105);
        classifier.getCoefficient().set(1, 0.025913);
        COXOptimizer loss = new COXOptimizer(classifier, data);
        loss.setTies(config.getBoolean("isTies"));
        loss.setParallelism(config.getBoolean("isParallelism"));
        loss.setL2(config.getBoolean("l2"));
        List<Double> listStrength = config.getDoubles("l2Strength");
        loss.setL2Strength(listStrength);
        LBFGS optimizer = new LBFGS(loss);
//        optimizer.getTerminator().setMode(Terminator.Mode.FINISH_MAX_ITER);
//        optimizer.getTerminator().setMaxIteration(config.getInt("iter"));
        optimizer.optimize();
        System.out.println("final average negative log likelihood: " + optimizer.getTerminator().getHistory().get(optimizer.getTerminator().getHistory().size()-1)/loss.getDeathCount());

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
            System.out.println("\tcoef \t se(ceof)");
            for (int i=0; i<ses.length; i++) {
                System.out.println("coef-" + i + "\t" + String.format("%.6f", classifier.getCoefficient().get(i)) + "\t" + String.format("%.6f", ses[i]));
            }
        } catch (Exception e) {
            System.out.println("capture exception in singular matrix");
            System.out.println("Negative Log-likelihood History: \n" + optimizer.getTerminator().getHistory());
            System.out.println("Means");
            System.out.println(Arrays.toString(data.getMeans()));
            System.out.println("Results");
            System.out.println("\tcoef");
            for (int i=0; i<classifier.getCoefficient().size(); i++) {
                System.out.println("coef-" + i + "\t" + String.format("%.6f", classifier.getCoefficient().get(i)));
            }
        }



        long stopTime = System.nanoTime();
        long difference = stopTime - startTime;
        System.out.println("costing time: " + TimeUnit.NANOSECONDS.toSeconds(difference) + " sec.");
    }
}
