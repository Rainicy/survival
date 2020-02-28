package edu.neu.ccs.survival.cox_hazards;

import edu.neu.ccs.pyramid.optimization.Optimizable;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rainicy on 3/16/16.
 */
public class COXOptimizer implements Optimizable.ByGradientValue {

    COXClassifier classifier;
    DataFormat dataSet;
    int numFeatures;

    Map<MultiKey, List<Integer>> death;

    long deathCount;
    long totalCount;
    boolean ties;

    public boolean isParallel() {
        return isParallel;
    }

    public void setParallelism(boolean parallel) {
        isParallel = parallel;
    }

    boolean isParallel;
    boolean isL2 = false;
    double[] l2Strength;


    public COXOptimizer(COXClassifier classifier, DataFormat dataSet) {
        this.classifier = classifier;
        this.dataSet = dataSet;
        this.numFeatures = dataSet.getNumFeatures();
        System.out.println("updating death");
        updateDeath();
        System.out.println("done with updating death");

        int total = 0;
        int count = 0;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
            for (Pair<int[], Vector> pair : entry.getValue()) {
                total += pair.getFirst()[0];
                count += pair.getFirst()[1];
            }
        }
        this.deathCount = count;
        this.totalCount = total;
        this.l2Strength = new double[numFeatures];
        System.out.println("death/total: " + count + "/" + total);
    }

    /**
     * update the death, shuffle it for ties later.
     * TODO: skip it if ties is false.
     */
    private void updateDeath() {
        death = new HashMap<>();
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
            MultiKey key = entry.getKey();
            List<Pair<int[], Vector>> value = entry.getValue();
            List<Integer> deathIndex = new ArrayList<>();
            for (int i=0; i<value.size(); i++) {
                int[] label = value.get(i).getFirst();
                for (int j=0; j<label[1]; j++) {
                    deathIndex.add(i);
                }
            }
            Collections.shuffle(deathIndex);
            death.put(key, deathIndex);
        }
    }

    /**
     * get the objectives for a test dataset.
     * @return double
     */
    public double getObjectives(DataFormat testData) {
        if (isParallel) {
        double objects = testData.getData().entrySet().stream().parallel().mapToDouble(entry -> getObjective(entry)).sum();
        if (isL2) {
            objects += penalty();
        }
        return objects;
        }

        double objectives = 0;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : testData.getData().entrySet()) {
            objectives += getObjective(entry);
        }
        if (isL2) {
            objectives += penalty();
        }
        return objectives;
    }

    /**
     * get objectives for whole dataset.
     * @return
     */
    private double getObjectives() {
        if (isParallel) {
            double objects = dataSet.getData().entrySet().stream().parallel().mapToDouble(entry -> getObjective(entry)).sum();
            if (isL2) {
                objects += penalty();
            }
            return objects;
        }

        double objectives = 0;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
            objectives += getObjective(entry);
        }
        if (isL2) {
            objectives += penalty();
        }
        return objectives;
    }

    /**
     * please (4.2) in paper: High-dimensional, massive sample-size Cox proportional hazards regression for survival analysis
     * @return
     */
    private double penalty() {
        int numCoef = classifier.getNumFeatures();
        double penalty = (0.5 * Math.log(2 * Math.PI)) * numCoef;
        for (int i=0; i<numFeatures; i++) {
            penalty += Math.log(Math.sqrt(l2Strength[i]));
        }
        for (int i=0; i<numFeatures; i++) {
            double coef = classifier.getCoefficient().get(i);
            penalty += 0.5 * coef * coef / l2Strength[i];
        }
        return penalty;
    }

    /**
     * get objective by given list of raw dataset.
     * @param entry
     * @return
     */
    private double getObjective(Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry) {
        double objective = 0;
        List<Pair<int[], Vector>> value = entry.getValue();
        double[] denominators = getDenominators(value);

        // with ties
        if(ties) {
            List<Integer> deathIndex = death.get(entry.getKey());
            double[] logSumDenominators = getLogSumDenominators(denominators, value, deathIndex);
            for (int i=0; i<deathIndex.size(); i++) {
                int index = deathIndex.get(i);
                objective -= denominators[index] - logSumDenominators[i];
            }
        } else {
            double logSumDenominators = getLogSumDenominators(denominators, value);
            for (int i=0; i<value.size(); i++) {
                Pair<int[], Vector> pair = value.get(i);
                int[] label = pair.getFirst();
                if (label[1] != 0) {
                    objective -= (denominators[i] - logSumDenominators) * label[1];
                }
            }
        }
        return objective;
    }

    /**
     * get logSumExp with tied time survival.
     * @param denominators
     * @param value
     * @param deathIndex
     * @return
     */
    private double[] getLogSumDenominators(double[] denominators, List<Pair<int[], Vector>> value, List<Integer> deathIndex) {
        double[] results = new double[deathIndex.size()];

        double maxElement = denominators[0];
        int maxIndex = 0;
        for (int i=0; i<denominators.length; i++) {
            double number = denominators[i];
            if (number > maxElement) {
                maxElement = number;
                maxIndex = i;
            }
        }
        int[] livesCount = new int[denominators.length];
        double sum = 0;
        for (int i=0; i<denominators.length; i++) {
            int count = value.get(i).getFirst()[0];
            livesCount[i] = count;
            sum += count * Math.exp(denominators[i] - maxElement);
        }

        // removing death
        for (int i=0; i<deathIndex.size(); i++) {
            results[i] = Math.log(sum) + maxElement;

            int index = deathIndex.get(i);
            livesCount[index]--;

            if (livesCount[index] == 0 && maxIndex == index) {
                maxElement = Double.NEGATIVE_INFINITY;
                for (int j=0; j<denominators.length; j++) {
                    double number = denominators[j];
                    if (number > maxElement && livesCount[j] > 0) {
                        maxElement = number;
                        maxIndex = j;
                    }
                }
                sum = 0;
                for (int j=0; j<denominators.length; j++) {
                    if (livesCount[j] == 0) continue;
                    sum += livesCount[j] * Math.exp(denominators[j] - maxElement);
                }
            } else {
                sum -= Math.exp(denominators[index] - maxElement);
            }
        }
        return results;
    }


    /**
     * get logSumExp without tied times survival.
     * @param denominators
     * @param value
     * @return
     */
    private double getLogSumDenominators(double[] denominators, List<Pair<int[], Vector>> value) {
        double maxElement = denominators[0];
        for (double number : denominators) {
            if (number > maxElement) {
                maxElement = number;
            }
        }
        double sum = 0;
        for (int i=0; i<denominators.length; i++) {
            double number = denominators[i];
            sum += value.get(i).getFirst()[0] * Math.exp(number - maxElement);
        }

        return Math.log(sum) + maxElement;
    }

    /**
     * return coefficient * X for each raw data.
     * @param value
     * @return
     */
    private double[] getDenominators(List<Pair<int[], Vector>> value) {
        double[] results = new double[value.size()];
        for (int i=0; i<value.size(); i++) {
            Vector vector = value.get(i).getSecond();
            results[i] = classifier.dotVector(vector);
        }
        return results;
    }

    @Override
    public Vector getGradient() {
        Vector gradient = new DenseVector(numFeatures);
        if (isParallel) {
            List<Vector> listVectors = dataSet.getData().entrySet().stream().parallel().map(entry -> getGradient(entry)).collect(Collectors.toList());
            for (Vector vector : listVectors) {
                gradient = gradient.plus(vector);
            }
        } else {
            for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
                gradient = gradient.plus(getGradient(entry));
            }
        }

        gradient = gradient.times(-1);
        if(isL2) {
            for (int i=0; i<numFeatures; i++) {
                gradient.set(i, gradient.get(i)+classifier.getCoefficient().get(i)/l2Strength[i]);
            }
        }

        return gradient;

    }


    public Vector getGradient(Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry) {
        Vector gradient = new DenseVector(numFeatures);
        List<Pair<int[], Vector>> value = entry.getValue();
        double[] denominators = getDenominators(value);

        // updating the coefficient
        if (ties) {
            // with ties
            MultiKey key = entry.getKey();
            List<Integer> deathIndex = death.get(key);
            double[] logSumDenominators = getLogSumDenominators(denominators, value, deathIndex);
            Vector[] avgGradient = getAvgGradients(denominators, logSumDenominators, value, deathIndex);
            for (int i=0; i<avgGradient.length; i++) {
                int index = deathIndex.get(i);
                Vector feature = value.get(index).getSecond();
                gradient = gradient.plus(feature.minus(avgGradient[i]));
            }
        } else {
            // no ties.
            double logSumDenominators = getLogSumDenominators(denominators, value);
            Vector avgGradient = getAvgGradient(denominators, logSumDenominators, value);
            // updating the coefficient
            for (Pair<int[], Vector> pair : value) {
                int[] label = pair.getFirst();
                if (label[1] != 0) {
                    Vector feature = pair.getSecond();
                    gradient = gradient.plus(feature.minus(avgGradient).times(label[1]));
                }
            }
        }
        return gradient;
    }

    /**
     * get the average gradient vector by removing one death, which is indicated by given index.
     * @param denominators
     * @param logSumDenominators
     * @param value
     * @param deathIndex
     * @return
     */
    private Vector[] getAvgGradients(double[] denominators, double[] logSumDenominators, List<Pair<int[], Vector>> value, List<Integer> deathIndex) {
        Vector[] results = new Vector[deathIndex.size()];
        for (int i=0; i<results.length; i++) {
            results[i] = new DenseVector(numFeatures);
        }

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int[] label = pair.getFirst();
            Vector feature = pair.getSecond();
            double[] weights = getWeights(denominators[i], logSumDenominators);

            int totalPeople = label[0];

            for (int j=0; j<weights.length; j++) {
                results[j] = results[j].plus(feature.times(weights[j] * totalPeople));
                if (deathIndex.get(j) == i) {
                    totalPeople--;
                }
            }
        }
        return results;
    }

    /**
     * get the weights for given different log denominators.
     * @param denominator
     * @param logSumDenominators
     * @return
     */
    private double[] getWeights(double denominator, double[] logSumDenominators) {
        double[] weights = new double[logSumDenominators.length];
        for(int i=0; i<weights.length; i++) {
            weights[i] = Math.exp(denominator - logSumDenominators[i]);
        }
        return weights;
    }

    /**
     * get the average gradient vector.
     * @param denominators
     * @param logSumDenominators
     * @param value
     * @return
     */
    private Vector getAvgGradient(double[] denominators, double logSumDenominators, List<Pair<int[], Vector>> value) {
        Vector vector = new DenseVector(numFeatures);

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int[] label = pair.getFirst();
            Vector feature = pair.getSecond();
            double weight = Math.exp(denominators[i] - logSumDenominators) * label[0];
            vector = vector.plus(feature.times(weight));
        }

        return vector;
    }

    public double[][] getVar() {
        double[][] inforM = getInformationMatrix();
        RealMatrix a = new Array2DRowRealMatrix(inforM);
        RealMatrix inverseA = MatrixUtils.inverse(a);
        return inverseA.getData();
    }

    public double[][] getVar(int size) {
        double[][] inforM = getInformationMatrix(size);
        RealMatrix a = new Array2DRowRealMatrix(inforM);
        RealMatrix inverseA = MatrixUtils.inverse(a);
        return inverseA.getData();
    }

    public double[] getStandardError(double[][] inverseInforM) {
        double[] result = new double[numFeatures];
        for (int i=0; i<numFeatures; i++)  {
            result[i] = Math.sqrt(inverseInforM[i][i]);
        }

        return result;
    }

    public double[] getStandardError(double[][] inverseInforM, int size) {
        double[] result = new double[size];
        for (int i=0; i<size; i++)  {
            result[i] = Math.sqrt(inverseInforM[i][i]);
        }

        return result;
    }


    public Vector getStandardError() {
        double[][] inforM = getInformationMatrix();
        RealMatrix a = new Array2DRowRealMatrix(inforM);
        RealMatrix inverseA = MatrixUtils.inverse(a);

        double[][] inverseInforM = inverseA.getData();

//
//        System.out.println("Information Matrix: ");
//        for (int i=0; i<numFeatures; i++) {
//            System.out.println(Arrays.toString(inforM[i]));
//        }
//        System.out.println("Inverse: ");
//        for (int i=0; i<numFeatures; i++) {
//            System.out.println(Arrays.toString(inverseInforM[1]));
//        }

        Vector result = new DenseVector(numFeatures);
        for (int i=0; i<numFeatures; i++)  {
            result.set(i, Math.sqrt(inverseInforM[i][i]));
        }

        return result;
    }


    // I = - (Hessian Matrix)
    public double[][] getInformationMatrix() {
        System.out.println("number of features: " + numFeatures);
        double[][] var = new double[numFeatures][numFeatures];
        for (int j=0; j<numFeatures; j++) {
            for (int k=0; k<numFeatures; k++) {
                var[j][k] = getInformationCell(j, k);
            }
        }
        return var;
    }

    // I = - (Hessian Matrix)
    public double[][] getInformationMatrix(int size) {
        System.out.println("number of features: " + size);
        double[][] var = new double[size][size];
        for (int j=0; j<size; j++) {
            for (int k=0; k<size; k++) {
                var[j][k] = getInformationCell(j, k);
            }
        }
        return var;
    }


    private double getInformationCell(int j, int k) {

        // diagonal
        if (j == k) {
            return getSecondDerivative(j);
        } else {
            return getSecondDerivative(j, k);
        }
    }

    public double getSecondDerivative(int j, int k) {
        double result = 0;

        if (isParallel) {
            result = dataSet.getData().entrySet().stream().parallel().mapToDouble(entry -> getSecondDerivative(entry, j, k)).sum();
        } else {
            for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
                result += getSecondDerivative(entry, j, k);
            }
        }
        // penalty second derivative is canceled out.
        return result;
    }

    public double getSecondDerivative(int index) {
        double result = 0;
        if (isParallel) {
            result = dataSet.getData().entrySet().stream().parallel().mapToDouble(entry -> getSecondDerivative(entry, index)).sum();
        } else {
             for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
                 result += getSecondDerivative(entry, index);
             }
        }
//        result = 1.0/Math.sqrt(result);
        if (isL2) {
            result += 1.0 / l2Strength[index];
        }
        return result;
    }


    private double getSecondDerivative(Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry, int j, int k) {

        double result = 0;
        List<Pair<int[], Vector>> value = entry.getValue();
        double[] denominators = getDenominators(value);


        // TODO: ties version of calculate second derivative
        if (ties) {
            MultiKey key = entry.getKey();
            List<Integer> deathIndex = death.get(key);
            double[] logSumDenominators = getLogSumDenominators(denominators, value, deathIndex);
            double[] avgDerivatives = getAvgDerivatives(denominators, logSumDenominators, value, deathIndex, j, k);

            for (int i=0; i<avgDerivatives.length; i++) {
                result += avgDerivatives[i];
            }

        } else {
            double logSumDenominators = getLogSumDenominators(denominators, value);
            double avgDerivative = getAvgDerivative(denominators, logSumDenominators, value, j, k);
            for (int i=0; i<value.size(); i++) {
                Pair<int[], Vector> pair = value.get(i);
                int label1 = pair.getFirst()[1];
                if (label1 != 0) {
                    result += avgDerivative * label1;
                }
            }
        }


        return result;
    }



    // diagonal ones
    private double getSecondDerivative(Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry, int index) {

        double result = 0;
        List<Pair<int[], Vector>> value = entry.getValue();
        double[] denominators = getDenominators(value);


        if (ties) {
            MultiKey key = entry.getKey();
            List<Integer> deathIndex = death.get(key);
            double[] logSumDenominators = getLogSumDenominators(denominators, value, deathIndex);
            double[] avgDerivatives = getAvgDerivatives(denominators, logSumDenominators, value, deathIndex, index);

            for (int i=0; i<avgDerivatives.length; i++) {
                result += avgDerivatives[i];
            }

        } else {
            double logSumDenominators = getLogSumDenominators(denominators, value);
            double avgDerivative = getAvgDerivative(denominators, logSumDenominators, value, index);
            for (int i=0; i<value.size(); i++) {
                Pair<int[], Vector> pair = value.get(i);
                int label1 = pair.getFirst()[1];
                if (label1 != 0) {
                    result += avgDerivative * label1;
                }
            }
        }


        return result;
    }

    // negative second derivative on non-diagonal (index) element with ties
    private double[] getAvgDerivatives(double[] denominators, double[] logSumDenominators, List<Pair<int[], Vector>> value, List<Integer> deathIndex, int j, int k) {
        double[] results = new double[deathIndex.size()];
        double[] secondResults = new double[deathIndex.size()];
        double[] thirdResults = new double[deathIndex.size()];

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int[] label = pair.getFirst();
            double featureJ = pair.getSecond().get(j);
            double featureK = pair.getSecond().get(k);
            double[] weights = getWeights(denominators[i], logSumDenominators);

            int totalPeople = label[0];

            for (int ii=0; ii<weights.length; ii++) {
                results[ii]  += featureJ * featureK * weights[ii] * totalPeople;
                secondResults[ii] += featureJ * weights[ii] * totalPeople;
                thirdResults[ii] += featureK * weights[ii] * totalPeople;

                if (deathIndex.get(ii) == i) {
                    totalPeople--;
                }
            }
        }

        for (int i=0; i<results.length; i++) {
            results[i] -= secondResults[i] * thirdResults[i];
        }

        return results;
    }

    // negative second derivative on diagonal (index) element with ties
    private double[] getAvgDerivatives(double[] denominators, double[] logSumDenominators, List<Pair<int[], Vector>> value, List<Integer> deathIndex, int index) {
        double[] results = new double[deathIndex.size()];
        double[] secondResults = new double[deathIndex.size()];

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int[] label = pair.getFirst();
            double feature = pair.getSecond().get(index);
            double[] weights = getWeights(denominators[i], logSumDenominators);

            int totalPeople = label[0];

            for (int j=0; j<weights.length; j++) {
                results[j] += feature * feature * weights[j] * totalPeople;
                secondResults[j] += feature * weights[j] * totalPeople;

                if (deathIndex.get(j) == i) {
                    totalPeople--;
                }
            }
        }

        for (int i=0; i<results.length; i++) {
            results[i] -= secondResults[i] * secondResults[i];
        }


        return results;
    }

    // negative second derivative on non-diagonal (j, k) element without ties
    private double getAvgDerivative(double[] denominators, double logSumDenominators, List<Pair<int[], Vector>> value, int j, int k) {
        double vector = 0;
        double secondVector = 0;
        double thirdVector = 0;

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int label0 = pair.getFirst()[0];
            double featureJ = pair.getSecond().get(j);
            double featureK = pair.getSecond().get(k);
            double weight = Math.exp(denominators[i] - logSumDenominators) * label0;
            vector += featureJ * featureK * weight;
            secondVector += featureJ * weight;
            thirdVector += featureK * weight;
        }
        vector -= secondVector * thirdVector;

        return vector;
    }

    // negative second derivative on diagonal (index) element without ties
    private double getAvgDerivative(double[] denominators, double logSumDenominators, List<Pair<int[], Vector>> value, int index) {
        double vector = 0;
        double secondVector = 0;

        for (int i=0; i<value.size(); i++) {
            Pair<int[], Vector> pair = value.get(i);
            int label0 = pair.getFirst()[0];
            double feature = pair.getSecond().get(index);
            double weight = Math.exp(denominators[i] - logSumDenominators) * label0;
            vector += feature*feature*weight;
            secondVector += feature * weight;
        }

        vector -= secondVector * secondVector;

        return vector;
    }




    public boolean isTies() {
        return ties;
    }

    public void setTies(boolean ties) {
        this.ties = ties;
    }

    @Override
    public double getValue() {
        return getObjectives();
    }

    @Override
    public Vector getParameters() {
        return classifier.getCoefficient();
    }

    @Override
    public void setParameters(Vector vector) {
        this.classifier.setCoefficient(vector);
    }


    public Map<MultiKey, List<Integer>> getDeath() {
        return death;
    }
    public long getDeathCount() {
        return deathCount;
    }
    public long getTotalCount() {
        return totalCount;
    }

    public boolean isL2() {
        return isL2;
    }

    public void setL2(boolean l2) {
        isL2 = l2;
    }

    public double[] getL2Strength() {
        return l2Strength;
    }

    public void setL2Strength(List<Double> l2Strength) {
        for (int i=0; i<l2Strength.size(); i++) {
            this.l2Strength[i] = l2Strength.get(i);
        }
    }

}
