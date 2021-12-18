package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDate;

public class Drink extends Consumable{
    private double volume;

    public Drink(String type, String name, String notes, double price, LocalDate expiryDate, double info) {
        super(type, name, notes, price, expiryDate);
        this.volume = info;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
