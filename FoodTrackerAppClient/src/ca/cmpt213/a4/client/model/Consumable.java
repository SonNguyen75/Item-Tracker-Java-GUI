package ca.cmpt213.a4.client.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Abstract class of Consumable, store basic fields of a Consumable object and abstract methods for food and drink to override
 */
public abstract class Consumable implements Comparable<Consumable> {
    protected String name;
    protected String notes;
    protected double price;
    protected LocalDate expiryDate;
    protected String type;
    //Unique identifier used to remove item
    protected long ID;

    public Consumable() {
        this.name = "";
        this.notes = "";
        this.price = 0;
        this.expiryDate = LocalDate.now();
    }
    public Consumable(String name, String notes, double price) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.type = "";
    }

    public String getType() {
        return type;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setType(String type) {
        this.type = type;
    }

    //Setter for measurement that will set either weight or volume depends on the type of Consumable
    public abstract void setMeasurement(double measurement);


    public abstract String toString();
    public abstract double getMeasurement();

    /**
     * Private helper function to help to calculate the remaining day until the expiry date of that item
     */
    public long numOfDaysToExpired() {
        LocalDate today = LocalDate.now();
        return today.until(expiryDate, ChronoUnit.DAYS);
    }

    /**
     * Implementing Comparable
     * @param o The Consumable objects we want to compare to
     * @return Number of days between each Consumable expiry date
     */
    public int compareTo(Consumable o) {
        return (int)(this.numOfDaysToExpired() - o.numOfDaysToExpired());
    }
}