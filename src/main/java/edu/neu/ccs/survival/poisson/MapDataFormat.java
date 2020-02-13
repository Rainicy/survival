package poisson;

import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.Vector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rainicy on 11/19/17
 */
public class MapDataFormat {
    private int numFeatures;

    // Site-> ID -> Pair<IndexofID, list<date1(int[N, Death], Vector(logN, feature1, ...))....>>
    private Map<String, Map<String, Pair<Integer, List<Pair<double[], Vector>>>>> data;

    public MapDataFormat(int numFeatures) {
        this.numFeatures = numFeatures;
        this.data = new HashMap<>();
    }

    public void insertRowdata(String site, String ID, double total, double death, Vector feature) {
        if (!data.containsKey(site)) {
            data.put(site, new HashMap<>());
        }
        if (!data.get(site).containsKey(ID)) {
            int preIndex = data.get(site).size();
            data.get(site).put(ID, new Pair<>(preIndex, new LinkedList<>()));
        }
        double[] first = new double[]{total, death};
        Pair<double[], Vector> value = new Pair<>(first, feature);
        data.get(site).get(ID).getSecond().add(value);
    }

    public Map<String, Map<String, Pair<Integer, List<Pair<double[], Vector>>>>> getData() {
        return data;
    }

    public int getNumFeatures() {
        return numFeatures;
    }
}

