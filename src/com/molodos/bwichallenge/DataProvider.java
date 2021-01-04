package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;
import com.molodos.bwichallenge.models.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

    public static Item[] getSortedItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Notebook Büro 13\"", 205, 2451, 40));
        items.add(new Item("Notebook Büro 14\"", 420, 2978, 35));
        items.add(new Item("Notebook outdoor", 450, 3625, 80));
        items.add(new Item("Mobiltelefon Büro", 60, 717, 30));
        items.add(new Item("Mobiltelefon Outdoor", 157, 988, 60));
        items.add(new Item("Mobiltelefon Heavy Duty", 220, 1220, 65));
        items.add(new Item("Tablet Büro klein", 620, 1405, 40));
        items.add(new Item("Tablet Büro groß", 250, 1455, 40));
        items.add(new Item("Tablet outdoor klein", 540, 1690, 45));
        items.add(new Item("Tablet outdoor groß", 370, 1980, 68));
        Collections.sort(items);
        return items.toArray(new Item[0]);
    }

    public static Truck[] getTrucks() {
        List<Truck> items = new ArrayList<>();
        items.add(new Truck(1100000, 72400));
        items.add(new Truck(1100000, 85700));
        return items.toArray(new Truck[0]);
    }
}
