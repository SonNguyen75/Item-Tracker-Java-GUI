package ca.cmpt213.a4.webappserver.model;
import java.time.LocalDate;

public class ConsumableFactory {
    public Consumable getConsumable(String consumableType, String name, String notes,
                                    double price, double measurement, LocalDate expiryDate) {
        if (consumableType.equalsIgnoreCase("food")) {
            return new Food(consumableType, name, notes, price, expiryDate, measurement);
        }
        else if (consumableType.equalsIgnoreCase("drink")) {
            return new Drink(consumableType, name, notes, price,expiryDate, measurement);
        }
        return null;
    }
}
