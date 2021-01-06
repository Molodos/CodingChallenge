package com.molodos.codingchallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for reading from and writing to files.
 *
 * @author Michael Weichenrieder
 */
public class FileManager {

    public static List<String[]> readCsvFile(String fileName) {
        List<String[]> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(parseCsvLine(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static String[] parseCsvLine(String line) {
        String[] split = line.split(",");
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            StringBuilder column = new StringBuilder(split[i]);
            if (column.toString().startsWith("\"")) {
                while (!column.toString().endsWith("\"")) {
                    i++;
                    column.append(",").append(split[i]);
                }
            }
            column = new StringBuilder(column.toString().trim());
            if (column.toString().startsWith("\"")) {
                column = new StringBuilder(column.substring(1, column.length() - 1).trim());
            }
            columns.add(column.toString().replaceAll("\"\"", "\""));
        }
        return columns.toArray(new String[0]);
    }
}
