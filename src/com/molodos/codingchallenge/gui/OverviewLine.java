package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.Truck;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * This class models a line for overview tables.
 *
 * @author Michael Weichenrieder
 */
public class OverviewLine {

    // Line header
    private final String name;

    // Whether or not a 'g' should be added to the formatted display output
    private final boolean grams;

    // Values by truck columns
    private final HashMap<Truck, Double> values = new HashMap<>();

    /**
     * Construct a OverviewLine object by its specifications
     *
     * @param name  Line header
     * @param grams Use a trailing 'g' when formatting
     */
    public OverviewLine(String name, boolean grams) {
        this.name = name;
        this.grams = grams;
    }

    /**
     * Add a value for a truck column.
     *
     * @param truck Column
     * @param value Value
     */
    public void addValue(Truck truck, double value) {
        values.put(truck, value);
    }

    /**
     * Getter for line header.
     *
     * @return Line header
     */
    public String getName() {
        return name;
    }

    /**
     * Calculate the raw total value of the line.
     *
     * @return Raw total value of the line
     */
    private double getTotalValue() {
        // Iterate through columns and add together
        double total = 0;
        for (Truck truck : values.keySet()) {
            total += values.get(truck);
        }

        // Return
        return total;
    }

    /**
     * Retrieve the formatted line total value.
     *
     * @return Formatted line total value
     */
    public String getTotalDisplay() {
        // Decide whether or not to add a trailing 'g' and format
        if (grams) {
            return new DecimalFormat("0.#####g").format(getTotalValue());
        }
        return new DecimalFormat("0.#####").format(getTotalValue());
    }

    /**
     * Retrieve the trucks raw value.
     *
     * @param truck Truck to look up value for
     * @return Raw value of truck
     */
    private double getValue(Truck truck) {
        return values.getOrDefault(truck, 0.0);
    }

    /**
     * Retrieve the formatted truck value.
     *
     * @param truck Truck to look up value for
     * @return Formatted truck value
     */
    public String getDisplay(Truck truck) {
        // Decide whether or not to add a trailing 'g' and format
        if (grams) {
            return new DecimalFormat("0.#####g").format(getValue(truck));
        }
        return new DecimalFormat("0.#####").format(getValue(truck));
    }
}
