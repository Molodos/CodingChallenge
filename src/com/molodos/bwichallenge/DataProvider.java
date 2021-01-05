package com.molodos.bwichallenge;

import com.molodos.bwichallenge.models.Item;
import com.molodos.bwichallenge.models.ItemList;
import com.molodos.bwichallenge.models.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

    public static ItemList getSortedItems() {
        ItemList itemList = new ItemList();
        itemList.addItem(new Item("Notebook Büro 13\"", 205, 2451, 40));
        itemList.addItem(new Item("Notebook Büro 14\"", 420, 2978, 35));
        itemList.addItem(new Item("Notebook outdoor", 450, 3625, 80));
        itemList.addItem(new Item("Mobiltelefon Büro", 60, 717, 30));
        itemList.addItem(new Item("Mobiltelefon Outdoor", 157, 988, 60));
        itemList.addItem(new Item("Mobiltelefon Heavy Duty", 220, 1220, 65));
        itemList.addItem(new Item("Tablet Büro klein", 620, 1405, 40));
        itemList.addItem(new Item("Tablet Büro groß", 250, 1455, 40));
        itemList.addItem(new Item("Tablet outdoor klein", 540, 1690, 45));
        itemList.addItem(new Item("Tablet outdoor groß", 370, 1980, 68));
        return itemList;
    }

    public static Truck[] getTrucks() {
        List<Truck> items = new ArrayList<>();
        items.add(new Truck("Truck 1", 1100000, 72400));
        items.add(new Truck("Truck 2", 1100000, 85700));
        return items.toArray(new Truck[0]);
    }
}
