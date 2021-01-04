package com.molodos.bwichallenge.models;

public class Item implements Comparable<Item> {

    private final String name;
    private final int units;
    private final double weightG, usability;

    private final double efficiency;

    public Item(String name, int units, double weightG, double usability) {
        this.name = name;
        this.units = units;
        this.weightG = weightG;
        this.usability = usability;
        efficiency = usability / weightG;
    }

    @Override
    public int compareTo(Item o) {
        return Double.compare(o.efficiency, efficiency);
    }

    @Override
    public String toString() {
        return units + "x " + name + ": Weight " + weightG + "g, Usability " + usability + ", Efficiency " + efficiency;
    }
}
