package com.molodos.bwichallenge.models;

import java.util.ArrayList;
import java.util.List;

public class ItemTuple {

    private final List<Item> items = new ArrayList<>();

    public ItemTuple copy() {
        ItemTuple tupleNew = new ItemTuple();
        tupleNew.items.addAll(items);
        return tupleNew;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getItemCount(Item searchItem) {
        int count = 0;
        for(Item item : items) {
            if(item.equals(searchItem)) {
                count++;
            }
        }
        return count;
    }

    public double getTotalValue() {
        double total = 0;
        for(Item item : items) {
            total += item.getTotalValue();
        }
        return total;
    }

    public double getTotalWeight() {
        double total = 0;
        for(Item item : items) {
            total += item.getTotalWeight();
        }
        return total;
    }
}
