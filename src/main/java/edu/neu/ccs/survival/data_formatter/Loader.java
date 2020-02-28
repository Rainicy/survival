package edu.neu.ccs.survival.data_formatter;

import edu.neu.ccs.pyramid.util.Pair;
import edu.neu.ccs.survival.cox_hazards.DataFormat;
import edu.neu.ccs.survival.cox_hazards.MultiKey;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.*;

/**
 * Created by Rainicy on 2/27/20
 */
public class Loader {

    /**
     * The data loader,for given predictors, stratas and death and life.
     * @param input
     * @param predictors
     * @param stratas
     * @param death
     * @param life
     * @param sep
     * @return
     * @throws IOException
     */
    public static DataFormat loadPredictorsAndStrata(String input,
                                                     int[] predictors,
                                                     int[] stratas,
                                                     int death,
                                                     int life,
                                                     String sep) throws IOException {
        int numFeature = predictors.length;
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) { // skip the header
                continue;
            }
            String[] lineInfo = line.split(sep, -1);

            MultiKey key = new MultiKey();
            for (int strataIndex : stratas) {
                String strata = lineInfo[strataIndex];
                key.addKey(strata);
            }

            DenseVector vector = new DenseVector(numFeature);
            for (int i=0; i<predictors.length; i++) {
                double featureValue = Double.parseDouble(lineInfo[predictors[i]]);
                vector.setQuick(i, featureValue);
            }

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[life]);
            label[1] = Integer.parseInt(lineInfo[death]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(numFeature);
        data.setNumStrata(data.getData().size());
        return data;
    }
}
