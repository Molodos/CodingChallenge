package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;

/**
 * This class is for saving snapshots of the current program state to later display in th GUI.
 *
 * @author Michael Weichenrieder
 */
public class DisplayData {

    // Initial input data of the program
    private final ItemList initialList;
    private final Truck[] initialTrucks;

    /**
     * Construct a DisplayData objects by the program input data.
     *
     * @param items  Input items of the program
     * @param trucks Input trucks of the program
     */
    public DisplayData(ItemList items, Truck[] trucks) {
        // Save a copy of the item list
        initialList = items.copy();

        // Create a copy of teh trucks array
        Truck[] copiedTrucks = new Truck[trucks.length];
        for (int i = 0; i < trucks.length; i++) {
            copiedTrucks[i] = trucks[i].copy();
        }

        // Save the trucks array copy
        initialTrucks = copiedTrucks;
    }

    /**
     * Getter for initial item list.
     *
     * @return Initial item list
     */
    public ItemList getInitialList() {
        return initialList;
    }

    /**
     * Getter for initial truck array.
     *
     * @return Initial truck array
     */
    public Truck[] getInitialTrucks() {
        return initialTrucks;
    }
}
