package poisson;

import edu.neu.ccs.pyramid.optimization.Optimizable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Rainicy on 6/20/17.
 */
public class PoissonOptimizer implements Optimizable.ByGradientValue{
    PoissonClassifier classifier;
    DataFormat dataSet;
    int numFeatures;

    public PoissonOptimizer(PoissonClassifier classifier, DataFormat dataSet) {
        this.numFeatures = classifier.getNumFeatures();
        this.classifier = classifier;
        this.dataSet = dataSet;
    }

    public double getObjectives() {
        double result = IntStream.range(0, dataSet.getNumData()).parallel().mapToDouble(i -> getObjective(i)).sum();

//        result += dataSet.getSumOffset();
        return result * -1.0;
    }

    private double getObjective(int index) {
        int death = dataSet.getDeaths()[index];
        double offset = dataSet.getOffsets()[index];
        Vector feature = dataSet.getFeatures()[index];
        // beta * x
        double dotProduct = classifier.dotVector(feature);

        double result = death * dotProduct -  offset * Math.exp(dotProduct);

        return result;
    }


    @Override
    public Vector getGradient() {
        Vector gradient = new DenseVector(numFeatures + 1);

        List<Vector> listVectors = IntStream.range(0, dataSet.getNumData()).parallel().mapToObj(i -> getOneGradient(i)).collect(Collectors.toList());

        for (Vector vector : listVectors) {
            gradient = gradient.plus(vector);
        }
        gradient = gradient.times(-1);
        return gradient;
    }

    public Vector getOneGradient(int index) {

        int death = dataSet.getDeaths()[index];
        Vector feature = dataSet.getFeatures()[index];
        double offset = dataSet.getOffsets()[index];

        // y - t*exp(beta*x)
        double prod = death - offset * classifier.expDotVector(feature);
        Vector gradient = feature.times(prod).clone();

        return gradient;
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
}
