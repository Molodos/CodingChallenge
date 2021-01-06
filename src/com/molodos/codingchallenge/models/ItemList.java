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

    public boolean addItem(Item item) {
        if (item.getTotalWeight() + totalWeight > maxCapacity) {
            return false;
        }
        totalWeight += item.getTotalWeight();
        totalValue += item.getTotalValue();
        boolean found = false;
        for (Item value : items) {
            if (value.equals(item)) {
                value.addUnits(item.getUnits());
                found = true;
            }
        }
        if (!found) {
            items.add(item);
        }
        Collections.sort(items);
        return true;
    }

    public boolean removeItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                if (items.get(i).getUnits() < item.getUnits()) {
                    return false;
                }
                items.get(i).removeUnits(item.getUnits());
                if (items.get(i).getUnits() <= 0) {
                    items.remove(i);
                }
                totalWeight -= item.getTotalWeight();
                totalValue -= item.getTotalValue();
                return true;
            }
        }
        return false;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getRemainingCapacity() {
        if (maxCapacity == Double.MAX_VALUE) {
            return maxCapacity;
        }
        return maxCapacity - totalWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<ItemTuple> getAllTuples(int size) {
        List<ItemTuple> tuples = new ArrayList<>();
        if (size == 0) {
            return tuples;
        }
        tuples.add(new ItemTuple());
        for (int i = 0; i < size; i++) {
            tuples = upgradeTuples(tuples);
        }
        return tuples;
    }

    private List<ItemTuple> upgradeTuples(List<ItemTuple> tuples) {
        for (int i = tuples.size() - 1; i >= 0; i--) {
            ItemTuple tuple = tuples.get(i);
            tuples.remove(i);
            for (Item item : items) {
                ItemTuple add = tuple.copy();
                if (add.getItemCount(item) >= item.getUnits()) {
                    continue;
                }
                add.addItem(item.copy(1));
                tuples.add(add);
                if (add.size() > 1 && item.equals(add.getFirstItem())) {
                    break;
                }
            }
        }
        return tuples;
    }

    /**
     * Returns a String representation of the item list.
     *
     * @return A String representation of the item list
     */
    @Override
    public String toString() {
        String representation = "";
        for (Item item : items) {
            if (item.getUnits() > 0) {
                if (!representation.equals("")) {
                    representation += "\n";
                }
                representation += "- " + item.toString();
            }
        }
        return representation;
    }
}
