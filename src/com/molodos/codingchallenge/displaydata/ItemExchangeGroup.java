package com.molodos.codingchallenge.displaydata;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models an exchange of multiple items between two item containing instances.
 *
 * @author Michael Weichenrieder
 */
public class ItemExchangeGroup {

    // List with all the exchanges the group contains
    private final ArrayList<ItemExchange> exchanges = new ArrayList<>();

    // True if exchange between trucks
    private final boolean betweenTrucks;

    /**
     * Create item exchange group by its parameters.
     *
     * @param betweenTrucks true if the exchange is between two trucks
     */
    public ItemExchangeGroup(boolean betweenTrucks) {
        this.betweenTrucks = betweenTrucks;
    }

    /**
     * Adds an exchange to the group.
     *
     * @param exchange The exchange to be added
     */
    public void addExchange(ItemExchange exchange) {
        exchanges.add(exchange);
    }

    /**
     * Check whether or not the exchange is between two trucks.
     *
     * @return True if it is between two trucks, else false
     */
    public boolean isTruckExchange() {
        return betweenTrucks;
    }

    /**
     * Returns a list of all the contained exchanges.
     *
     * @return A list of all the contained exvhanges
     */
    public List<ItemExchange> getExchanges() {
        return exchanges;
    }
}
