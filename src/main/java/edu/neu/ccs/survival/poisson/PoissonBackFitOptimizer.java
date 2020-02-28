package edu.neu.ccs.survival.poisson;

import edu.neu.ccs.pyramid.optimization.Optimizable;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Rainicy on 11/19/17
 */
public class PoissonBackFitOptimizer implements  Optimizable.ByGradientValue{

    PoissonBackFit classifier;
    MapDataFormat data;
    int numFeatures;

    public PoissonBackFitOptimizer(PoissonBackFit classifier,
                                   MapDataFormat data) {
        this.numFeatures = classifier.getNumFeatures();
        this.classifier = classifier;
        this.data = data;
    }

    @Override
    public Vector getGradient() {
        Vector gradient = new DenseVector(numFeatures + 1);
        List<Vector> listVector = data.getData().entrySet()
                .parallelStream().map(
                        entry -> getOneGradient(entry.getValue())
                ).collect(Collectors.toList());

        for (Vector vector : listVector) {
            gradient = gradient.plus(vector);
        }
        gradient = gradient.times(-1);
        return gradient;
    }

    private Vector  getOneGradient(Map<String, Pair<Integer, List<Pair<double[], Vector>>>> value) {

        List<Vector> listVector = value.entrySet().parallelStream().map(
                entry -> getOneGradient(entry.getValue().getSecond())
        ).collect(Collectors.toList());
        Vector result = new DenseVector(numFeatures + 1);
        for (Vector vector : listVector) {
            result = result.plus(vector);
        }
        return result;
    }

    private Vector getOneGradient(List<Pair<double[], Vector>> pairList) {
        Vector vector = new DenseVector(numFeatures + 1);
        for (Pair<double[], Vector> pair : pairList) {
            double[] first = pair.getFirst();
            Vector second = pair.getSecond();
            double death = first[1];
            double offset = first[0];

            double prod = death - offset * classifier.expDotVector(second);
//            vector = vector.plus(second.times(prod));
            vector.set(0, vector.get(0)+prod);
            for (int i=1; i<=numFeatures; i++) {
                vector.set(i, vector.get(i) + second.get(i)*prod);
            }
        }
        return vector;
    }

    /**
     * objectives
     * @return
     */
    @Override
    public double getValue() {
        return -1.0 * data.getData().entrySet()
                .parallelStream()
                .mapToDouble(entry -> getObjective(entry.getValue())).sum();
    }

    private double getObjective(Map<String, Pair<Integer, List<Pair<double[], Vector>>>> value) {
        return value.entrySet().parallelStream().mapToDouble(entry ->
         getObjective(entry.getValue().getSecond())).sum();

    }

    private double getObjective(List<Pair<double[], Vector>> value) {
        double result = 0.0;
        for (Pair<double[], Vector> pair : value) {
            double[] first = pair.getFirst();
            Vector second = pair.getSecond();
            double death = first[1];
            double offset = first[0];
            double dotProduct = classifier.dotVector(second);
            result += death * dotProduct - offset * Math.exp(dotProduct);
        }
        return result;
    }

    @Override
    public Vector getParameters() {
        return classifier.getCoefficient();
    }

    @Override
    public void setParameters(Vector vector) {
        this.classifier.setCoefficient(vector);
    }

//    public double getObe
}
