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

        // Add all trucks to the list
        trucks.add(new Truck("Truck 1", 1100000, 72400));
        trucks.add(new Truck("Truck 2", 1100000, 85700));

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

        // Add all possible items to the ItemList
        itemList.addItem(new Item("Notebook Büro 13\"", 205, 2451, 40));
        itemList.addItem(new Item("Notebook Büro 14\"", 420, 2978, 35));
        itemList.addItem(new Item("Notebook outdoor", 450, 3625, 80));
        itemList.addItem(new Item("Mobiltelefon Büro", 60, 717, 30));
        itemList.addItem(new Item("Mobiltelefon Outdoor", 157, 988, 60));
        itemList.addItem(new Item("Mobiltelefon Heavy Duty", 220, 1220, 65));
        itemList.addItem(new Item("Tablet Büro klein", 620, 1405, 40));
        itemList.addItem(new Item("Tablet Büro groß", 250, 1455, 40));
        itemList.addItem(new Item("Tablet outdoor klein", 540, 1690, 45));
        itemList.addItem(new Item("Tablet outdoor groß", 370, 1980, 68));

        // Return the ItemList
        return itemList;
    }
}
