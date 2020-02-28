package edu.neu.ccs.survival.poisson;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 * Created by Rainicy on 6/20/17.
 */
public class DataFormat {

    private int numData;
    private int numFeatures;
    private int[] deaths;
    private Vector[] features;
    private double[] offsets;
//    private double[] logOffsets;
//    private double sumOffset;


    public DataFormat(int numData, int numFeatures) {
        this.numData = numData;
        this.numFeatures = numFeatures;
        this.deaths = new int[numData];
        this.features = new DenseVector[numData];
        this.offsets = new double[numData];
//        this.logOffsets = new double[numData];
    }

    public void setRowData(int index, int death, double offset, double[] feature) {
        if (feature.length != numFeatures) {
            throw new IllegalArgumentException("Number of given features is not matching: " + feature +  " vs " + numFeatures);
        }
        if (!(index < numData)) {
            throw new IllegalArgumentException("Index is exceeding the total number of data: " + index + "  vs " + numData);
        }
        deaths[index] = death;
        offsets[index] = offset;
//        logOffsets[index] = Math.log(offset);
        // first one is intercept
        features[index] = new DenseVector(numFeatures + 1);
        features[index].set(0, 1);
        for (int i=0; i<feature.length; i++) {
            features[index].set(i+1, feature[i]);
        }
    }

//    public void updateSumOffset() {
//        this.sumOffset = IntStream.range(0, numData).parallel().mapToDouble(i -> updateOffset(i)).sum();
//    }

//    private double updateOffset(int index) {
//        int death = deaths[index];
//        double logOffset = logOffsets[index];
//
//        double result = death * logOffset;
//
//        for (int d=death; d>1; d--) {
//            result -= Math.log(d);
//        }
//        return result;
//    }

    public int getNumData() {
        return numData;
    }

    public void setNumData(int numData) {
        this.numData = numData;
    }

    public int getNumFeatures() {
        return numFeatures;
    }

    public void setNumFeatures(int numFeatures) {
        this.numFeatures = numFeatures;
    }

    public int[] getDeaths() {
        return deaths;
    }

    public void setDeaths(int[] deaths) {
        this.deaths = deaths;
    }

    public Vector[] getFeatures() {
        return features;
    }

    public void setFeatures(Vector[] features) {
        this.features = features;
    }

    public double[] getOffsets() {
        return offsets;
    }

    public void setOffsets(double[] offsets) {
        this.offsets = offsets;
    }

//    public double[] getLogOffsets() {
//        return logOffsets;
//    }
//
//    public void setLogOffsets(double[] logOffsets) {
//        this.logOffsets = logOffsets;
//    }

//    public double getSumOffset() {
//        return sumOffset;
//    }


}
