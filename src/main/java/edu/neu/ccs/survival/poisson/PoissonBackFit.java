package poisson;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 * Created by Rainicy on 11/19/17
 */
public class PoissonBackFit {
    private int numFeatures;
    // 0 is the bias, and the rest is feature coefficient
    Vector coefficient;

    public PoissonBackFit(int numFeatures) {
        this.numFeatures = numFeatures;
        this.coefficient = new DenseVector(numFeatures + 1);
    }

    public int getNumFeatures() {
        return this.numFeatures;
    }

    public Vector getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Vector vector) {
        this.coefficient = vector;
    }

    public double dotVectorWithoutBias(Vector vector) {
        double result = 0;
        for (int i=1; i<vector.size(); i++) {
            result += vector.get(i) * coefficient.get(i);
        }
        return result;
    }

    public double dotVector(Vector vector) {
//        return coefficient.dot(vector);
        double result = coefficient.get(0);
        for (int i=1; i<vector.size(); i++) {
            result += vector.get(i) * coefficient.get(i);
        }
        return result;
    }

    public double expDotVector(Vector vector) {
        return Math.exp(dotVector(vector));
    }

}
