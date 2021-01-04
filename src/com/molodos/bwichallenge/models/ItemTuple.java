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

    public List<Item> getAllItems() {
        return items;
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

    public Item getFirstItem() {
        if(items.size() > 0) {
            return items.get(0);
        } else {
            return null;
        }
    }

    public int size() {
        return items.size();
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

    @Override
    public String toString() {
        String str = "[";
        for(Item item : items) {
            if(!str.equals("[")) {
                str += ", ";
            }
            str += item.getName();
        }
        str += "]";
        return str;
    }
}
