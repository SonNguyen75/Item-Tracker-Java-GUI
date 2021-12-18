package ca.cmpt213.a4.client.model;

import java.time.LocalDate;

/**
 * Consumable Factory, will create object based on which type is it
 */
public class ConsumableFactory {
    public Consumable getConsumable(String consumableType, String name, String notes,
                                    double price, double measurement, LocalDate expiryDate) {
        if (consumableType.equalsIgnoreCase("food")) {
            return new Food(name, notes, price, measurement, expiryDate);
        }
        else if (consumableType.equalsIgnoreCase("drink")) {
            return new Drink(name, notes, price, measurement, expiryDate);
        }
        return null;
    }
}
