package ca.cmpt213.a4.client.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Class Drink, store an extra field of volume and override abstract methods from Consumable
 */
public class Drink extends Consumable{
    private double volume;

    public Drink() {
        super();
        this.type = "Drink";
        volume = 0;
    }


    public Drink(String name, String notes, double price, double volume, LocalDate expiryDate) {
        super(name, notes, price);
        this.volume = volume;
        this.expiryDate = expiryDate;
        type = "Drink";
    }

    @Override
    public void setMeasurement(double volume) {
        this.volume = volume;
    }

    @Override
    public double getMeasurement() {
        return volume;
    }

    @Override
    public String toString() {
        long numOfDaysLeft;
        String output;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatExpiryDate = expiryDate.format(formatter);
        numOfDaysLeft = numOfDaysToExpired();
        System.out.println("This is a drink item!");
        output = "Name: " + this.name + "\n" + "Notes: " + this.notes + "\n" + "Price: " + this.price + "\n" + "Volume: " + this.volume + "\n"
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
    @Override
    public int compareTo(Consumable o) {
        return (int)(this.numOfDaysToExpired() - o.numOfDaysToExpired());
    }
}
