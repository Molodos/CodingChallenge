package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;
import com.molodos.bwichallenge.models.ItemList;
import com.molodos.bwichallenge.models.ItemTuple;
import com.molodos.bwichallenge.models.Truck;

import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {
        System.out.print("Filling trucks...");
        ItemList items = DataProvider.getSortedItems();
        Truck[] trucks = DataProvider.getTrucks();
        for(Truck truck : trucks) {
            fillTruck(truck, items);
        }
        System.out.println("done");
        System.out.print("Optimizing load...");
        boolean rebalancingDone = true;
        boolean optimizationDone = true;
        while(rebalancingDone || optimizationDone) {
            rebalancingDone = false;
            if(trucks.length > 0) {
                for(int i = 0; i < trucks.length - 1; i++) {
                    if(balanceTrucks(trucks[i], trucks[i + 1], 5)) {
                        rebalancingDone = true;
                    }
                }
            }
            optimizationDone = false;
            for(Truck truck : trucks) {
                if(optimizeTruck(truck, items, 5)) {
                    optimizationDone = true;
                }
            }
        }
        System.out.println("done\n");
        printStats(trucks);
    }

    private static void printStats(Truck[] trucks) {
        double totalValue = 0;
        for(Truck truck : trucks) {
            totalValue += truck.getTotalValue();
            System.out.println(truck.toString() + "\n");
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

    private static boolean balanceTrucks(Truck truckA, Truck truckB, int maxTupleExchange) {
        boolean back = false;
        checkLoop: while(true) {
            double maxRemaining = Math.max(truckA.getRemainingG(), truckB.getRemainingG());
            List<ItemTuple> truckTuples = new ArrayList<>();
            List<ItemTuple> replaceTuples = new ArrayList<>();
            for(int i = 1; i <= maxTupleExchange; i++) {
                truckTuples.addAll(truckA.getAllTuples(i));
                replaceTuples.addAll(truckB.getAllTuples(i));
            }
            for(ItemTuple tuple : truckTuples) {
                for(ItemTuple replace : replaceTuples) {
                    double truckAChange = replace.getTotalWeight() - tuple.getTotalWeight();
                    double aLeftNew = truckA.getRemainingG() - truckAChange;
                    double bLeftNew = truckB.getRemainingG() + truckAChange;
                    if(Math.min(aLeftNew, bLeftNew) >= 0 && Math.max(aLeftNew, bLeftNew) > maxRemaining) {
                        for(Item item : tuple.getAllItems()) {
                            truckA.removeItem(item);
                        }
                        for(Item item : replace.getAllItems()) {
                            truckB.removeItem(item);
                        }
                        for(Item item : tuple.getAllItems()) {
                            truckB.addItem(item);
                        }
                        for(Item item : replace.getAllItems()) {
                            truckA.addItem(item);
                        }
                        back = true;
                        continue checkLoop;
                    }
                }
            }
            break;
        }
        return back;
    }

    private static boolean optimizeTruck(Truck truck, ItemList items, int maxTupleReplace) {
        boolean back = false;
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
                        back = true;
                        continue checkLoop;
                    }
                }
            }
            break;
        }
        return back;
    }
}
