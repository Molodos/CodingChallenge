package com.molodos.codingchallenge;

import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;

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

    /**
     * Read a csv file into a list of String arrays containing the columns of the lines.
     *
     * @param fileName Name of the input csv file
     * @return Read and parsed lines
     */
    public static List<String[]> readCsvFile(String fileName) {
        // List to store read and parsed lines
        List<String[]> lines = new ArrayList<>();

        // Read file
        try {
            // Create a reader
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

            // Read line by line
            String line;
            while ((line = br.readLine()) != null) {
                // Parse line and add it to the parsed line list
                lines.add(parseCsvLine(line));
            }
        } catch (Exception e) {
            // Print exception on error
            e.printStackTrace();
        }

        // Return lines
        return lines;
    }

    /**
     * Parses a csv line into an array of Strings to split the columns.
     *
     * @param line Input csv line
     * @return Array of column values in the line
     */
    private static String[] parseCsvLine(String line) {
        // Split the line at commas
        String[] split = line.split(",");

        // A list to store splitted columns to
        List<String> columns = new ArrayList<>();

        // Iterate through splitted line parts
        for (int i = 0; i < split.length; i++) {
            // Add part to column
            StringBuilder column = new StringBuilder(split[i]);

            // If column starts with a quote, the next splitted parts until one ends with a quote, also belong to the column
            if (column.toString().startsWith("\"")) {
                // Add next parts until one ends with a quote
                while (!column.toString().endsWith("\"")) {
                    i++;
                    column.append(",").append(split[i]);
                }
            }

            // Remove leading and trailing spaces
            column = new StringBuilder(column.toString().trim());

            // Remove leading and trailing quotes if exist
            if (column.toString().startsWith("\"")) {
                // Remove quotes and trim again
                column = new StringBuilder(column.substring(1, column.length() - 1).trim());
            }

            // Replace two quotes with one to unescape quotes and add column to list
            columns.add(column.toString().replaceAll("\"\"", "\""));
        }

        // Return parsed line as an array
        return columns.toArray(new String[0]);
    }

    /**
     * Saves the solution of the program execution to a csv file.
     *
     * @param trucks The trucks that were loaded
     * @param spareItems A list of not loaded items
     * @param fileName The name of the csv file to save the solution to
     */
    public static void saveSolution(Truck[] trucks, ItemList spareItems, String fileName) {

    }
}
