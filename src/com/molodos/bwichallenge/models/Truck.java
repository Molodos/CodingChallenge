package com.molodos.bwichallenge.models;

public class Truck {

    private final double capacityG, driverG;
    private double remainingG;

    public Truck(double capacityG, double driverG) {
        this.capacityG = capacityG;
        this.driverG = driverG;
        remainingG = capacityG - driverG;
    }

    public double getremainingG() {
        return remainingG;
    }
}
