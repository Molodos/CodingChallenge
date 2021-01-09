package com.molodos.codingchallenge.gui;

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

    /**
     * Adds an exchange to the group.
     *
     * @param exchange The exchange to be added
     */
    public void addExchange(ItemExchange exchange) {
        exchanges.add(exchange);
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
