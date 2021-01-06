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
        trucks.add(new Truck("Transporter 1", 1100000, 72400));
        trucks.add(new Truck("Transporter 2", 1100000, 85700));

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
        itemList.addItem(new Item("Notebook Büro 13\"", 2451, 40, 205));
        itemList.addItem(new Item("Notebook Büro 14\"", 2978, 35, 420));
        itemList.addItem(new Item("Notebook outdoor", 3625, 80, 450));
        itemList.addItem(new Item("Mobiltelefon Büro", 717, 30, 60));
        itemList.addItem(new Item("Mobiltelefon Outdoor", 988, 60, 157));
        itemList.addItem(new Item("Mobiltelefon Heavy Duty", 1220, 65, 220));
        itemList.addItem(new Item("Tablet Büro klein", 1405, 40, 620));
        itemList.addItem(new Item("Tablet Büro groß", 1455, 40, 250));
        itemList.addItem(new Item("Tablet outdoor klein", 1690, 45, 540));
        itemList.addItem(new Item("Tablet outdoor groß", 1980, 68, 370));

        // Return the ItemList
        return itemList;
    }
}
