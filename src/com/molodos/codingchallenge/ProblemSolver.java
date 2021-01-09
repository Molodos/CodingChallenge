package com.molodos.codingchallenge;

import com.molodos.codingchallenge.gui.AlgorithmGUI;
import com.molodos.codingchallenge.gui.DisplayData;
import com.molodos.codingchallenge.gui.ItemExchange;
import com.molodos.codingchallenge.gui.ItemExchangeGroup;
import com.molodos.codingchallenge.models.Item;
import com.molodos.codingchallenge.models.ItemList;
import com.molodos.codingchallenge.models.ItemTuple;
import com.molodos.codingchallenge.models.Truck;
import javafx.application.Application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the programm.
 * This class controls the general execution flow.
 *
 * @author Michael Weichenrieder
 */
public class ProblemSolver {

    /**
     * Main method is executed on programm start.
     * Opens GUI, loads items and trucks and calculates an optimal loading list to load the trucks with a maximum total value.
     *
     * @param args Command line arguments (currently the program doesn't take any command line arguments)
     */
    public static void main(String[] args) {
        // Use DataProvider to load items and trucks
        System.out.print("Hardware und Transporter werden eingelesen...");
        ItemList items = DataProvider.getSortedItems();
        Truck[] trucks = DataProvider.getTrucks();
        System.out.println("fertig");

        // Initialize and start GUI
        DisplayData displayData = new DisplayData(items, trucks);
        AlgorithmGUI.startGUI(displayData);

        // Initially fill trucks with most efficient items
        System.out.print("Transporter werden befüllt...");
        fillTrucks(trucks, items);
        displayData.setAfterFirstLoad(trucks, items);
        System.out.println("fertig");

        // Exchange items between trucks and unloaded items to optimize the total value of all loaded items
        // Only search for exchangeable item tuples with a maximum of five items in order to save time whilst having no significant result quality loss
        System.out.print("Beladung wird optimiert...");
        optimizeTrucksLoad(trucks, items, 5, displayData);
        displayData.setAfterOptimization(trucks, items);
        System.out.println("fertig");

        // Save calculated optimal loading list for the trucks and some other stats
        System.out.print("Ergebnisse werden gespeichert...");
        FileManager.saveSolution(trucks, items, "solution.csv");
        System.out.println("fertig");

        // Print calculated optimal loading list for the trucks and some other stats
        System.out.println("Ergebnisse werden ausgegeben...");
        printStats(trucks, items);
    }


    /**
     * Fills Truck objects with items until there is no more space for any of the given items.
     * Items with with a higher efficiency value (value/weight) will be taken first as ItemList objects are sorted by efficiency in descending order.
     *
     * @param trucks Truck objects to be filled with items
     * @param items  Items to be filled into the trucks
     */
    private static void fillTrucks(Truck[] trucks, ItemList items) {
        // Iterate through all trucks and fill every truck on its own
        for (Truck truck : trucks) {
            // Iterate through all items not already loaded
            for (Item item : items.getItems()) {
                // Calculate max units of the item that fit into the truck and split them from the available items
                int maxUnits = (int) (truck.getRemainingCapacity() / item.getWeight());
                Item split = item.splitUnits(maxUnits);

                // Try loading split items to truck and put items back to ItemList if loading fails (normally should not happen)
                if (!truck.addItem(split)) {
                    item.addUnits(split.getUnits());
                }
            }
        }
    }

    /**
     * Optimizes load of trucks by alternating between maximizing free space and maximizing total item value.
     * If in an iteration of alternating both maximizing free space and total item value don't change anything, optimization will be finished.
     *
     * @param trucks       Truck objects whose load should be optimized
     * @param items        Items that are not loaded already to fill truck with if needed
     * @param maxTupleSize Maximum item count of tuples to exchange at once (lower values lead to faster calculation)
     * @param displayData  DisplayData object to log exchanges to
     */
    private static void optimizeTrucksLoad(Truck[] trucks, ItemList items, int maxTupleSize, DisplayData displayData) {
        // Local variables to show whether or not maximizations were done in the last iteration
        boolean freeSpaceDone = true;
        boolean valueDone = true;

        // Iterate between maximizing free space and total item value until no more changes can be done
        while (freeSpaceDone || valueDone) {
            // Reset variables
            freeSpaceDone = false;
            valueDone = false;

            // If there are minimum two trucks, set them up in a list and always try maximizing free space between two neighbour trucks
            if (trucks.length > 1) {
                for (int i = 0; i < trucks.length - 1; i++) {
                    // If maximization of neighbour trucks brings any changes, set boolean to true in order to allow another iteration
                    if (maximizeFreeSpace(trucks[i], trucks[i + 1], maxTupleSize, displayData)) {
                        freeSpaceDone = true;
                    }
                }
            }

            // For all trucks, try maximizing the total value by exchanging item tuples with item tuples from the unloaded items
            for (Truck truck : trucks) {
                // If maximization of total value brings any changes, set boolean to true in order to allow another iteration
                if (maximizeValue(truck, items, maxTupleSize, displayData)) {
                    valueDone = true;
                }
            }
        }

        // Make exchange log available for display in GUI
        displayData.lastExchangeDone();
    }

    /**
     * Maximizes free space in one truck by minimizing free space in the other truck.
     * Only exchanges item tuples between the trucks if the emptier truck afterwards will have more space left than the emptier truck before.
     *
     * @param truckA       First truck to use for exchanging items
     * @param truckB       Second truck to use for exchanging items
     * @param maxTupleSize Maximum item count of tuples to exchange at once (lower values lead to faster calculation)
     * @param displayData  DisplayData object to log exchanges to
     * @return true if truck load was changed, else false
     */
    private static boolean maximizeFreeSpace(Truck truckA, Truck truckB, int maxTupleSize, DisplayData displayData) {
        // Initialize exchange group
        ItemExchangeGroup exchangeGroup = new ItemExchangeGroup(true);

        // Local variable to store whether or not modifications were made to truck load
        boolean modificationsMade = false;

        // Repeat until no more modifications can be made
        maximizeFreeSpaceLoop:
        while (true) {
            // Calculate the maximum space left in the emptier truck
            double maxSpaceLeft = Math.max(truckA.getRemainingCapacity(), truckB.getRemainingCapacity());

            // Retrieve lists of all possible tuples not bigger than the maximum size for both trucks
            List<ItemTuple> truckATuples = new ArrayList<>();
            List<ItemTuple> truckBTuples = new ArrayList<>();
            for (int i = 1; i <= maxTupleSize; i++) {
                truckATuples.addAll(truckA.getAllTuples(i));
                truckBTuples.addAll(truckB.getAllTuples(i));
            }

            // Iterate through all possible combinations of tuples of the two trucks
            for (ItemTuple tupleA : truckATuples) {
                for (ItemTuple tupleB : truckBTuples) {
                    // Calculate the space that would be left in the trucks if tuples would be exchanged
                    double truckASpaceLeftChange = tupleA.getTotalWeight() - tupleB.getTotalWeight();
                    double aSpaceLeftAfterwards = truckA.getRemainingCapacity() + truckASpaceLeftChange;
                    double bSpaceLeftAfterwards = truckB.getRemainingCapacity() - truckASpaceLeftChange;

                    // If exchange of tuples is possible without exceeding truck weight limits and if one of the trucks will be emptier than the emptier truck in the beginning, exchange tuples
                    if (Math.min(aSpaceLeftAfterwards, bSpaceLeftAfterwards) >= 0 && Math.max(aSpaceLeftAfterwards, bSpaceLeftAfterwards) > maxSpaceLeft) {
                        // Remove all items contained in tuples from their trucks
                        for (Item item : tupleA.getAllItems()) {
                            truckA.removeItem(item);
                        }
                        for (Item item : tupleB.getAllItems()) {
                            truckB.removeItem(item);
                        }

                        // Add items from the tuples to the other truck
                        for (Item item : tupleA.getAllItems()) {
                            truckB.addItem(item);

                            // Save exchange to group
                            exchangeGroup.addExchange(new ItemExchange(item, truckA.getName(), truckB.getName()));
                        }
                        for (Item item : tupleB.getAllItems()) {
                            truckA.addItem(item);

                            // Save exchange to group
                            exchangeGroup.addExchange(new ItemExchange(item, truckB.getName(), truckA.getName()));
                        }

                        // Add spacer to exchange group
                        exchangeGroup.addExchange(ItemExchange.getSpacer());

                        // Set boolean to signal, that changes were made
                        modificationsMade = true;

                        // Rerun loop because changes were made and possible item tuples have to be recalculated
                        continue maximizeFreeSpaceLoop;
                    }
                }
            }
            // End loop if no more changes were possible
            break;
        }

        // Save exchanges if modifications were done
        if (modificationsMade) {
            displayData.addItemExchangeGroup(exchangeGroup);
        }

        // Return whether or not modifications were made to truck load
        return modificationsMade;
    }

    /**
     * Maximizes total value of a truck by exchanging item tuples with item tuples from the unloaded items.
     * Only exchanges item tuples if afterwards the total value of the truck will be greater than before.
     *
     * @param truck        Truck whose value should be maximized
     * @param items        Unloaded items to use for exchanging items
     * @param maxTupleSize Maximum item count of tuples to exchange at once (lower values lead to faster calculation)
     * @param displayData  DisplayData object to log exchanges to
     * @return true if truck load was changed, else false
     */
    private static boolean maximizeValue(Truck truck, ItemList items, int maxTupleSize, DisplayData displayData) {
        // Initialize exchange group
        ItemExchangeGroup exchangeGroup = new ItemExchangeGroup(false);

        // Local variable to store whether or not modifications were made to truck load
        boolean modificationsMade = false;

        // Repeat until no more modifications can be made
        maximizeValueLoop:
        while (true) {
            // Retrieve lists of all possible tuples not bigger than the maximum size for the truck and the unloaded items
            List<ItemTuple> truckTuples = new ArrayList<>();
            List<ItemTuple> unloadedTuples = new ArrayList<>();
            for (int i = 1; i <= maxTupleSize; i++) {
                truckTuples.addAll(truck.getAllTuples(i));
                unloadedTuples.addAll(items.getAllTuples(i));
            }

            // Iterate through all possible combinations of tuples of the truck and the unloaded items
            for (ItemTuple tuple : truckTuples) {
                for (ItemTuple replace : unloadedTuples) {
                    // If exchange of tuples leads to an increase of truck total value and the maximum weight of the truck will not be exceeded, exchange tuples
                    if (replace.getTotalValue() > tuple.getTotalValue() && replace.getTotalWeight() <= tuple.getTotalWeight() + truck.getRemainingCapacity()) {
                        // Remove exchange all items contained in the truck tuple to the unused items
                        for (Item item : tuple.getAllItems()) {
                            truck.removeItem(item);
                            items.addItem(item);

                            // Save exchange to group
                            exchangeGroup.addExchange(new ItemExchange(item, truck.getName(), "[Ausladen]"));
                        }

                        // Refill truck by exchanging replace tuple from unused items to truck load
                        for (Item item : replace.getAllItems()) {
                            items.removeItem(item);
                            truck.addItem(item);

                            // Save exchange to group
                            exchangeGroup.addExchange(new ItemExchange(item, "[Einladen]", truck.getName()));
                        }

                        // Add spacer to exchange group
                        exchangeGroup.addExchange(ItemExchange.getSpacer());

                        // Set boolean to signal, that changes were made
                        modificationsMade = true;

                        // Rerun loop because changes were made and possible item tuples have to be recalculated
                        continue maximizeValueLoop;
                    }
                }
            }
            // End loop if no more changes were possible
            break;
        }

        // Save exchanges if modifications were done
        if (modificationsMade) {
            displayData.addItemExchangeGroup(exchangeGroup);
        }

        // Return whether or not modifications were made to truck load
        return modificationsMade;
    }

    /**
     * Prints statistics of current truck load and unloaded items.
     *
     * @param trucks Trucks whose loaded items and according statistics should be printed
     * @param items  Unused items that should be printed
     */
    private static void printStats(Truck[] trucks, ItemList items) {
        // Print unloaded items
        System.out.println("\nNicht verladene Hardware:\n" + items.toString() + "\n");

        // Print details of all trucks and calculate their total value
        double totalValue = 0;
        for (Truck truck : trucks) {
            totalValue += truck.getTotalValue();
            System.out.println(truck.toString() + "\n");
        }

        // Print total value of all loaded items
        DecimalFormat decimalFormat = new DecimalFormat("0.#####");
        System.out.println("Summe aller Nutzwerte: " + decimalFormat.format(totalValue));
    }
}
