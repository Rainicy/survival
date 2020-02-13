package poisson;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 * Created by Rainicy on 6/20/17.
 */
public class PoissonClassifier {
    private int numFeatures;
    // which includes the intercept(size = numFeatures+1)
    Vector coefficient;

    public PoissonClassifier(int numFeatures) {
        this.numFeatures = numFeatures;
        this.coefficient = new DenseVector(numFeatures+1);
//        for (int i=0; i<coefficient.size(); i++) {
//            coefficient.set(i, Math.random());
//        }
    }

    public int getNumFeatures() {
        return this.numFeatures;
    }

    public Vector getCoefficient() {
        return this.coefficient;
    }

    public void setNumFeatures(int numFeatures) {
        this.numFeatures = numFeatures;
    }

    public void setCoefficient(Vector vector) {
        this.coefficient = vector;
    }

    public double dotVector(Vector vector) {
        return coefficient.dot(vector);
    }

    public  double expDotVector(Vector vector) {
        return Math.exp(dotVector(vector));
    }
}

