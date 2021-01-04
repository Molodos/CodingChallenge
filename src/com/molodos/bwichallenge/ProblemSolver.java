package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;
import com.molodos.bwichallenge.models.ItemList;
import com.molodos.bwichallenge.models.Truck;

public class ProblemSolver {

    public static void main(String[] args) {
        ItemList items = DataProvider.getSortedItems();
        Truck[] trucks = DataProvider.getTrucks();
        double totalValue = 0;
        for(Truck truck : trucks) {
            fillTruck(truck, items);
            totalValue += truck.getTotalValue();
            System.out.println(truck.toString() + "\n");
        }
        System.out.println("Total value (all trucks): " + totalValue);
    }

    private static void fillTruck(Truck truck, ItemList items) {
        // Fill by efficiency
        for(Item item : items.getItems()) {
            Item split = item.splitUnits(1);
            while(split.getUnits() > 0 && truck.addItem(split)) {
                split = item.splitUnits(1);
            }
            item.addUnits(split.getUnits());
        }

        // Optimize
        boolean changed = false;
        do {
            // TODO: Optimization
        } while(changed);
    }
}
