package com.molodos.codingchallenge.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class models an item list consisting of several items.
 *
 * @author Michael Weichenrieder
 */
public class ItemList {

    // List containing all the items
    private final List<Item> items = new ArrayList<>();

    // Counter for total weight and value
    private double totalWeight = 0, totalValue = 0;

    // Maximum weight, the list can contain
    private final double maxCapacity;

    /**
     * Constructor to create an ItemList object with a maximum capacity.
     *
     * @param maxCapacity Maximum capacity, the ItemList can contain
     */
    public ItemList(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Constructor to create an ItemList object with unlimited capacity.
     */
    public ItemList() {
        maxCapacity = Double.MAX_VALUE;
    }

    /**
     * Adds an item to the item list if the maximum capacity allows it.
     *
     * @param item Item to be added to the item list
     * @return true if adding was successful, false if capacity would have ben exceeded
     */
    public boolean addItem(Item item) {
        // Cancel if adding item would exceed maximum capacity
        if (item.getTotalWeight() + totalWeight > maxCapacity) {
            return false;
        }

        // Increase total weight and value counters
        totalWeight += item.getTotalWeight();
        totalValue += item.getTotalValue();

        // Boolean to mark if units were added
        boolean found = false;

        // Iterate through all items and add units to existing item if item already exists
        for (Item value : items) {
            if (value.equals(item)) {
                // Add units
                value.addUnits(item.getUnits());

                // Set boolean to indicate that item was already added
                found = true;
            }
        }

        // If item wasn't already added, add it and sort list
        if (!found) {
            items.add(item);
            Collections.sort(items);
        }

        // Return true for success
        return true;
    }

    /**
     * Removes an item from the item list if enough units exist.
     *
     * @param item Item to be removed from the item list
     * @return true if removing was successful, false if not enough units of item type existed
     */
    public boolean removeItem(Item item) {
        // Iterate over all items
        for (Item value : items) {
            // Check if item equals item to be removed
            if (value.equals(item)) {
                // Return false for failure if there are not enough units available to remove
                if (value.getUnits() < item.getUnits()) {
                    return false;
                }

                // Remove item units
                value.removeUnits(item.getUnits());

                // Decrease total weight and value counters
                totalWeight -= item.getTotalWeight();
                totalValue -= item.getTotalValue();

                // Return true for success
                return true;
            }
        }

        // Return false for failure if item wasn't found
        return false;
    }

    /**
     * Returns the total value of all the contained items.
     *
     * @return Total value of all the contained items
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Returns the remaining capacity of the ItemList object.
     *
     * @return Remaining capacity or Double.MAX_VALUE for unlimited
     */
    public double getRemainingCapacity() {
        // Return Double.MAX_VALUE if capacity is unlimited
        if (maxCapacity == Double.MAX_VALUE) {
            return maxCapacity;
        }

        // Else calculate and return remaining capacity
        return maxCapacity - totalWeight;
    }

    /**
     * Retrieves a list of all conteined items.
     *
     * @return A list of all contained items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Returns the number of units of the specified type contained in the list.
     *
     * @param searchItem Item type searched
     * @return Number of contained units
     */
    public int getUnits(Item searchItem) {
        // Iterate through all items
        for(Item item : items) {
            // Return the unit count if item was found
            if(item.equals(searchItem)) {
                return item.getUnits();
            }
        }

        // Return 0 if not found
        return 0;
    }

    /**
     * Generate all possible tuples of items with a specified size.
     *
     * @param size Count of Item objects in the tuples
     * @return A list of all tuples possible with the items of the item list and the specified size
     */
    public List<ItemTuple> getAllTuples(int size) {
        // Initialize a list
        List<ItemTuple> tuples = new ArrayList<>();

        // Return the empty list if size is smaller than or equal to zero
        if (size <= 0) {
            return tuples;
        }

        // Add an empty tuple as a base
        tuples.add(new ItemTuple());

        // Upgrade the tuple list size times to contain the right tuples
        for (int i = 0; i < size; i++) {
            // Add new tuples and increase tuple size by one
            tuples = upgradeTuples(tuples);
        }

        // Return final tuple list
        return tuples;
    }

    /**
     * For each tuple in the list, duplicate it for every possible item to be added to finally have a list with all possible tuples having a one bigger size.
     *
     * @param tuples A list of tuples whose size should be increased by one
     * @return A list with all according tuples having a one bigger size than the initial tuples
     */
    private List<ItemTuple> upgradeTuples(List<ItemTuple> tuples) {
        // Iterate backwards through all tuples in the list to not destroy indexes no already visited by removing items
        for (int i = tuples.size() - 1; i >= 0; i--) {
            // Remove and save tuple
            ItemTuple tuple = tuples.remove(i);

            // Go though all items that could possibly be appended
            for (Item item : items) {
                // Create a copy of the saved tuple
                ItemTuple add = tuple.copy();

                // If already all units of the item to be added were added to the tuple, don't add another unit continue loop
                if (add.getItemCount(item) >= item.getUnits()) {
                    continue;
                }

                // Add a copy of the item to the tuple and add the new tuple to the tuple list
                add.addItem(item.copy(1));
                tuples.add(add);

                // Loop cancellation condition to not generate multiple unnecessary variations ot a tuple, where only the order is different
                if (add.size() > 1 && item.equals(add.getFirstItem())) {
                    break;
                }
            }
        }

        // Return the upgraded tuple list
        return tuples;
    }

    /**
     * Returns a String representation of the item list.
     *
     * @return A String representation of the item list
     */
    @Override
    public String toString() {
        // Get the maximum unit char count for formatting reasons
        int maxUnits = 0;
        for(Item item : items) {
            if(item.getUnits() > maxUnits) {
                maxUnits = item.getUnits();
            }
        }
        int maxChars = String.valueOf(maxUnits).length();

        // Initialize a StringBuilder
        StringBuilder representation = new StringBuilder();

        // Iterate through all items and add a line for each one if it has units
        for (Item item : items) {
            if (item.getUnits() > 0) {
                // Add a leading new line if it isn't the first line
                if (!representation.toString().equals("")) {
                    representation.append("\n");
                }

                // Format the line
                representation.append("- ");
                representation.append(item.toString().replace("x", "x" + " ".repeat(Math.max(0, maxChars - String.valueOf(item.getUnits()).length()))));
            }
        }

        // Return the String representation
        return representation.toString();
    }
}
