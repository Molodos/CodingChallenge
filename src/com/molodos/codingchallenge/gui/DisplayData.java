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

    // Data after first load
    private ItemList afterFirstLoadList = null;
    private Truck[] afterFirstLoadTrucks = null;

    // Data after optimization
    private ItemList afterOptimizationList = null;
    private Truck[] afterOptimizationTrucks = null;

    /**
     * Construct a DisplayData objects by the program input data.
     *
     * @param items  Input items of the program
     * @param trucks Input trucks of the program
     */
    public DisplayData(ItemList items, Truck[] trucks) {
        // Save a copy of the item list
        initialList = items.copy();

        // Create a copy of the trucks array
        Truck[] copiedTrucks = new Truck[trucks.length];
        for (int i = 0; i < trucks.length; i++) {
            copiedTrucks[i] = trucks[i].copy();
        }

        // Save the trucks array copy
        initialTrucks = copiedTrucks;
    }

    /**
     * Setter for data after first load.
     *
     * @param trucks Trucks after first load
     * @param items  Items left after first load
     */
    public void setAfterFirstLoad(Truck[] trucks, ItemList items) {
        // Save a copy of the item list
        afterFirstLoadList = items.copy();

        // Create a copy of the trucks array
        Truck[] copiedTrucks = new Truck[trucks.length];
        for (int i = 0; i < trucks.length; i++) {
            copiedTrucks[i] = trucks[i].copy();
        }

        // Save the trucks array copy
        afterFirstLoadTrucks = copiedTrucks;
    }

    /**
     * Setter for data after optimization.
     *
     * @param trucks Trucks after optimization
     * @param items  Items left after optimization
     */
    public void setAfterOptimization(Truck[] trucks, ItemList items) {
        // Save a copy of the item list
        afterOptimizationList = items.copy();

        // Create a copy of the trucks array
        Truck[] copiedTrucks = new Truck[trucks.length];
        for (int i = 0; i < trucks.length; i++) {
            copiedTrucks[i] = trucks[i].copy();
        }

        // Save the trucks array copy
        afterOptimizationTrucks = copiedTrucks;
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

    /**
     * Getter for item list after first load.
     *
     * @return Item list after first load
     */
    public ItemList getAfterFirstLoadList() {
        return afterFirstLoadList;
    }

    /**
     * Getter for truck array after first load.
     *
     * @return Truck array after first load
     */
    public Truck[] getAfterFirstLoadTrucks() {
        return afterFirstLoadTrucks;
    }

    /**
     * Getter for item list after optimization.
     *
     * @return Item list after optimization
     */
    public ItemList getAfterOptimizationList() {
        return afterOptimizationList;
    }

    /**
     * Getter for truck array after optimization.
     *
     * @return Truck array after optimization
     */
    public Truck[] getAfterOptimizationTrucks() {
        return afterOptimizationTrucks;
    }
}
