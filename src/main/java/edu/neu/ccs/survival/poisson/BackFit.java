package edu.neu.ccs.survival.poisson;

import edu.neu.ccs.pyramid.optimization.LBFGS;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rainicy on 11/20/17
 */
public class BackFit {
    int numFeatures;
    MapDataFormat data;
    PoissonBackFitOptimizer loss;
    PoissonBackFit classifier;
    int numIter;
    // log offset for each location and group
//    Map<String, HashMap<String, Double>> logOffsets;

    public BackFit(PoissonBackFit classifier, MapDataFormat data, int numFeatures) {
        this.data = data;
        this.numFeatures = numFeatures;
        this.classifier = classifier;
        this.loss = new PoissonBackFitOptimizer(classifier, data);
//        this.logOffsets = new HashMap<>();
//        updateLogOffsets();
    }

//    private void updateLogOffsets() {
//        data.getData().entrySet()
//                .stream().forEach(
//                        entry -> {
//                            updateLogOffsets(entry.getKey(), entry.getValue());
//                        }
//        );
//    }
//
//    private void updateLogOffsets(String key, Map<String, Pair<Integer, List<Pair<double[], Vector>>>> value) {
//        if (!logOffsets.containsKey(key)) {
//            logOffsets.put(key, new HashMap<>());
//        }
//        value.entrySet()
//                .stream().forEach(
//                        entry -> {
//                            String ID = entry.getKey();
//                            logOffsets.get(key).put(ID, 0.0);
//                        }
//        );
//    }

    public void optimize(int iteration) {
        for (int i=0; i<iteration; i++) {
            System.out.println("iter: " + i);
            System.out.println("step A-update: ");
            // step A(1): update offsets
//            updateOffset1();
            System.out.println("step A-fit: ");
            // step A(2): fit Poisson model
            fitPoisson();
            System.out.println(classifier.getCoefficient());
            // step B(1): update offsets
            System.out.println("step B-update: ");
            updateOffset2();
            // step B(2): fit Poisson model for each
            System.out.println("step B-fit: ");
            fitPoissonsSites();
        }
        System.out.println("Final: " );
//        updateOffset1();
        fitPoisson();
        System.out.println(classifier.getCoefficient());
    }

    private void updateOffset2() {
        data.getData().entrySet().parallelStream().
                forEach(entry -> updateOffset2(entry.getKey(), entry.getValue()));
    }

    private void updateOffset2(String site, Map<String, Pair<Integer, List<Pair<double[], Vector>>>> value) {
        value.entrySet().parallelStream().forEach(
                entry -> {
                    List<Pair<double[], Vector>> listPair = entry.getValue().getSecond();
                    // TODO: debug
                    if (site.equals("100010002") && entry.getKey().equals("67FNW")) {
                        for (Pair<double[], Vector> pair : listPair) {
                            double[] first = pair.getFirst();
                            Vector second = pair.getSecond();
                            double total = first[0];
                            double death = first[1];
                            double logN = second.get(0);
                            double feature = second.get(1);
                            double effect = logN + classifier.dotVectorWithoutBias(pair.getSecond());
                            System.out.println(total + "\t" + Math.round(death) + "\t" + logN + "\t" + effect + "\t" + feature);
                        }
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    for (Pair<double[], Vector> pair : listPair) {
                        double logNatc = pair.getSecond().get(0);
                        logNatc += classifier.dotVectorWithoutBias(pair.getSecond());
                        pair.getFirst()[0] = Math.exp(logNatc);
                    }
                }
        );
    }

    private void fitPoissonsSites() {
        data.getData().entrySet()
                .parallelStream().forEach(
                        entry -> {
                            fitPoissonsSites(entry.getKey(), entry.getValue());
                        }
        );
    }

    private void fitPoissonsSites(String site, Map<String, Pair<Integer, List<Pair<double[], Vector>>>> value) {
        int numID = value.size();
        MapDataFormat data = new MapDataFormat(numID-1);
        //TODO: Debug
        Map<Integer, String> indexMap=new HashMap<>();
        // geenrate data
        value.entrySet().stream().forEach(
                entry -> {
                    int index = entry.getValue().getFirst();
                    String ID = entry.getKey();
                    for (Pair<double[], Vector> pair : entry.getValue().getSecond()) {
                        double[] first = pair.getFirst();
                        double total = first[0];
                        double death = first[1];
                        Vector feature = new DenseVector(numID);
                        feature.set(index, 1.0);
                        data.insertRowdata(site, ID, total, death, feature);
                    }
                    // TODO: Debug
                    if(site.equals("100010002")) {
                        indexMap.put(index, ID);
//                        System.out.println(ID + ": " + index);
                    }
                }
        );

        // optimizer
        PoissonBackFit classifier1 = new PoissonBackFit(numID-1);
        PoissonBackFitOptimizer loss1 = new PoissonBackFitOptimizer(classifier1, data);
        LBFGS optimizer1 = new LBFGS(loss1);
//        optimizer1.getTerminator().setMode(FINISH_MAX_ITER);
//        optimizer1.getTerminator().setMaxIteration(numIter);
//        GradientDescent optimizer1 = new GradientDescent(loss1);
        optimizer1.optimize();
        //TODO: Debug
        if(site.equals("100010002")) {
//            System.out.println("numIter: " + numIter);
//            System.out.println("coefs: " + classifier1.getCoefficient());
            System.out.println("loss: " + optimizer1.getTerminator().getHistory());
            System.out.println("estimate the ID log poisson model ... ");
            for (int i=0; i<classifier1.getCoefficient().size(); i++) {
                System.out.println(indexMap.get(i) + ": " + classifier1.getCoefficient().get(i));
            }
        }

        // finally update the logOffset.
        value.entrySet().stream().forEach(
                entry -> {
                    String ID = entry.getKey();
                    List<Pair<double[], Vector>> listPairsFromLocs = data.getData().get(site).get(ID).getSecond();
                    int index = entry.getValue().getFirst();
                    List<Pair<double[], Vector>> listPairs = entry.getValue().getSecond();
//                    if (site.equals("100010002") && ID.equals("67FNW")) {
//                        System.out.println("DEBUG............\n\n\n");
//                        System.out.println("index of " + ID + ": " + index);
//                        for (Pair<double[], Vector> pair: listPair) {
//                            double[] first = pair.getFirst();
//                            Vector second = pair.getSecond();
//                            System.out.print(first[0] + "\t" + Math.round(first[1]) + "\t" + second.get(0) + "\t" +
//                            classifier1.getCoefficient().get(index) + "\t" + (classifier1.getCoefficient().get(index) + second.get(0)) +
//                            "\t");
//                            double logOff = pair.getSecond().get(0);
//                            pair.getFirst()[0] = Math.exp(classifier1.getCoefficient().get(index)+Math.log(pair.getFirst()[0]) + logOff);
//                            System.out.println(pair.getFirst()[0] + "\t" + Math.log(pair.getFirst()[0]));
//                        }
//                    } else {
//                        for (Pair<double[], Vector> pair: listPair) {
//                            double logOff = pair.getSecond().get(0);
//                            pair.getFirst()[0] = Math.exp(classifier1.getCoefficient().get(index)+Math.log(pair.getFirst()[0]) + logOff);
//                        }
//                    }



                    for (int i=0; i<listPairs.size(); i++) {
                        Pair<double[], Vector> pair = listPairsFromLocs.get(i);
                        // TODO: check predict
//                        double predict = classifier1.dotVector(pair.getSecond()) + Math.log(pair.getFirst()[0]);
//                        predict -= Math.log(pair.getFirst()[0]);
                        double predict = classifier1.dotVector(pair.getSecond()) + Math.log(pair.getFirst()[0]);

                        Pair<double[], Vector> pair1 = listPairs.get(i);
                        pair1.getFirst()[0] = Math.exp(pair1.getSecond().get(0)  + predict);
//                        logOffsets.get(site).put(ID, predict-pair.getFirst()[0]);
                    }
                }
        );

        // TODO: debug
        value.entrySet().stream().forEach(
                entry -> {
                    List<Pair<double[], Vector>> listPair = entry.getValue().getSecond();
                    if (site.equals("100010002") && entry.getKey().equals("67FNW")) {
                        System.out.println("After Step B offsets --- ageeffect");
                        for (Pair<double[], Vector> pair : listPair) {
                            double[] first = pair.getFirst();
                            Vector second = pair.getSecond();
                            double total = first[0];
                            double death = first[1];
                            double logN = second.get(0);
                            double feature = second.get(1);
                            double effect = logN + classifier.dotVectorWithoutBias(pair.getSecond());
                            System.out.println(total + "\t" + Math.round(death) + "\t" + logN + "\t" + effect + "\t" + feature);
                        }
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }




    public void fitPoisson() {
//        this.classifier = new PoissonBackFit(data.getNumFeatures());
//        this.loss = new PoissonBackFitOptimizer(classifier, data);
        LBFGS optimizer = new LBFGS(loss);
//        optimizer.getTerminator().setMode(Terminator.Mode.FINISH_MAX_ITER);
//        optimizer.getTerminator().setMaxIteration(numIter);
        optimizer.optimize();
        System.out.println("loss for mian LBFGS: " + optimizer.getTerminator().getHistory());
    }

    public int getNumIter() {
        return numIter;
    }

    public void setNumIter(int numIter) {
        this.numIter = numIter;
    }

}
