package edu.neu.ccs.survival.data_formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rainicy on 2/27/20
 */
public class Util {
    /**
     * For the given columns names, return their indexes.
     * Assume the first row is the headers, e.g. ID, ZIPCODE, pm, ozone and etc.
     * If columnNames = List("pm", "ozone"), then return an array of [2, 3].
     * @param input
     * @param columnNames
     * @param sep
     * @return the array of indexes
     */
    public static int[] getColumnIndexByName(String input, List<String> columnNames, String sep) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(input));
        String header;
        while((header = br.readLine()) != null) {
            break;
        }
        Map<String, Integer> nameToIndex = new HashMap<>();

        String[] names = header.split(sep, -1);

        for (int i=0; i<names.length; i++) {
            nameToIndex.putIfAbsent(names[i].toLowerCase(), i);
        }

        List<Integer> indexes = new LinkedList<>();

        for (String name : columnNames) {
            try {
                int index = nameToIndex.get(name.toLowerCase());
                indexes.add(index);
            } catch (Exception e) {
                throw new RuntimeException("Failed to find the column in the given file: " + name);
            }
        }

        return indexes.stream()
                .mapToInt(Integer :: intValue)
                .toArray();
    }

    /**
     * For the given one column name, return their index.
     * Assume the first row is the headers, e.g. ID, ZIPCODE, pm, ozone and etc.
     * If columnName = "ozone", then return an integer 3.
     * @param input
     * @param columnName
     * @param sep
     * @return the array of indexes
     */
    public static int getColumnIndexByName(String input, String columnName, String sep) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(input));
        String header;
        while((header = br.readLine()) != null) {
            break;
        }
        String[] names = header.split(sep, -1);

        for (int i=0; i<names.length; i++) {
            if (names[i].toLowerCase().equals(columnName.toLowerCase())) {
                return i;
            }
        }

        throw new RuntimeException("Failed to find the column in the given file: " + columnName);
    }
}
