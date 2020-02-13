package experiment;

import cox_hazards.COXClassifier;
import cox_hazards.COXOptimizer;
import cox_hazards.DataFormat;
import data_formatter.brfss.Format;
import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Rainicy on 5/9/16.
 *
 * Cox model ran on different states.
 */
public class Exp231 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify a properties file.");
        }

        Config config = new Config(args[0]);
        System.out.println(config);
        String input = config.getString("input");
        String stateInfo = config.getString("siteid_state");
        String locationInfo = config.getString("map_site_id");

        // loading data
        System.out.println("loading data...");
        Map<String, DataFormat> datas = Format.loadBRFSSPMByStates(input, stateInfo, locationInfo);
        System.out.println("Total #states: " + datas.size());
        BufferedWriter bw = new BufferedWriter(new FileWriter(config.getString("output")));
        bw.write("state\t#death/#people\tcoefficient\tstandardError\n");
        int count = 1;
        int totalCount = datas.size();
        for (Map.Entry<String, DataFormat> entry : datas.entrySet()) {
            String state = entry.getKey();
            DataFormat data = entry.getValue();
            System.out.println(count + "/" + totalCount + ": " + state);

            COXClassifier classifier = new COXClassifier(data.getNumFeatures());
            COXOptimizer loss = new COXOptimizer(classifier, data);
            loss.setTies(config.getBoolean("isTies"));

            LBFGS optimizer = new LBFGS(loss);
            double totalDeath = loss.getDeathCount();
            double total = loss.getTotalCount();
            for (int i=0; i<config.getInt("iterations"); i++) {
                optimizer.iterate();
                System.out.println("iter: " + i + "\tobjective: " + optimizer.getTerminator().getLastValue() + "\tcoefficients: " + classifier.getCoefficient());
            }

            System.out.println("\nFinal result:\n"  + "coefficients: " + classifier.getCoefficient() + "\tstandard errors: " + loss.getStandardError());

            bw.write(state + "\t" + totalDeath + "/" + total + "\t" + classifier.getCoefficient().get(0) + "\t" + loss.getStandardError().get(0) + "\n");

            count++;
        }
        bw.close();
    }
}
