package com.molodos.codingchallenge.models;

public class Item implements Comparable<Item> {

    private final String name;
    private final double weightG, value;
    private int units;

    private final double efficiency;

    public Item(String name, int units, double weightG, double value) {
        this.name = name;
        this.units = units;
        this.weightG = weightG;
        this.value = value;
        efficiency = value / weightG;
    }

    public Item copy(int units) {
        return new Item(name, units, weightG, value);
    }

    public double getWeight() {
        return weightG;
    }

    public String getName() {
        return name;
    }

    public double getTotalWeight() {
        return weightG * units;
    }

    public double getTotalValue() {
        return value * units;
    }

    public void addUnits(int units) {
        this.units += units;
    }

    public void removeUnits(int units) {
        units = Math.min(units, this.units);
        this.units -= units;
    }

    public Item splitUnits(int units) {
        if(units > this.units) {
            units = this.units;
        }
        this.units -= units;
        return new Item(name, units, weightG, value);
    }

    public int getUnits() {
        return units;
    }

    @Override
    public int compareTo(Item o) {
        return Double.compare(o.efficiency, efficiency);
    }

    @Override
    public String toString() {
        return units + "x " + name + " [weight = " + weightG + "g, value = " + value + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item) {
            Item item = (Item) obj;
            return item.name.equalsIgnoreCase(name) && item.weightG == weightG && item.value == value;
        }
        return super.equals(obj);
    }
}
