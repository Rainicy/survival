package edu.neu.ccs.survival.data_formatter.poisson;

import edu.neu.ccs.survival.poisson.DataFormat;
import edu.neu.ccs.survival.poisson.MapDataFormat;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Rainicy on 6/20/17.
 */
public class Format {

    public static DataFormat loadTestData8(String input, int numData, int numFeatures) throws IOException {
        DataFormat data = new DataFormat(numData, numFeatures);

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        while (((line = br.readLine())!= null) && (lineCount < numData+1)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split("\\t");

                // pm
                double pm = Double.parseDouble(lineInfo[2]);
                double total = Double.parseDouble(lineInfo[6]);
                int dead = Integer.parseInt(lineInfo[7]);

                //setRowData(int index, int death, int offset, double[] feature)
                double[] feature = new double[1];
                feature[0] = pm;
                data.setRowData(lineCount, dead, total, feature);
            }
        }
        return data;
    }

    public static MapDataFormat loadOzone(String input, String group) throws IOException {
        switch (group) {
            case "1": return loadOzone(input, 1);
            default: {
                throw new RuntimeException("wrong group: " + group);
            }
        }
    }

    public static MapDataFormat loadOzone(String input, int numFeatures) throws IOException {
        MapDataFormat data = new MapDataFormat(1);
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        int dataCount = 0;
        while((line = br.readLine()) != null ){
            lineCount += 1;
            if (lineCount > -1) {
                String[] lineInfo = line.split(",", -1);
                // o3maxh_overal || N || Yall || site
                if (lineInfo[22].equals("") || lineInfo[5].equals("") || lineInfo[6].equals("") || lineInfo[0].equals("")) {
                    continue;
                }
                int N = Integer.parseInt(lineInfo[5]);
                double logOffset = Math.log(N);
                int death = Integer.parseInt(lineInfo[6]);
                double o3 = Double.parseDouble(lineInfo[22]);
                Vector vector = new DenseVector(new double[]{logOffset, o3});
                String site = lineInfo[0];
                String ID = lineInfo[4];
                data.insertRowdata(site, ID, N, death, vector);
                dataCount += 1;
            }
        }
        br.close();
        System.out.println("total line of data: " + dataCount);
        return data;
    }

    /**
     * folder: /scratch/wang.bin/health/DSenrollee/original
     * file name: O3_centered.csv
     * @param input
     * @param numData
     * @param numFeatures
     * @return
     * @throws IOException
     */
    public static DataFormat loadO3(String input, int numData, int numFeatures) throws IOException {
        int infactData = 0;
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        while (((line = br.readLine())!= null) && (lineCount < numData+1)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",", -1);

                // pm
                if (lineInfo[22].equals("")) {
                    continue;
                }
                if (lineInfo[5].equals("")) { // N
                    continue;
                }
                if (lineInfo[6].equals("")) { // Y
                    continue;
                }
                infactData++;
            }
        }

        DataFormat data = new DataFormat(infactData, numFeatures);

        br = new BufferedReader(new FileReader(input));
        lineCount = -2;
        int dataCount = 0;
        while (((line = br.readLine())!= null)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",", -1);

                // pm
                if (lineInfo[22].equals("")) {
                    continue;
                }
                double pm = Double.parseDouble(lineInfo[22]);
                if (lineInfo[5].equals("")) {
                    continue;
                }
                double total = Double.parseDouble(lineInfo[5]);
                if (lineInfo[6].equals("")) {
                    continue;
                }
                int dead = Integer.parseInt(lineInfo[6]);

                //setRowData(int index, int death, int offset, double[] feature)
                double[] feature = new double[1];
                feature[0] = pm;
                data.setRowData(dataCount, dead, total, feature);
                dataCount++;
            }
        }
        br.close();
        System.out.println("total line of data: " + dataCount);
        return data;
    }

    /**
     * folder: /scratch/wang.bin/health/DSenrollee/original/o3cms_asr2008_new.csv
     * file name: O3_centered.csv
     * @param input
     * @param numData
     * @param numFeatures
     * @return
     * @throws IOException
     */
    public static DataFormat loadO3cms(String input, int numData, int numFeatures) throws IOException {
        int infactData = 0;
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        while (((line = br.readLine())!= null) && (lineCount < numData+1)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",", -1);

                // pm
                if (lineInfo[9].equals("")) {
                    continue;
                }
                if (lineInfo[15].equals("")) { // N
                    continue;
                }
                if (lineInfo[16].equals("")) { // Y
                    continue;
                }
                infactData++;
            }
        }

        DataFormat data = new DataFormat(infactData, numFeatures);

        br = new BufferedReader(new FileReader(input));
        lineCount = -2;
        int dataCount = 0;
        while (((line = br.readLine())!= null)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",", -1);

                // o3
                if (lineInfo[9].equals("")) {
                    continue;
                }
                double pm = Double.parseDouble(lineInfo[9]);
                if (lineInfo[15].equals("")) {
                    continue;
                }
                double total = Double.parseDouble(lineInfo[15]);
                if (lineInfo[16].equals("")) {
                    continue;
                }
                int dead = Integer.parseInt(lineInfo[16]);

                //setRowData(int index, int death, int offset, double[] feature)
                double[] feature = new double[1];
                feature[0] = pm;
                data.setRowData(dataCount, dead, total, feature);
                dataCount++;
            }
        }
        br.close();
        System.out.println("total line of data: " + dataCount);
        return data;
    }
    /**
     * file name: pmindex20002012123DS80r.csv
     * @param input
     * @param numData
     * @param numFeatures
     * @return
     * @throws IOException
     */
    public static DataFormat loadPrev(String input, int numData, int numFeatures) throws IOException {
        DataFormat data = new DataFormat(numData, numFeatures);

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        while (((line = br.readLine())!= null) && (lineCount < numData+1)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",");

                // pm
                double pm = Double.parseDouble(lineInfo[6]);
                double total = Double.parseDouble(lineInfo[3]);
                int dead = Integer.parseInt(lineInfo[4]);

                //setRowData(int index, int death, int offset, double[] feature)
                double[] feature = new double[1];
                feature[0] = pm;
                data.setRowData(lineCount, dead, total, feature);
            }
        }
        return data;
    }

    /**
     * file name: pmindex20002012123DS.csv
     * @param input
     * @param numData
     * @param numFeatures
     * @return
     * @throws IOException
     */
    public static DataFormat loadPrev1(String input, int numData, int numFeatures) throws IOException {
        DataFormat data = new DataFormat(numData, numFeatures);

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -2;
        while (((line = br.readLine())!= null) && (lineCount < numData+1)) {
            lineCount += 1;
            // skip header
            if (lineCount > -1) {
                String[] lineInfo = line.split(",");

                // pm
                double pm = Double.parseDouble(lineInfo[7]);
                double total = Double.parseDouble(lineInfo[3]);
                int dead = Integer.parseInt(lineInfo[4]);

                //setRowData(int index, int death, int offset, double[] feature)
                double[] feature = new double[1];
                feature[0] = pm;
                data.setRowData(lineCount, dead, total, feature);
            }
        }
        return data;
    }
}
