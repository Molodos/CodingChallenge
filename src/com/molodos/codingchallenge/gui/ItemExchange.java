package com.molodos.codingchallenge.gui;

import com.molodos.codingchallenge.models.Item;

import java.text.DecimalFormat;

/**
 * This class models an exchange of items between two item containing instances.
 *
 * @author Michael Weichenrieder
 */
public class ItemExchange {

    // Exchanged item
    private final Item item;

    // Source and destination of exchange
    private final String source, destination;

    /**
     * Constructs a new ItemExchange object by its specifications
     *
     * @param item        Exchanged item
     * @param source      Item source
     * @param destination Item destination
     */
    public ItemExchange(Item item, String source, String destination) {
        this.item = item;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Creates a spacer to add in the display table.
     *
     * @return Fake ItemExchange object as spacer
     */
    public static ItemExchange getSpacer() {
        return new ItemExchange(new Item(null, 0, 0, 0), null, null);
    }

    /**
     * Getter for exchanged item name.
     *
     * @return Exchanged item name or null if spacer
     */
    public String getItemName() {
        return item.getName();
    }

    /**
     * Getter for exchanged units.
     *
     * @return Exchanged units or null if spacer
     */
    public Integer getUnits() {
        return item.getUnits() == 0 ? null : item.getUnits();
    }

    /**
     * Getter for exchange source.
     *
     * @return Exchange source or null if spacer
     */
    public String getSource() {
        return source;
    }

    /**
     * Getter for exchange destination.
     *
     * @return Exchange destination or null if spacer
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter for formatted total weight of item.
     *
     * @return Formatted total weight of item or null if spacer
     */
    public String getTotalWeight() {
        if (item.getTotalWeight() == 0) {
            return null;
        }
        return new DecimalFormat("0.#####g").format(item.getTotalWeight());
    }

    /**
     * Getter for formatted total value of item.
     *
     * @return Formatted total value of item or null if spacer
     */
    public String getTotalValue() {
        if (item.getTotalValue() == 0) {
            return null;
        }
        return new DecimalFormat("0.#####").format(item.getTotalValue());
    }
}
