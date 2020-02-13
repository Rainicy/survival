package cox_hazards;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 * Created by Rainicy on 3/16/16.
 */
public class COXClassifier {
    private int numFeatures;
    Vector coefficient;


    public COXClassifier(int numFeatures) {
        this.numFeatures = numFeatures;
        this.coefficient = new DenseVector(numFeatures);
        for (int i=0; i<coefficient.size(); i++) {
            coefficient.set(i, Math.random());
        }
    }


    public int getNumFeatures() {
        return numFeatures;
    }

    public Vector getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Vector vector) {
        this.coefficient = vector;
    }

    /**
     * return beta^T * X
     * @param vector
     * @return
     */
    public double dotVector(Vector vector) {
        return coefficient.dot(vector);
    }


    /**
     * return exp{beta^T * X}
     * @param vector
     * @return
     */
    public double expDotVector(Vector vector) {
        return Math.exp(dotVector(vector));
    }
}
