package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Consumable implements Comparable<Consumable>{
    protected String name;
    protected String notes;
    protected String type;
    protected double price;
    protected LocalDate expiryDate;
    //Unique identifier used to remove item
    protected long ID;

    public Consumable(String type, String name, String notes, double price, LocalDate expiryDate) {
        this.type = type;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long numOfDaysToExpired() {
        LocalDate today = LocalDate.now();
        return today.until(expiryDate, ChronoUnit.DAYS);
    }

    public int compareTo(Consumable o) {
        return (int)(this.numOfDaysToExpired() - o.numOfDaysToExpired());
    }

}