package com.molodos.codingchallenge.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models an item tuple consisting of several items.
 *
 * @author Michael Weichenrieder
 */
public class ItemTuple {

    // A list of all items the tuple contains
    private final List<Item> items = new ArrayList<>();

    /**
     * Returns an exact copy of the item tuple.
     *
     * @return An exact copy of the item tuple
     */
    public ItemTuple copy() {
        // Create a new item tuple
        ItemTuple tupleNew = new ItemTuple();

        // Add all items of the current item tuple also to the newly created copy
        tupleNew.items.addAll(items);

        // Return the newly created copy
        return tupleNew;
    }

    /**
     * Returns a list of all the items, the item tuple contains.
     *
     * @return A list of all the items, the item tuple contains
     */
    public List<Item> getAllItems() {
        // Group items if several instances of the same type exist
        List<Item> groupedItems = new ArrayList<>();
        addLoop:
        for (Item item : items) {
            // Add units if already exists
            for (int i = 0; i < groupedItems.size(); i++) {
                if (groupedItems.get(i).equals(item)) {
                    groupedItems.get(i).addUnits(item.getUnits());
                    continue addLoop;
                }
            }

            // Else add a copy of the item to the list
            groupedItems.add(item.copy(item.getUnits()));
        }

        // Return grouped list
        return groupedItems;
    }

    /**
     * Adds an item to the item tuple.
     *
     * @param item Item to be added to the item tuple
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Checks how often the specified item is contained in the tuple.
     *
     * @param searchItem Item to search for in tuple
     * @return Number of contained items of the specified item
     */
    public int getItemCount(Item searchItem) {
        // Local count variable
        int count = 0;

        // Iterate through all contained items
        for (Item item : items) {
            // If the item is the same as the search item (except units), increase count by one
            if (item.equals(searchItem)) {
                count++;
            }
        }

        // Return number of counted items
        return count;
    }

    /**
     * Retrieves the first item that was added to the tuple.
     *
     * @return The first item that was added to the tuple or null if the tuple is empty
     */
    public Item getFirstItem() {
        if (items.size() > 0) {
            return items.get(0);
        } else {
            return null;
        }
    }

    /**
     * Returns size of the tuple.
     *
     * @return Size of the tuple
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns the total value of all the different items and units in the tuple.
     *
     * @return Total value of the tuple items
     */
    public double getTotalValue() {
        // Local variable to count total value
        double totalValue = 0;

        // Iterate through all contained items and add their value to the total value
        for (Item item : items) {
            totalValue += item.getTotalValue();
        }

        // Return the calculated total value
        return totalValue;
    }

    /**
     * Returns the total weight of all the different items and units in the tuple.
     *
     * @return Total weight of the tuple items
     */
    public double getTotalWeight() {
        // Local variable to count total weight
        double totalWeight = 0;

        // Iterate through all contained items and add their weight to the total weight
        for (Item item : items) {
            totalWeight += item.getTotalWeight();
        }

        // Return the calculated total weight
        return totalWeight;
    }

    /**
     * Returns a String representation of the tuple.
     *
     * @return A String representation of the tuple
     */
    @Override
    public String toString() {
        return items.toString();
    }
}
