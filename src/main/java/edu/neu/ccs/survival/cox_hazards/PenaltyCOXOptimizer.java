package cox_hazards;

import edu.neu.ccs.pyramid.optimization.Optimizable;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rainicy on 8/3/16.
 */
public class PenaltyCOXOptimizer implements Optimizable.ByGradientValue {

    COXClassifier classifier;
    DataFormat dataSet;
    int numFeatures;

    Map<MultiKey, List<Integer>> death;

    int deathCount;
    int totalCount;
    boolean ties;

    boolean isParallel;

    // variance penalty
    double variance;



    public void setParallelism(boolean b) {
        this.isParallel = b;
    }

    public boolean isParallel() {
        return this.isParallel;
    }

    public PenaltyCOXOptimizer(COXClassifier classifier, DataFormat dataSet, double variance) {
        this.classifier = classifier;
        this.dataSet = dataSet;
        this.variance = variance;
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
        System.out.println("death/total: " + count + "/" + total);
    }

    /**
     * update the death, shuffle it for ties later.
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
     * get objectives for whole dataset.
     *
     * @return
     */
    private double getObjectives() {
        if (isParallel) {
            return dataSet.getData().entrySet().stream().parallel().mapToDouble(entry -> getObjective(entry)).sum();
        }

        double objectives = 0;
        for (Map.Entry<MultiKey, List<Pair<int[], Vector>>> entry : dataSet.getData().entrySet()) {
            objectives += getObjective(entry);
        }

        return objectives + getPenalty();
    }

    /**
     * penalty the weights, l2 penalty.
     * @return
     */
    public double getPenalty() {
        double square = 0;
        double squareVariance = 2 * square * square;
        double constant = 0.5 * Math.log(2 * Math.PI) + Math.log(variance);
        Vector weights = classifier.getCoefficient();
        for (int k=0; k<weights.size(); k++) {
            square -= constant + Math.pow(weights.get(k), 2) / squareVariance;
        }
        return square;
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

        Vector penaltyGradient = getPenaltyGradient();
        gradient = gradient.times(-1).plus(penaltyGradient);
        return gradient;

    }

    public Vector getPenaltyGradient() {
        Vector gradientVector = new DenseVector(numFeatures);
        double squareVariance = variance * variance;
        Vector weights = classifier.getCoefficient();
        for (int i=0; i<weights.size(); i++) {
            gradientVector.set(i, weights.get(i) / squareVariance);
        }
        return gradientVector;
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

    public Vector getStandardError() {
        Vector result = new DenseVector(numFeatures);
        for (int i=0; i<numFeatures; i++) {
            result.set(i, getSecondDerivative(i));
        }
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
        result = 1.0/Math.sqrt(result);
        return result;
    }

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
    public int getDeathCount() {
        return deathCount;
    }
    public int getTotalCount() {
        return totalCount;
    }


}
