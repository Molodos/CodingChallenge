package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;
import com.molodos.bwichallenge.models.ItemList;
import com.molodos.bwichallenge.models.ItemTuple;
import com.molodos.bwichallenge.models.Truck;

import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) {
        calculate(true);
    }

    private static void calculate(boolean details) {
        ItemList items = DataProvider.getSortedItems();
        Truck[] trucks = DataProvider.getTrucks();
        double totalValue = 0;
        for(Truck truck : trucks) {
            fillTruck(truck, items);
            optimizeTruck(truck, items, 5);
        }
        for(Truck truck : trucks) {
            totalValue += truck.getTotalValue();
            if(details) {
                System.out.println(truck.toString() + "\n");
            }
        }
        System.out.println("Total value (all trucks): " + totalValue);
    }

    private static void fillTruck(Truck truck, ItemList items) {
        for(Item item : items.getItems()) {
            Item split = item.splitUnits(1);
            while(split.getUnits() > 0 && truck.addItem(split)) {
                split = item.splitUnits(1);
            }
            item.addUnits(split.getUnits());
        }
    }

    private static void optimizeTruck(Truck truck, ItemList items, int maxTupleReplace) {
        checkLoop: while(true) {
            List<ItemTuple> truckTuples = new ArrayList<>();
            List<ItemTuple> replaceTuples = new ArrayList<>();
            for(int i = 1; i <= maxTupleReplace; i++) {
                truckTuples.addAll(truck.getAllTuples(i));
                replaceTuples.addAll(items.getAllTuples(i));
            }
            for(ItemTuple tuple : truckTuples) {
                for(ItemTuple replace : replaceTuples) {
                    if(replace.getTotalValue() > tuple.getTotalValue() && replace.getTotalWeight() <= tuple.getTotalWeight() + truck.getRemainingG()) {
                        for(Item item : tuple.getAllItems()) {
                            truck.removeItem(item);
                            items.addItem(item);
                        }
                        for(Item item : replace.getAllItems()) {
                            items.removeItem(item);
                            truck.addItem(item);
                        }
                        continue checkLoop;
                    }
                }
            }
            break;
        }
    }
}
