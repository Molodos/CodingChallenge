package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.Truck;

import java.util.ArrayList;
import java.util.List;

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

    // Optimization exchanges
    private boolean allExchangesDone = false;
    private final List<ItemExchangeGroup> exchanges = new ArrayList<>();

    // Data after optimization
    private ItemList afterOptimizationList = null;
    private Truck[] afterOptimizationTrucks = null;

    // Runtime
    private Long runTime = null;

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
     * Adds an exchange group to the list.
     *
     * @param itemExchangeGroup Exchange group to be added
     */
    public void addItemExchangeGroup(ItemExchangeGroup itemExchangeGroup) {
        exchanges.add(itemExchangeGroup);
    }

    /**
     * Finishes exchanges to make them available for GUI visualisation.
     */
    public void lastExchangeDone() {
        allExchangesDone = true;
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
     * Sets run time to make it available for GUI visualisation.
     *
     * @param runTime Run time of algorithm in milliseconds
     */
    public void setRunTime(long runTime) {
        this.runTime = runTime;
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
     * Returns a list of all optimization exchanges if optimization is finished.
     *
     * @return A list of all optimization exchanges of null if optimization is not finished yet
     */
    public List<ItemExchangeGroup> getExchangeGroups() {
        return allExchangesDone ? exchanges : null;
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

    /**
     * Get formatted algorithm run time if already available.
     *
     * @return Formatted run time or null if not available yet
     */
    public String getRunTime() {
        // Return null if nur available yet
        if (runTime == null) {
            return null;
        }

        // Calculate days, hours, minutes, seconds and milliseconds
        int milliseconds = (int) (runTime % 1000);
        int seconds = (int) (runTime / 1000.0);
        int minutes = (int) (seconds / 60.0);
        seconds %= 60;
        int hours = (int) (minutes / 60.0);
        minutes %= 60;
        int days = (int) (hours / 24.0);
        hours %= 24;

        // Format
        StringBuilder formatted = new StringBuilder();
        if (days > 0) {
            formatted.append(days).append(days == 1 ? " Tag und " : " Tage und ").append(hours).append(hours == 1 ? " Stunde" : " Stunden");
        } else if (hours > 0) {
            formatted.append(hours).append(hours == 1 ? " Stunde und " : " Stunden und ").append(minutes).append(minutes == 1 ? " Minute" : " Minuten");
        } else if (minutes > 0) {
            formatted.append(minutes).append(minutes == 1 ? " Minute und " : " Minuten und ").append(seconds).append(seconds == 1 ? " Sekunde" : " Sekunden");
        } else if (seconds > 0) {
            formatted.append(seconds).append(seconds == 1 ? " Sekunde und " : " Sekunden und ").append(milliseconds).append(milliseconds == 1 ? " Millisekunde" : " Millisekunden");
        } else {
            formatted.append(milliseconds).append(milliseconds == 1 ? " Millisekunde" : " Millisekunden");
        }

        // Return
        return formatted.toString();
    }
}
