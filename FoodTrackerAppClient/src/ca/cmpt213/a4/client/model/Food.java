package ca.cmpt213.a4.client.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class Food, store an extra field of volume and override abstract methods from Consumable
 */
public class Food extends Consumable{
    private double weight;

    public Food() {
        super();
        this.type = "Food";
        weight = 0;
    }

    public Food(String name, String notes, double price, double weight, LocalDate expiryDate) {
        super(name, notes, price);
        type = "Food";
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override
    public void setMeasurement(double weight) {
        this.weight = weight;
    }

    @Override
    public double getMeasurement() {
        return weight;
    }

    //Implement abstract toString method for Food items
    @Override
    public String toString() {
        long numOfDaysLeft;
        String output;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatExpiryDate = expiryDate.format(formatter);
        numOfDaysLeft = numOfDaysToExpired();
        System.out.println("This is a food item!");
        output = "Name: " + this.name + "\n" + "Notes: " + this.notes + "\n" + "Price: " + this.price + "\n" + "Weight: " + this.weight + "\n"
                + "Expiry date: " + formatExpiryDate + "\n";

        if (numOfDaysLeft == 0) { //If the expiry date is today
            output += "This item will expire today";
        }
        else if (numOfDaysLeft > 0) {
            output += "This food item is expired in " + numOfDaysLeft + " day(s)";
        }
        else { //If item is expired
            long numOfDayExpired = -numOfDaysLeft;
            output += "This food item is expired for " + numOfDayExpired + " day(s)";
        }
        return output;
    }



}
