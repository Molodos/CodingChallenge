package com.molodos.codingchallenge.models;

import java.text.DecimalFormat;

/**
 * This class models a truck to be filled with items.
 *
 * @author Michael Weichenrieder
 */
public class Truck extends ItemList {

    // Name of the truck
    private final String name;

    // Capacity and driver weight in grams
    private final double capacity, driverWeight;

    /**
     * Constructor to create a Truck object by its specification.
     *
     * @param name         Name of the truck
     * @param capacity     Capacity of the truck in grams
     * @param driverWeight Weight of the trucks driver in grams
     */
    public Truck(String name, double capacity, double driverWeight) {
        // Call the ItemLists constructor with a maximum capacity of the truck excluding the drivers weight
        super(capacity - driverWeight);

        // Save the parameters
        this.name = name;
        this.capacity = capacity;
        this.driverWeight = driverWeight;
    }

    /**
     * Returns the total truck capacity.
     *
     * @return total truck capacity
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Returns the name of the truck.
     *
     * @return Name of the truck
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a String representation of the truck.
     *
     * @return A String representation of the truck
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.#####");
        return String.format("%s:%n" +
                "%s%n" +
                "Gewicht inklusive Fahrer: %sg%n" +
                "Freie Kapazität:          %sg%n" +
                "Nutzwert:                 %s", name, super.toString(), decimalFormat.format(capacity - driverWeight), decimalFormat.format(getRemainingCapacity()), decimalFormat.format(getTotalValue()));
    }
}
