package com.molodos.codingchallenge;

import com.molodos.codingchallenge.models.Item;
import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;

import java.io.*;
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
     * @param trucks     The trucks that were loaded
     * @param spareItems A list of not loaded items
     * @param fileName   The name of the csv file to save the solution to
     */
    public static void saveSolution(Truck[] trucks, ItemList spareItems, String fileName) {
        // Initialize a StringBuilder to save file outputs and add item name header
        StringBuilder output = new StringBuilder("Hardware");

        // Add headers for all truck columns
        for (Truck truck : trucks) {
            // Add prefix
            output.append(",Einheiten ");

            // Add truck name and escape characters if needed
            if (truck.getName().contains("\"") || truck.getName().contains(",")) {
                output.append("\"").append(truck.getName().replaceAll("\"", "\"\"")).append("\"");
            } else {
                output.append(truck.getName());
            }
        }

        // Add header for total value and end line
        output.append(",Einheiten Gesamt\r\n");

        // Print item counts for each item and truck
        for (Item item : spareItems.getItems()) {
            // Add item name and escape characters if needed
            if (item.getName().contains("\"") || item.getName().contains(",")) {
                output.append("\"").append(item.getName().replaceAll("\"", "\"\"")).append("\"");
            } else {
                output.append(item.getName());
            }

            // Append units in all trucks and calculate total
            int total = 0;
            for (Truck truck : trucks) {
                int units = truck.getUnits(item);
                total += units;
                output.append(",").append(units);
            }

            // Append total and end line
            output.append(",").append(total).append("\r\n");
        }

        // Append empty line
        output.append(",".repeat(trucks.length + 1)).append("\r\n");

        // Add headers for truck totals
        for (Truck truck : trucks) {
            // Add comma separator
            output.append(",");

            // Add truck name and escape characters if needed
            if (truck.getName().contains("\"") || truck.getName().contains(",")) {
                output.append("\"");
                output.append(truck.getName().replaceAll("\"", "\"\""));
                output.append("\"");
            } else {
                output.append(truck.getName());
            }
        }

        // Add header for total value and end line
        output.append(",Gesamt\r\n");

        // Add total weights with driver
        output.append("Gewicht inklusive Fahrer");
        double totalWeight = 0;
        for (Truck truck : trucks) {
            output.append(",");
            totalWeight += truck.getCapacity() - truck.getRemainingCapacity();
            output.append(truck.getCapacity() - truck.getRemainingCapacity()).append("g");
        }
        output.append(",").append(totalWeight).append("g\r\n");

        // Add free capacities
        output.append("Freie Kapazität");
        double totalFree = 0;
        for (Truck truck : trucks) {
            output.append(",");
            totalFree += truck.getRemainingCapacity();
            output.append(truck.getRemainingCapacity()).append("g");
        }
        output.append(",").append(totalFree).append("g\r\n");

        // Add values
        output.append("Nutzwert");
        double totalValue = 0;
        for (Truck truck : trucks) {
            output.append(",");
            totalValue += truck.getTotalValue();
            output.append(truck.getTotalValue());
        }
        output.append(",").append(totalValue).append("\r\n");

        // Append empty line
        output.append(",".repeat(trucks.length + 1)).append("\r\n");

        // Add list of items left
        String suffix = ",".repeat(trucks.length);
        output.append("Nicht verladene Hardware,Einheiten").append(suffix).append("\r\n");
        for (Item item : spareItems.getItems()) {
            // Add item name and escape characters if needed
            if (item.getName().contains("\"") || item.getName().contains(",")) {
                output.append("\"").append(item.getName().replaceAll("\"", "\"\"")).append("\"");
            } else {
                output.append(item.getName());
            }

            // Add item units
            output.append(",").append(item.getUnits()).append(suffix).append("\r\n");
        }

        // Write to file
        BufferedWriter writer = null;
        try {
            // Open file and write
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(output.toString());
        } catch (Exception e) {
            // Print exception
            e.printStackTrace();
        } finally {
            // Close writer if still open
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
