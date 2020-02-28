package edu.neu.ccs.survival.data_formatter.brfss;

import edu.neu.ccs.survival.cox_hazards.DataFormat;
import edu.neu.ccs.survival.cox_hazards.MultiKey;
import edu.neu.ccs.pyramid.util.Pair;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rainicy on 3/15/16.
 */
public class Format {
    public static DataFormat loadDSenrolleeByPM(String input, String meanPMFile, double maxAvgPM) throws IOException {
        Set<String> selectedZip = selectZipCode(meanPMFile, maxAvgPM);
        System.out.println("#Zipcode selected: " + selectedZip.size());
        return loadDSenrolleeByGivenZip(input, selectedZip);
    }


    private static Set<String> selectZipCode(String meanPMFile, double maxAvgPM) throws IOException {
        Set<String> selected = new HashSet<>();
        String line = null;
        BufferedReader br = new BufferedReader(new FileReader(meanPMFile));
        int lineCount = -1;
        while ((line=br.readLine()) != null) {
            lineCount += 1;
            // skip header
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            if (Double.parseDouble(lineInfo[1]) <= maxAvgPM) {
                selected.add(lineInfo[0]);
            }
        }
        br.close();
        return selected;
    }


    public static DataFormat loadO3(String input, String group) throws IOException {
        switch (group) {
            case "all": return loadO3(input);
            case "all_no_X1K": return loadO3NoStrataX1K(input);
            case "all_no": return loadO3NoStrata(input);
            case "center_all": return loadO3Center(input);
            case "center_all_loc": return loadO3CenterStrataLocation(input);
            case "center_allDetail": return loadO3CenterDetail(input);
            case "center_all_X1K": return loadO3CenterX1K(input);
            case "center_all_X1KDetail22": return loadO3CenterX1KDetail(input, 22);
            case "center_all_X1KDetail23": return loadO3CenterX1KDetail(input, 23);
            case "center_all_X1KDetail24": return loadO3CenterX1KDetail(input, 24);
            case "center_all_X1KDetail22Nostrata": return loadO3CenterX1KDetailNostrata(input, 22);
            case "center_all_X1KDetail23Nostrata": return loadO3CenterX1KDetailNostrata(input, 23);
            case "center_all_X1KDetail24Nostrata": return loadO3CenterX1KDetailNostrata(input, 24);
            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }



    private static DataFormat loadO3NoStrata(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);

            // set O3
            if (lineInfo[10].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[12]);
            label[1] = Integer.parseInt(lineInfo[13]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3NoStrataX1K(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);

            // set O3
            if (lineInfo[10].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10])*1000);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[12]);
            label[1] = Integer.parseInt(lineInfo[13]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadO3CenterX1KDetailNostrata(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])*1000);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterX1KDetail(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];
            if (ID.length()<4) {
                continue;
            }
            String age = ID.substring(0,2);
            String sex = ID.substring(2,3);
            String race = ID.substring(3);



            // set O3
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex])*1000);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterX1K(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];

            // set O3
            if (lineInfo[22].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[22])*1000);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterDetail(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];
            if (ID.length()<4) {
                continue;
            }
            String age = ID.substring(0,2);
            String sex = ID.substring(2,3);
            String race = ID.substring(3);


            // set O3
            if (lineInfo[22].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[22]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3CenterStrataLocation(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];
            String loc = lineInfo[0];

            // set O3
            if (lineInfo[22].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[22]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            key.setRegion(loc);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3Center(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String ID = lineInfo[4];

            // set O3
            if (lineInfo[22].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[22]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(ID);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    private static DataFormat loadO3(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            int year = Integer.parseInt(lineInfo[1]);
            int month = Integer.parseInt(lineInfo[2]);
            String date = String.valueOf((year-1900)*12 + month);
            String sex = lineInfo[6];
            String race = lineInfo[7];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set O3
            if (lineInfo[10].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[12]);
            label[1] = Integer.parseInt(lineInfo[13]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadCleanDS(String input, String group) throws IOException {
        switch (group) {
            case "pm": return loadCleanDS(input, 7);
            case "pmc": return loadCleanDS(input, 8);
            case "no2": return loadCleanDS(input, 9);
            case "no2c": return loadCleanDS(input, 10);
            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }

    public static DataFormat loadCleanDS(String input, int featureIndex) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int lineCount = -1;
        while ((line = br.readLine()) != null) {
            lineCount += 1;
            if (lineCount < 1) {
                continue;
            }
            String[] lineInfo = line.split(",", -1);
            // none exist for value
            if (lineInfo[featureIndex].equals("")) {
                continue;
            }


            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[2];
            String race = lineInfo[3];
            String age = lineInfo[4];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }
            // set feature value
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[featureIndex]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadDSenrollee(String input, String group) throws IOException {
        switch (group) {
            case "all":  return loadDSenrollee(input);
            case "all_lung":  return loadDSenrolleeByLungCancer(input);
            case "all_nostrata": return loadDSenrolleeNoStrata(input);
            case "all.s.by.age": return loadDSenrolleeStrataByAge(input);
            case "no2": return loadDSenrolleeNo2(input);
            case "no2_lung": return loadDSenrolleeNo2ByLungCancer(input);
            case "centeredNo2": return loadDSenrolleeCenteredNo2(input);
            case "centeredNo2_lung": return loadDSenrolleeCenteredNo2ByLungCancer(input);
            case "no2_nostrata": return loadDSenrolleeNo2NoStrata(input);
            case "centeredNo2_nostrata": return loadDSenrolleeCenteredNo2NoStrata(input);
            case "no2byUrban": return loadDSenrolleeNo2ByUrban(input);
            case "centeredNo2byUrban": return loadDSenrolleeCenteredNo2ByUrban(input);
            /// by areas on Centered No2
            case "centeredNo2byArea1": return loadDSenrolleeCenteredNo2ByArea(input, "1");
            case "centeredNo2byArea2": return loadDSenrolleeCenteredNo2ByArea(input, "2");
            case "centeredNo2byArea3": return loadDSenrolleeCenteredNo2ByArea(input, "3");
            case "centeredNo2byArea4": return loadDSenrolleeCenteredNo2ByArea(input, "4");

            ///
            case "no2byUrbanByPercentage": return loadDSenrolleeNo2ByUrbanByPercentage(input);
            case "no2byUrbanByPercentageMiddle": return loadDSenrolleeNo2ByUrbanByPercentageMiddle(input);
            case "no2byUrbanByPercentageLow": return loadDSenrolleeNo2ByUrbanByPercentageLow(input);
            case "no2byUrbanByPercentageLeaveLow": return loadDSenrolleeNo2ByUrbanByPercentageLeaveLow(input);
            case "no2byUrbanByPercentageLeaveUp": return loadDSenrolleeNo2ByUrbanByPercentageLeaveUp(input);
            /////
            case "centeredNo2byUrbanByPercentage": return loadDSenrolleeCenteredNo2ByUrbanByPercentage(input);
            case "centeredNo2byUrbanByPercentageMiddle": return loadDSenrolleeCenteredNo2ByUrbanByPercentageMiddle(input);
            case "centeredNo2byUrbanByPercentageLow": return loadDSenrolleeCenteredNo2ByUrbanByPercentageLow(input);
            case "centeredNo2byUrbanByPercentageLeaveLow": return loadDSenrolleeCenteredNo2ByUrbanByPercentageLeaveLow(input);
            case "centeredNo2byUrbanByPercentageLeaveUp": return loadDSenrolleeCenteredNo2ByUrbanByPercentageLeaveUp(input);
            /////
            case "no2byNewUrban": return loadDSenrolleeNo2ByNewUrban(input);
            case "no2bySubUrban": return loadDSenrolleeNo2BySubUrban(input);
            case "centeredNo2bySubUrban": return loadDSenrolleeCenteredNo2BySubUrban(input);
            case "no2byNewSubUrban": return loadDSenrolleeNo2ByNewSubUrban(input);
            case "no2byRural": return loadDSenrolleeNo2ByRural(input);
            case "centeredNo2byRural": return loadDSenrolleeCenteredNo2ByRural(input);
            case "no2byNewRural": return loadDSenrolleeNo2ByNewRural(input);
            // pm and no2 as two feature together.
            case "pm_no2": return loadDSenrolleePMNo2(input);
            case "pm_centeredNo2": return loadDSenrolleePMCenteredNo2(input);
            // pm and no2 and product of pm and no2 three features
            case "pm_no2_com": return  loadDSenrolleePMNo2Com(input);
            case "pm_centeredNo2_com": return  loadDSenrolleePMCenteredNo2Com(input);
            // load with some features included
            case "gender": return loadDSenrolleeWithGender(input);
            case "male": return loadDSenrolleeByMale(input);
            case "centeredNo2byMale": return loadDSenrolleeCenteredNo2ByMale(input);
            case "female": return loadDSenrolleeByFemale(input);
            case "centeredNo2byFemale": return loadDSenrolleeCenteredNo2ByFemale(input);
            case "more75": return loadDSenrolleeByMore75(input);
            case "centeredNo2More75": return loadDSenrolleeCenteredNo2ByMore75(input);
            case "less75": return loadDSenrolleeByLess75(input);
            case "centeredNo2Less75": return loadDSenrolleeCenteredNo2ByLess75(input);
            case "white": return loadDSenrolleeByWhite(input);
            case "centeredNo2White": return loadDSenrolleeCenteredNo2ByWhite(input);
            case "nonwhite": return loadDSenrolleeByNonWhite(input);
            case "centeredNo2Nonwhite": return loadDSenrolleeCenteredNo2ByNonWhite(input);
            case "urban": return loadDSenrolleeByUrban(input);
            case "suburban": return loadDSenrolleeBySuburban(input);
            case "rural": return loadDSenrolleeByRural(input);
            case "nonurban": return loadDSenrolleeByNonUrban(input);

            // load with centered PM by different filters.
            case "centeredPMAll": return loadDSenrolleeCenteredPM(input);
            case "centeredPMMale": return loadDSenrolleeCenteredPMByMale(input);
            case "centeredPMFemale": return loadDSenrolleeCenteredPMByFemale(input);
            case "centeredPMMore75": return loadDSenrolleeCenteredPMByMore75(input);
            case "centeredPMLess75": return loadDSenrolleeCenteredPMByLess75(input);
            case "centeredPMWhite": return loadDSenrolleeCenteredPMByWhite(input);
            case "centeredPMNonwhite": return loadDSenrolleeCenteredPMByNonWhite(input);
            case "centeredPMUrban": return loadDSenrolleeCenteredPMByUrban(input);
            case "centeredPMSuburban": return loadDSenrolleeCenteredPMBySuburban(input);
            case "centeredPMRural": return loadDSenrolleeCenteredPMByRural(input);
            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }

    public static DataFormat loadSample(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // key
            String date = lineInfo[0];
            MultiKey key = new MultiKey(date);

            // vector
            DenseVector vector = new DenseVector(3);
            vector.set(0, Double.parseDouble(lineInfo[2]));
            vector.set(1, Double.parseDouble(lineInfo[3]));
            vector.set(2, Double.parseDouble(lineInfo[4]));

            // label
            int[] label = new int[2];
            int death = Integer.parseInt(lineInfo[1]);
            label[0] = 1;
            label[1] = death;

            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);

        }

        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadtestData8StrataAge(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int cline = 0;
        while ((line = br.readLine()) != null) {
            cline++;
            if (cline == 1) {
                continue;
            }
            String[] lineInfo = line.split("\\t", -1);

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[3];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadtestData8(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        int cline = 0;
        while ((line = br.readLine()) != null) {
            cline++;
            if (cline == 1) {
                continue;
            }
            String[] lineInfo = line.split("\\t", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[4];
            String race = lineInfo[5];
            String age = lineInfo[3];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * given the input file path: brfss.cvs, with 18 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 1.location, 17.DeathRatio,
     *      2) strata columns: 2.date, 12.race, 13.sex, 14.age
     *      3) combined records for ages=90+.
     *      4) 3,4,5. Region to be specified.
     *
     * @param input
     * @return
     */
    public static DataFormat loadBRFSSPM(String input, int region) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            if (!lineInfo[region].equals("1")) {
                continue;
            }
            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[7];
            String race = "5";
            for (int i=12; i>7; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-8);
                }
            }
            String age = lineInfo[14];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * given the input file path: brfss.cvs, with 18 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 3,4,5. Region, 17.DeathRatio,
     *      2) strata columns: 2.date, 12.race, 13.sex, 14.age
     *      3) combined records for ages=90+.
     *      4) Map key based on 1.location.
     *
     * @param input
     * @return
     */
    public static Map<String, DataFormat> loadBRFSSPMByLocations(String input) throws IOException {
        Map<String, DataFormat> result = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;

        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            // parse the location
            String location = lineInfo[1];
            if (!result.containsKey(location)) {
                result.put(location, new DataFormat());
            }

            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[7];
            String race = "5";
            for (int i=12; i>7; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-8);
                }
            }
            String age = lineInfo[14];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            result.get(location).insertRowData(key, pair);
        }

        for (Map.Entry<String, DataFormat> entry : result.entrySet()) {
            entry.getValue().setNumFeatures(1);
            entry.getValue().setNumStrata(entry.getValue().getData().size());
        }
        return  result;
    }

    /**
     * given the input file path: brfss.cvs, with 19 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 1.location, 18.DeathRatio,
     *      2) strata columns: 2.date, 9-14.race, 8.sex, 15.age, 3,4,5,6. regions
     *      3) combined records for ages=90+.
     *      4) feature vector: 7.PM values
     *
     * @param input
     * @return
     */
    public static DataFormat loadBRFSSPM_StrataRegions(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[8];
            String race = "5";
            for (int i=14; i>8; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-9);
                }
            }

            String region = "0";
            for (int i=3; i<7; i++) {
                if (lineInfo[i].equals("1")) {
                    region = Integer.toString(i-3);
                }
            }

            String age = lineInfo[15];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[7]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[16]);
            label[1] = Integer.parseInt(lineInfo[17]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex, region);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * given the input file path: brfss.cvs, with 19 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 1.location, 18.DeathRatio,
     *      2) strata columns: 2.date, 9-14.race, 8.sex, 15.age
     *      3) combined records for ages=90+.
     *      4) feature vector: 3,4,5,6. regions and 7.PM values
     *
     * @param input
     * @return
     */
    public static DataFormat loadBRFSSPMRegions(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);


            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[8];
            String race = "5";
            for (int i=14; i>8; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-9);
                }
            }
            String age = lineInfo[15];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(5);
            vector.set(0, Double.parseDouble(lineInfo[7]));
            vector.set(1, Double.parseDouble(lineInfo[3]));
            vector.set(2, Double.parseDouble(lineInfo[4]));
            vector.set(3, Double.parseDouble(lineInfo[5]));
            vector.set(4, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[16]);
            label[1] = Integer.parseInt(lineInfo[17]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(5);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * given the input file path: brfss.cvs, with 18 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 1.location, 3,4,5. Region, 17.DeathRatio,
     *      2) strata columns: 2.date, 12.race, 13.sex, 14.age
     *      3) combined records for ages=90+.
     *
     * @param input
     * @return
     */
    public static DataFormat loadBRFSSPM(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[7];
            String race = "5";
            for (int i=13; i>7; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-8);
                }
            }
            String age = lineInfo[14];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadDSenrolleeByThreshold(String input, String group, Double threshold) throws IOException {
        switch (group) {
            case "all": return loadDSenrolleeByThreshold(input, threshold);
            default: throw new RuntimeException("group: " + group + " is not supported yet!");
        }
    }

    private static DataFormat loadDSenrolleeByThreshold(String input, double threshold) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            double pm = Double.parseDouble(lineInfo[2]);
            if (pm >= threshold) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, pm);

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    private static DataFormat loadDSenrolleeWithGender(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // set PM and Gender
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[2]));
            vector.set(1, Integer.parseInt(lineInfo[3])-1);

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            key.setRace(race);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadDSenrolleeByNonUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("2") || (region.equals("3"))) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset centered PM but only choose the people who are rural.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByRural(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("3")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are rural.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByRural(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("3")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset centered but only choose the people who are suburban.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMBySuburban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are suburban.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeBySuburban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are new rural.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByNewRural(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[11];
            if (region.equals("3")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are rural.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByRural(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("3")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are rural.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByRural(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("3")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are from new suburban.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByNewSubUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[11];
            if (region.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are  suburban.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2BySubUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2BySubUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are from new urban code:
     * the source code are from: ua_zcta_rel_10.txt
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByNewUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[11];
            if (region.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages more than 33%.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrbanByPercentageLeaveLow(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region > 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages more than 33%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrbanByPercentageLeaveLow(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region > 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages lower than 33%.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrbanByPercentageLow(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region <= 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban percentages lower than 33%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrbanByPercentageLow(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region <= 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages between 33%~67%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrbanByPercentageMiddle(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region <= 0.67 && region > 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban percentages between 33%~67%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrbanByPercentageMiddle(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region <= 0.67 && region > 0.33) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages over 75%.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrbanByPercentageLeaveUp(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region <= 0.67) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban percentages over 75%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrbanByPercentageLeaveUp(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region < 0.67) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages over 67%.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrbanByPercentage(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region > 0.67) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are urban percentages over 75%.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrbanByPercentage(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            Double region = Double.parseDouble(lineInfo[11]);
            if (region > 0.67) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are in the given areacode "1, 2, 3, 4".
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByArea(String input, String areaCode) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String area = lineInfo[9];
            if (area.equals(areaCode)) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban.
     * only use feature centered NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban.
     * only use feature NO2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeNo2ByUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set No2
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[10]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset centered PM but only choose the people who are urban.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are urban.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByUrban(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String region = lineInfo[8];
            if (region.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are non-white.
     * model on centered No2
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByNonWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (!race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset centered PM but only choose the people who are non-white.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByNonWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (!race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are non-white.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByNonWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (!race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are white.
     * model on centered No2.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset but only choose the people who are white.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose the people who are white.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByWhite(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String race = lineInfo[4];
            if (race.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setSex(sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     *  load DSenrollee dataset but only choose the people who are smaller than 75.
     *  model on centered no2.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByLess75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt<=75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     *  load DSenrollee dataset centered PM but only choose the people who are smaller than 75.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByLess75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt<=75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     *  load DSenrollee dataset but only choose the people who are smaller than 75.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByLess75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt<=75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }


    /**
     *  load DSenrollee dataset but only choose the people who are strictly larger than 75.
     *  model on centered No2.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByMore75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt>75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     *  load DSenrollee dataset centered PM but only choose the people who are strictly larger than 75.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByMore75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt>75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     *  load DSenrollee dataset but only choose the people who are strictly larger than 75.
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByMore75(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt>75) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String sex = lineInfo[3];

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setSex(sex);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset model on centered No2 but only choose females
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByFemale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset on Centered PM but only choose females
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByFemale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose females
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByFemale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("2")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }


    /**
     * load DSenrollee dataset model on centered but only choose males
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredNo2ByMale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[12]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    /**
     * load DSenrollee dataset on centered PM but only choose males
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeCenteredPMByMale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[13]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }
    /**
     * load DSenrollee dataset but only choose males
     * @param input
     * @return
     * @throws IOException
     */
    private static DataFormat loadDSenrolleeByMale(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String sex = lineInfo[3];
            if (sex.equals("1")) {
                // parse key
                String date = lineInfo[1];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90) {
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: alive and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }
                // insert row data
                MultiKey key = new MultiKey(date);
                key.setAge(age);
                key.setRace(race);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());
        br.close();

        return data;
    }

    public static DataFormat loadDSenrolleeStrataByAge(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;

    }

    /**
     * Model on centered no2 without any strata
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static DataFormat loadDSenrolleeCenteredNo2NoStrata(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];

            // set No2
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[12]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * Model on no2 without any strata
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static DataFormat loadDSenrolleeNo2NoStrata(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];

            // set No2
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadDSenrolleeNoStrata(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    // three features: 0:PM; 1:centered No2; 2: PM*centered_No2
    public static DataFormat loadDSenrolleePMCenteredNo2Com(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM and No2
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[2]);
            double no2 = Double.parseDouble(lineInfo[12]);
            vector.set(0,pm);
            vector.set(1, no2);
            vector.set(2, pm*no2);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }
    // three features: 0:PM; 1:No2; 2: PM*No2
    public static DataFormat loadDSenrolleePMNo2Com(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM and No2
            DenseVector vector = new DenseVector(3);
            double pm = Double.parseDouble(lineInfo[2]);
            double no2 = Double.parseDouble(lineInfo[10]);
            vector.set(0,pm);
            vector.set(1, no2);
            vector.set(2, pm*no2);

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(3);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * two features: 0: PM and 1: centered No2
     */
    public static DataFormat loadDSenrolleePMCenteredNo2(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM and No2
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[2]));
            vector.set(1, Double.parseDouble(lineInfo[12]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }
    // two features: 0: PM and 1:No2
    public static DataFormat loadDSenrolleePMNo2(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM and No2
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[2]));
            vector.set(1, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadDSenrolleeCenteredNo2ByLungCancer(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set centered No2, skip the ones without No2 data.
            if (lineInfo[12].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[12]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[14]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
    /**
     * centered no2 data
     * @param input
     * @return
     * @throws IOException
     */
    public static DataFormat loadDSenrolleeCenteredNo2(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set centered No2
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[12]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }


    public static DataFormat loadDSenrolleeNo2ByLungCancer(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set No2
            if (lineInfo[10].equals("")) {
                continue;
            }
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }
//    it's just using No2 as the feature, and sex,races, ages as the strata
    public static DataFormat loadDSenrolleeNo2(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set No2
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[10]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * Centered PM for whole country.
     * @param input
     * @return
     * @throws IOException
     */
    public static DataFormat loadDSenrolleeCenteredPM(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[13]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    private static DataFormat loadDSenrolleeByGivenZip(String input, Set<String> selectedZip) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            String zipcode = lineInfo[0];
            if (selectedZip.contains(zipcode)) {
                // parse key
                String date = lineInfo[1];
                String sex = lineInfo[3];
                String race = lineInfo[4];
                String age = lineInfo[5];
                int ageInt = Integer.parseInt(age);
                if (ageInt >= 90){
                    age = "90";
                }

                // set PM
                DenseVector vector = new DenseVector(1);
                vector.set(0, Double.parseDouble(lineInfo[2]));

                // parse label: life and death
                int[] label = new int[2];
                label[0] = Integer.parseInt(lineInfo[6]);
                label[1] = Integer.parseInt(lineInfo[7]);
                if (label[0] == 0) {
                    continue;
                }

                // insert row data
                MultiKey key = new MultiKey(date, age, race, sex);
                Pair<int[], Vector> pair = new Pair<>(label, vector);
                data.insertRowData(key, pair);
            }
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadDSenrolleeByLungCancer(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[14]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadDSenrollee(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String sex = lineInfo[3];
            String race = lineInfo[4];
            String age = lineInfo[5];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90){
                age = "90";
            }

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[6]);
            label[1] = Integer.parseInt(lineInfo[7]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    /**
     * given the input file path: brfss.cvs, with 18 columns.
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 1.location, 17.DeathRatio;
     *      2) strata columns: 2.date, 12.race, 13.sex, 14.age
     *      3) combined records for ages=90+.
     *
     * @param input
     * @return
     */
    public static DataFormat loadBRFSS(String input) throws IOException {
        DataFormat data = new DataFormat();

        Set<String> dateSet = new HashSet<>();
        Set<String> sexSet = new HashSet<>();
        Set<String> raceSet = new HashSet<>();
        Set<String> ageSet = new HashSet<>();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[7];
            String race = "5";
            for (int i=12; i>7; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-8);
                }
            }
            String age = lineInfo[14];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            dateSet.add(date);
            sexSet.add(sex);
            raceSet.add(race);
            ageSet.add(age);

            // parse vector
            DenseVector vector = new DenseVector(4);
            vector.set(0, Integer.parseInt(lineInfo[3]));
            vector.set(1, Integer.parseInt(lineInfo[4]));
            vector.set(2, Integer.parseInt(lineInfo[5]));
            vector.set(3, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);

//            System.out.print("line: " + ++count + "\t");
//            System.out.print("key: " + key + "\t");
//            System.out.println("pair: " + Arrays.toString(pair.getFirst()) + " - " + pair.getSecond());
//            System.in.read();
        }

        data.setNumFeatures(4);
        data.setNumStrata(data.getData().size());

//        System.out.println(data);

        return data;
    }

    /**
     * given the input file path:
     *                             1.brfss.cvs;
     *                             2.siteid_state.csv
     *                             3.map_site_id.txt
     * return the DataFormat by:
     *      1) removing columns: 0.ID, 3,4,5. Region, 17.DeathRatio,
     *      2) strata columns: 2.date, 12.race, 13.sex, 14.age
     *      3) combined records for ages=90+.
     *      4) Map key based on 1.states.
     *
     * @param input
     * @return
     * @param input
     * @param stateInfo
     * @param locationInfo
     * @return
     * @throws IOException
     */
    public static Map<String, DataFormat> loadBRFSSPMByStates(String input, String stateInfo, String locationInfo) throws IOException {

        // STEP 1: build map for site-location: key is location and value is site
        Map<String, String> locSiteMap = new HashMap<>();
        BufferedReader br1 = new BufferedReader(new FileReader(locationInfo));
        String line;
        while ((line=br1.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            locSiteMap.put(lineInfo[1], lineInfo[0]);
        }
        br1.close();

        // STEP 2: build map for state-site: key is site, value is state
        Map<String, String> siteStateMap = new HashMap<>();
        BufferedReader br2 = new BufferedReader(new FileReader(stateInfo));
        while((line=br2.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);
            String key = lineInfo[3];
            String value = lineInfo[1];
            if (!siteStateMap.containsKey(key)) {
                siteStateMap.put(key, value);
            } else {
                if (!value.equals(siteStateMap.get(key))) {
                    System.out.println(key + ": " + siteStateMap.get(key) + " != " + value);
                }
            }
        }
        br2.close();


        // STEP 3: read data
        Map<String, DataFormat> result = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(input));
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // get state
            String state = siteStateMap.get(locSiteMap.get(lineInfo[1]));
            if (!result.containsKey(state)) {
                result.put(state, new DataFormat());
            }

            // parse key
            String date = lineInfo[2];
            String sex = lineInfo[7];
            String race = "5";
            for (int i=12; i>7; i--) {
                if (lineInfo[i].equals("1")) {
                    race = Integer.toString(i-8);
                }
            }
            String age = lineInfo[14];
            int ageInt = Integer.parseInt(age);
            if (ageInt >= 90) {
                age = "90";
            }

            // parse vector
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[6]));

            // parse label
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[15]);
            label[1] = Integer.parseInt(lineInfo[16]);

            // insert row data
            MultiKey key = new MultiKey(date, age, race, sex);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            result.get(state).insertRowData(key, pair);
        }

        for (Map.Entry<String, DataFormat> entry : result.entrySet()) {
            entry.getValue().setNumFeatures(1);
            entry.getValue().setNumStrata(entry.getValue().getData().size());
        }
        return  result;
    }

    public static DataFormat loadTestPrev(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[3];

            // set PM
            DenseVector vector = new DenseVector(1);
            vector.set(0, Double.parseDouble(lineInfo[2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            key.setAge(age);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(1);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadTestPrevNoStrata(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[3];

            // set PM
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(lineInfo[2]));
            vector.set(1, Double.parseDouble(age));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }

    public static DataFormat loadTestPrevNoStrataAge(String input) throws IOException {
        DataFormat data = new DataFormat();

        BufferedReader br = new BufferedReader(new FileReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(",", -1);

            // parse key
            String date = lineInfo[1];
            String age = lineInfo[3];

            // set PM
            DenseVector vector = new DenseVector(2);
            vector.set(0, Double.parseDouble(age));
            vector.set(1, Double.parseDouble(lineInfo[2]));

            // parse label: life and death
            int[] label = new int[2];
            label[0] = Integer.parseInt(lineInfo[5]);
            label[1] = Integer.parseInt(lineInfo[6]);
            if (label[0] == 0) {
                continue;
            }

            // insert row data
            MultiKey key = new MultiKey(date);
            Pair<int[], Vector> pair = new Pair<>(label, vector);
            data.insertRowData(key, pair);
        }

        br.close();
        data.setNumFeatures(2);
        data.setNumStrata(data.getData().size());

        return data;
    }


}
