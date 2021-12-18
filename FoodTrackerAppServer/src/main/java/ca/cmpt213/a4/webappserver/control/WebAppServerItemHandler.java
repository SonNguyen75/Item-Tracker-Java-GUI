package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class WebAppServerItemHandler {
    private List<Consumable> consumableList = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong();
    private ConsumableFactory consumableFactory = new ConsumableFactory();

    public List<Consumable> getConsumableList() {
        return consumableList;
    }

    public void setConsumableList(List<Consumable> consumableList) {
        this.consumableList = consumableList;
    }

    public List<Consumable> addFoodItem(Food newFood) {
        newFood.setID(nextId.getAndIncrement());
        if (!newFood.getName().isEmpty() && !newFood.getExpiryDate().toString().isEmpty()
                && newFood.getPrice() >= 0
                && newFood.getWeight()>= 0) {
            consumableList.add(newFood);
        }
        return consumableList;
    }

    public List<Consumable> addDrinkItem(Drink newDrink) {
        newDrink.setID(nextId.getAndIncrement());
        if (!newDrink.getName().isEmpty()
                && !newDrink.getExpiryDate().toString().isEmpty()
                && newDrink.getPrice() >= 0
                && newDrink.getVolume() >= 0)
            consumableList.add(newDrink);
        return consumableList;
    }

    public List<Consumable> removeItem(long itemToBeRemovedID) {
        Consumable itemToBeRemoved = null;
        for (Consumable currentItem : consumableList) {
            if (currentItem.getID() == itemToBeRemovedID) {
                itemToBeRemoved = currentItem;
            }
        }
        consumableList.remove(itemToBeRemoved);
        return consumableList;
    }

    public List<Consumable> listExpiredItems() {
        ArrayList<Consumable> expiredItem = new ArrayList<>();
        for (Consumable currentItem : consumableList) {
            if (currentItem.numOfDaysToExpired() < 0) {
                expiredItem.add(currentItem);
            }
        }
        Collections.sort(expiredItem);
        return expiredItem;
    }

    public List<Consumable> listNonExpiredItems () {
        ArrayList<Consumable> nonExpiredItem = new ArrayList<>();
        for (Consumable currentItem : consumableList) {
            if (currentItem.numOfDaysToExpired() >= 0) {
                nonExpiredItem.add(currentItem);
            }
        }
        Collections.sort(nonExpiredItem);
        return nonExpiredItem;
    }

    public List<Consumable> listExpiredIn7Days() {
        ArrayList<Consumable> expiredIn7DaysItems = new ArrayList<>();
        for (Consumable currentItem : consumableList) {
            if (currentItem.numOfDaysToExpired() >= 0 && currentItem.numOfDaysToExpired() <= 7) {
                expiredIn7DaysItems.add(currentItem);
            }
        }
        Collections.sort(expiredIn7DaysItems);
        return expiredIn7DaysItems;
    }

    public void saveItemsAsJSON () {
        //Override to enable writing LocalDateTime in JSON format
        Gson myGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,
                new TypeAdapter<LocalDate>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDate localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDate read(JsonReader jsonReader) throws IOException {
                        return LocalDate.parse(jsonReader.nextString());
                    }
                }).create();

        //Save foodStorage as a String in JSON format
        String foodStorageList = myGson.toJson(this.consumableList);

        //Saving that string into a file named foodStorage.json
        try (FileWriter file = new FileWriter("./itemList.json")) {
            file.write(foodStorageList);
            System.out.println("Thank you for using the program!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Consumable> readPreviousJSONSaveFile() {
        File input = new File("./itemList.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonArray foodJsonArray = fileElement.getAsJsonArray();
            this.consumableList = readJSONArray(foodJsonArray);

        } catch (FileNotFoundException e) {
            System.out.println("Previous save file does not exist");
        }
        return this.consumableList;
    }

    private ArrayList<Consumable> readJSONArray(JsonArray consumableListJSON) {
        ArrayList<Consumable> itemList = new ArrayList<>();
        for (JsonElement foodElement : consumableListJSON) {
            JsonObject currentItem = foodElement.getAsJsonObject();
            String name = currentItem.get("name").getAsString();
            String notes = currentItem.get("notes").getAsString();
            double price = currentItem.get("price").getAsDouble();
            String expiryDateString = currentItem.get("expiryDate").getAsString();
            String type = currentItem.get("type").getAsString();
            double measurement;
            if (type.equalsIgnoreCase("food")) {
                measurement = currentItem.get("weight").getAsDouble();
            } else {
                measurement = currentItem.get("volume").getAsDouble();
            }
            long ID = currentItem.get("ID").getAsLong();
            //Format LocalDate expiryDate = LocalDateTime.parse(expiryDateString);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expiryDate = LocalDate.parse(expiryDateString, dateFormat); //Formatting the date to the normal format
            //Create object based on type data
            Consumable newItem = consumableFactory.getConsumable(type, name, notes, price, measurement, expiryDate);
            newItem.setID(ID);
            //Add to ArrayList
            itemList.add(newItem);
        }
        return itemList;
    }
}
