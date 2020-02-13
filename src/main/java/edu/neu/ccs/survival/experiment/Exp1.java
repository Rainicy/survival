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
 * Created by Rainicy on 3/15/16.
 */
public class Exp1 {

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
            case "DSenrollee": data = Format.loadDSenrollee(input, config.getString("group"));
                break;
            case "clean_DSenrollee": data = Format.loadCleanDS(input, config.getString("group"));
                break;
            case "cleanDS": data = DSFormat.loadCleanDS(input, config.getString("group"));
                break;
            case "DSenrolleeByPM": data = Format.loadDSenrolleeByPM(input, config.getString("meanPMFile"), config.getDouble("maxAvgPM"));
                break;
            case "BRFSSPM_StrataRegions": data = Format.loadBRFSSPM_StrataRegions(input);
                break;
            case "BRFSSPMRegions": data = Format.loadBRFSSPMRegions(input);
                break;
            case "BRFSSPM": data = Format.loadBRFSSPM(input);
                break;
            case "BRFSSPMbyRegion": data = Format.loadBRFSSPM(input, config.getInt("region"));
                break;
            case "testData8": data = Format.loadtestData8(input);
                break;
            case "testData8StrataAge": data = Format.loadtestData8StrataAge(input);
                break;
            case "testPrev": data = Format.loadTestPrev(input);
                break;
            case "testPrevNoStrata": data = Format.loadTestPrevNoStrata(input);
                break;
            case "testPrevNoStrataAge": data = Format.loadTestPrevNoStrataAge(input);
                break;
            case "O3":
                data = Format.loadO3(input, config.getString("group"));
                break;
            case "Ozone":
                data = DSFormat.loadO3(input, config.getString("group"));
                break;
            default: throw new RuntimeException("wrong load data given: " + config.getString("load"));
        }
        System.out.println("done with loading data...");
//        System.out.println(data);

        long startTime = System.nanoTime();
        COXClassifier classifier = new COXClassifier(data.getNumFeatures());
        // just test and initialize it with the best coefficients:
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
            // TODO: remove
//            double[][] var = loss.getVar(2);
//            double[] ses = loss.getStandardError(var, 2);
            System.out.println("SES List: " + Arrays.toString(ses));
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
