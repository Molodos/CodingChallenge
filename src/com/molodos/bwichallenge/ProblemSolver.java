package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;

public class ProblemSolver {

    public static void main(String[] args) {
        Item[] items = DataProvider.getSortedItems();
        for(Item item : items) {
            System.out.println(item.toString());
        }
    }
}
