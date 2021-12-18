package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDate;

public class Food extends Consumable {
    private double weight;
    public Food(String type, String name, String notes, double price, LocalDate expiryDate, double info) {
        super(type, name, notes, price, expiryDate);
        this.weight = info;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
