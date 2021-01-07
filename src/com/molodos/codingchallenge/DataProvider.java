package com.molodos.codingchallenge;

import com.molodos.codingchallenge.models.Item;
import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for providing Truck objects ant items to fill them with.
 *
 * @author Michael Weichenrieder
 */
public class DataProvider {

    /**
     * Retrieves all trucks to be loaded with items and provides them as an array.
     *
     * @return An array of all trucks to be loaded with items
     */
    public static Truck[] getTrucks() {
        // Initialize a list for retrieved trucks
        List<Truck> trucks = new ArrayList<>();

        // Read input file
        List<String[]> csvLines = FileManager.readCsvFile("trucks.csv");

        // Column numbers of parameters
        int name = -1, capacity = -1, driverWeight = -1;

        // Iterate through all lines
        for (String[] line : csvLines) {
            if (name == -1) {
                // Read first line to get column numbers
                for (int i = 0; i < line.length; i++) {
                    switch (line[i]) {
                        case "name":
                            name = i;
                            break;
                        case "capacity":
                            capacity = i;
                            break;
                        case "driver weight":
                            driverWeight = i;
                    }
                }
            } else {
                // Read line into Truck object and add it to the list
                trucks.add(new Truck(line[name], Double.parseDouble(line[capacity]), Double.parseDouble(line[driverWeight])));
            }
        }

        // Parse list to an array and return it
        return trucks.toArray(new Truck[0]);
    }

    /**
     * Retrieves all possible items to be loaded into trucks and provides them.
     * ItemList objects are always sorted by efficiency (value/weight) in descending order.
     *
     * @return An ItemList containing all possible items to be loaded into trucks
     */
    public static ItemList getSortedItems() {
        // Initialize an ItemList for loadable items
        ItemList itemList = new ItemList();

        // Read input file
        List<String[]> csvLines = FileManager.readCsvFile("items.csv");

        // Column numbers of parameters
        int name = -1, units = -1, weight = -1, value = -1;

        // Iterate through all lines
        for (String[] line : csvLines) {
            if (name == -1) {
                // Read first line to get column numbers
                for (int i = 0; i < line.length; i++) {
                    switch (line[i]) {
                        case "name":
                            name = i;
                            break;
                        case "units":
                            units = i;
                            break;
                        case "weight":
                            weight = i;
                            break;
                        case "value":
                            value = i;
                    }
                }
            } else {
                // Read line into Item object and add it to the list
                itemList.addItem(new Item(line[name], Double.parseDouble(line[weight]), Double.parseDouble(line[value]), Integer.parseInt(line[units])));
            }
        }

        // Return the ItemList
        return itemList;
    }
}
