package com.molodos.codingchallenge.models;

/**
 * This class models an item to be added to a truck.
 * Items objects are comparable to other Item objects in order to make sorting them by efficiency possible.
 *
 * @author Michael Weichenrieder
 */
public class Item implements Comparable<Item> {

    // Name of the item
    private final String name;

    // Weight and value of one unit of the item
    private final double weight, value;

    // Count of available units of this item
    private int units;

    // Efficiency of this item (value/weight)
    private final double efficiency;

    /**
     * Constructor to create an Item object by its specification.
     *
     * @param name   Name of the item
     * @param weight Weight of one unit of the item in grams
     * @param value  Value of one unit of the item
     * @param units  Available units of the items
     */
    public Item(String name, double weight, double value, int units) {
        // Save parameters
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.units = units;

        // Calculate and save efficiency
        efficiency = value / weight;
    }

    /**
     * Creates a copy of the item with a specified amount of units.
     *
     * @param units Amount of units of the item, the copy should have
     * @return A copy of the item with the specified amount of units
     */
    public Item copy(int units) {
        // Create and return a copy of the item
        return new Item(name, weight, value, units);
    }

    /**
     * Returns the weight of one unit of the item in grams.
     *
     * @return Weight of one unit of the item in grams
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the name of the item.
     *
     * @return Name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of units of this item available.
     *
     * @return Units of this item available
     */
    public int getUnits() {
        return units;
    }

    /**
     * Returns the total weight of all units of the item.
     *
     * @return Total weight of all units of the item
     */
    public double getTotalWeight() {
        return weight * units;
    }

    /**
     * Returns the total value of all units of the item.
     *
     * @return Total value of all units of the item
     */
    public double getTotalValue() {
        return value * units;
    }

    /**
     * Adds a specified amount of units to the item.
     *
     * @param units Units to add to the item
     */
    public void addUnits(int units) {
        this.units += units;
    }

    /**
     * Removes a specified amount of units from the item.
     * If fever units then the amount to be removed exist, all the available units will be removed.
     *
     * @param units Units to remove from the item
     */
    public void removeUnits(int units) {
        // Get minimum of available units and units to remove
        units = Math.min(units, this.units);

        // Remove units
        this.units -= units;
    }

    /**
     * Splits of an amount of units from the item.
     * If fever units then the amount to be splitted exist, all the available units will be splitted.
     *
     * @param units Units to split from the item
     * @return A copied Item object containing the splitted amount of units
     */
    public Item splitUnits(int units) {
        // Get minimum of available units and units to split
        units = Math.min(units, this.units);

        // Remove units
        this.units -= units;

        // Return splitted units as a separate Item object
        return new Item(name, weight, value, units);
    }

    /**
     * Implementation of the Comparable interface in order to make sorting by efficiency possible.
     * Efficiency values are compared.
     *
     * @param other Other item to compare item to
     * @return A number expressing the relative sorting position of the two items to each other.
     */
    @Override
    public int compareTo(Item other) {
        return Double.compare(other.efficiency, efficiency);
    }

    /**
     * If the supplied object is an Item object, checks whether or not the items are the same beside units.
     *
     * @param object Another object to compare Item object with
     * @return True if objects are equal, else false
     */
    @Override
    public boolean equals(Object object) {
        // If an Item object is supplied, compare name, weight and value
        if (object instanceof Item) {
            Item item = (Item) object;
            return item.name.equalsIgnoreCase(name) && item.weight == weight && item.value == value;
        }

        // Fallback if no Item object is supplied
        return super.equals(object);
    }

    /**
     * Returns a String representation of the item.
     *
     * @return A String representation of the item
     */
    @Override
    public String toString() {
        return String.format("%dx %s", units, name);
    }
}
