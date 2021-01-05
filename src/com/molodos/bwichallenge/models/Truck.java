package com.molodos.bwichallenge.models;

import java.util.List;

public class Truck {

    private final String name;
    private final double capacityG, driverG;
    private final ItemList items;

    public Truck(String name, double capacityG, double driverG) {
        this.name = name;
        this.capacityG = capacityG;
        this.driverG = driverG;
        items = new ItemList(capacityG - driverG);
    }

    public boolean addItem(Item item) {
        return items.addItem(item);
    }

    public boolean removeItem(Item item) {
        return items.removeItem(item);
    }

    public double getRemainingG() {
        return items.getRemainingWeight();
    }

    public double getTotalValue() {
        return items.getTotalValue();
    }

    public List<ItemTuple> getAllTuples(int size) {
        return items.getAllTuples(size);
    }

    @Override
    public String toString() {
        String representation = name + ":\n";
        representation += items.toString() + "\n";
        representation += "Weight left: " + items.getRemainingWeight() + "g\n";
        representation += "Total value: " + items.getTotalValue();
        return representation;
    }
}
