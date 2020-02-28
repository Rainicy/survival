package edu.neu.ccs.survival.cox_hazards;

import com.google.common.primitives.Doubles;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.security.KeyException;
import java.util.*;

/**
 * Created by Rainicy on 3/11/16.
 */
public class DataFormat {
    private int numStrata;
    private int numFeatures;

    private Map<MultiKey, List<Pair<int[], Vector>>> data;


    public DataFormat() {
        data = new HashMap<>();
    }

    public void normalize() {
        double maxPM = -1;
        double minPM = Double.MAX_VALUE;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>>  entry : data.entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                double pm = pair.getSecond().get(3);
                if (pm > maxPM) {
                    maxPM = pm;
                }
                if (pm < minPM) {
                    minPM = pm;
                }
            }
        }

        double distant = maxPM - minPM;
        System.out.println("max : " + maxPM + "\t min : " + minPM);


        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>>  entry : data.entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                double pm = pair.getSecond().get(3);
                pair.getSecond().set(3, (pm-minPM)/distant);
            }
        }
    }


    /**
     * currently, it only supports one feature dataset.
     * @param numKnots
     */
    public void cubicSplines(int numKnots, int norm) {
        double[] knots = findKnots(numKnots);
        cubicSplines(knots, norm);

    }

    public void cubicSplines(double[] knots, int norm) {
        cubicSplines(Doubles.asList(knots), norm);
    }


    /**
     * According to:
     * http://support.sas.com/resources/papers/proceedings16/5621-2016.pdf
     * From Harrell(2001).
     *
     * Currently it only supports 3-7 number of knots.
     * @param numKnots
     * @return
     */
    private double[] findKnots(int numKnots) {

        double[] results = new double[numKnots];
        Pair<Double, Double> minAndmax = findMinAndMax();
        Map<Integer, List<Double>> preKnots = presetKnots();
        if (!preKnots.containsKey(numKnots)) {
            throw new RuntimeException("The given number of knots is not pre-specified yet: " + numKnots);
        }
        List<Double> quantiles = preKnots.get(numKnots);
        double min = minAndmax.getFirst();
        double max = minAndmax.getSecond();
        double range = max - min;
        for (int i=0; i<numKnots; i++) {
            results[i] = quantiles.get(i) * range + min;
        }
//        System.out.println("knots: " + Arrays.toString(results));
        return results;
    }

    private Map<Integer, List<Double>> presetKnots() {
        Map<Integer, List<Double>> results = new HashMap<>();
        results.put(3, Arrays.asList(0.1,0.5,0.9));
        results.put(4, Arrays.asList(0.05,0.35,0.65,0.95));
        results.put(5, Arrays.asList(0.05,0.275,0.5,0.725,0.95));
        results.put(6, Arrays.asList(0.05,0.23,0.41,0.59,0.77,0.95));
        results.put(7, Arrays.asList(0.025,0.1833,0.3417,0.5,0.6583,0.8167,0.975));
        return results;
    }

    private Pair<Double, Double> findMinAndMax() {
        double maxPM = -1;
        double minPM = Double.MAX_VALUE;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>>  entry : data.entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                double pm = pair.getSecond().get(0);
                if (pm > maxPM) {
                    maxPM = pm;
                }
                if (pm < minPM) {
                    minPM = pm;
                }
            }
        }
        System.out.println("min: " + minPM + "\tmax: " + maxPM);
        return new Pair<>(minPM, maxPM);
    }

    /**
     * it generates knots list by given start, end points and number of knots.
     *
     * @param start
     * @param end
     * @param numKnots
     */
    public void cubicSplines(double start, double end, int numKnots) {
        if (start > end) {
            throw new RuntimeException("start point should be smaller than end point, but " + start + " >= " + end);
        }

        if (numKnots < 2) {
            throw new RuntimeException("number of knots should be larger than 2 not " + numKnots);
        }

        List<Double> knots = new ArrayList<>(numKnots);
        double interval = (end - start) / (numKnots-1);
        for (int i=0; i<numKnots; i++) {
            knots.add(i, start+interval*i);
        }
        /* default norm*/
        cubicSplines(knots, 0);
    }

//    /**
//     *
//     * Restricted Cubic Splines
//     *
//     * refer:http://www.stat.washington.edu/courses/stat527/s14/slides/splines-contd.pdf
//     * http://www.stata.com/meeting/4nasug/RCsplines.pdf
//     *
//     * new one:
//     * http://support.sas.com/resources/papers/proceedings16/5621-2016.pdf
//     *
//     * it only supports one feature right now.
//     *
//     * @param knots
//     */
//    public void cubicSplines(List<Double> knots) {
//        // t_k
//        double lastKnot = knots.get(knots.size()-1);
//        // t_{k-1}
//        double lastSecondKnot = knots.get(knots.size()-2);
//        double lastTowDiff = lastKnot - lastSecondKnot;
//
//        int expandedNumFeatures = knots.size();
//        this.numFeatures = expandedNumFeatures;
//        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : data.entrySet()) {
//            for (Pair<int[], Vector> pair : entry.getValue()) {
//                double x = pair.getSecond().get(0);
//                Vector newX = new DenseVector(expandedNumFeatures);
//                newX.set(0, 1);
//                newX.set(1, x);
//
//                double diffLastSecond = x - lastSecondKnot;
//                double diffLast = x - lastKnot;
//
//                for (int j=2; j<knots.size(); j++) {
//                    double xj = 0;
//                    double knot = knots.get(j-1);
//                    double diffX = x - knot;
//                    if (diffX > 0) {
//                        xj = Math.pow(diffX, 3);
//                    }
//
//                    if (diffLastSecond > 0) {
//                        xj -= Math.pow(diffLastSecond, 3) * (lastKnot - knot) / lastTowDiff;
//                    }
//                    if (diffLast > 0) {
//                        xj += Math.pow(diffLast, 3) * (lastSecondKnot - knot) / lastTowDiff;
//                    }
//
//                    newX.set(j, xj);
//                }
//
//                pair.setSecond(newX);
//            }
//        }
//    }

    /**
     *
     * Restricted Cubic Splines, without bias
     *
     * refer:http://www.stat.washington.edu/courses/stat527/s14/slides/splines-contd.pdf
     * http://www.stata.com/meeting/4nasug/RCsplines.pdf
     *
     * new one:
     * http://support.sas.com/resources/papers/proceedings16/5621-2016.pdf
     *
     * it only supports one feature right now.
     *
     * @param knots
     */
    public void cubicSplines(List<Double> knots, int norm) {
        System.out.println("expands features to knots...");
        System.out.println("knots: " + knots);

        // t_k
        double lastKnot = knots.get(knots.size()-1);
        // t_{k-1}
        double lastSecondKnot = knots.get(knots.size()-2);
        double lastTowDiff = lastKnot - lastSecondKnot;

        // define norm
        double normValue = 1.0;
        switch (norm) {
            case 1: normValue = lastKnot - lastSecondKnot;
                break;
            case 2: normValue = Math.pow((lastKnot - knots.get(0)), (2.0/3.0));
                break;
            default:
                break;
        }

        int prevFeatures = this.numFeatures;
        System.out.println("previous feature: " + prevFeatures);
        int expandedNumFeatures = knots.size()-2; // extra features; if knots=3; then add 1 extra feature
        System.out.println("extra feature: " + expandedNumFeatures);
        this.numFeatures = prevFeatures + expandedNumFeatures;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : data.entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                Vector vect = pair.getSecond();
                double x = vect.get(0);
//                System.out.print("x: " + x + "\t");
                Vector newX = new DenseVector(this.numFeatures);
                for (int i=0; i<prevFeatures; i++) {
                    newX.set(i, vect.get(i));
                }
//                newX.set(0, x);

                double diffLastSecond = x - lastSecondKnot;
                double diffLast = x - lastKnot;

                for (int i=1; i<knots.size()-1; i++) {
                    double xi = 0;
                    double ti = knots.get(i-1);
                    double diffX = x - ti;
                    if (diffX > 0) {
                        xi = Math.pow(diffX/normValue, 3);
                    }

                    if (diffLastSecond > 0) {
                        xi -= Math.pow(diffLastSecond/normValue, 3) * (lastKnot - ti) / lastTowDiff;
                    }
                    if (diffLast > 0) {
                        xi += Math.pow(diffLast/normValue, 3) * (lastSecondKnot - ti) / lastTowDiff;
                    }

                    newX.set(prevFeatures+i-1, xi);
                }

                pair.setSecond(newX);
//                System.out.println("new: " + newX);
            }
        }
    }

    public double[] getMeans() {
        double[] means = new double[numFeatures];
        double count = 0;

        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : data.entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                int c = pair.getFirst()[0];
                Vector x = pair.getSecond();
                for (int i=0; i<x.size(); i++) {
                    means[i] += c * x.get(i);
                }
                count += c;
            }
        }

        for (int i=0; i<means.length; i++) {
            means[i] /= count;
        }

        return means;
    }

    public void setNumStrata(int numStrata) {
        this.numStrata = numStrata;
    }

    public void setNumFeatures(int numFeatures) {
        this.numFeatures = numFeatures;
    }

    public int getNumStrata() {
        return numStrata;
    }

    public int getNumFeatures() {
        return numFeatures;
    }

    public void insertRowData(MultiKey key, Pair<int[], Vector> rowData) {
        if (!data.containsKey(key)) {
            List<Pair<int[], Vector>> list = new ArrayList<>();
            data.put(key, list);
        }

        data.get(key).add(rowData);
    }

    public List<Pair<int[], Vector>> getData(MultiKey key) throws KeyException {
        if (!data.containsKey(key)) {
            throw new KeyException("No such key: " + key);
        }
        return data.get(key);
    }

    public Map<MultiKey, List<Pair<int[], Vector>>> getData() {
        return data;
    }


    public String toString() {
        String string = "numStrata: " + numStrata + "\t" + "numFeatures: " + numFeatures + "\n";
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : data.entrySet()) {
            string += entry.getKey() + ": \n";
            for (Pair<int[], Vector> ele : entry.getValue()) {
                string += "\t" + "label: " + Arrays.toString(ele.getFirst()) + "\t vector: " + ele.getSecond() + "\n";
            }
        }
        return string;
    }

}
