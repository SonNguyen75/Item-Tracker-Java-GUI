package ca.cmpt213.a4.client.control;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.ConsumableFactory;
import ca.cmpt213.a4.client.model.ItemID;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * A class with the responsibility to create items and pass it to the server in JSON format
 */
public class ClientAppItemHandler {
    private static final ConsumableFactory consumableFactory = new ConsumableFactory();
    public ClientAppItemHandler() {
    }

    //Override GSON to be able to write Object with LocalDate in JSON
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

    /**
     * A method that would trigger the server to load previous JSON Save file
     * @return  the ArrayList with previous Items in it
     */
    public ArrayList<Consumable> readPreviousJSONSaveFile() {
        ArrayList<Consumable> previousItems = new ArrayList<>();
        try {
            URL localHost = new URL("http://localhost:8080/getPreviousSaveFile");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rawJSON = in.readLine();
            JsonElement newArray = JsonParser.parseString(rawJSON);
            JsonArray consumableListJSON = newArray.getAsJsonArray();
            previousItems = readJSONArray(consumableListJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previousItems;
    }

    /**
     * A method that would trigger the server to save all currently stored item in a JSON file
     */
    public void saveItemListToJSONFile() {
        try {
            URL localHost = new URL("http://localhost:8080/saveToJSONFile");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return An ArrayList with all the item currently stored
     */
    public ArrayList<Consumable> listAll () {
        ArrayList<Consumable> allItem = new ArrayList<>();
        try {
            URL localHost = new URL("http://localhost:8080/listAll");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rawJSON = in.readLine();
            JsonElement newArray = JsonParser.parseString(rawJSON);
            JsonArray consumableListJSON = newArray.getAsJsonArray();
            allItem = readJSONArray(consumableListJSON);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return allItem;
    }

    /***
     *
     * @return An array list with all non-expired items in it
     */
    public ArrayList<Consumable> listNonExpired() {
        ArrayList<Consumable> nonExpiredItems = new ArrayList<>();
        try {
            URL localHost = new URL("http://localhost:8080/listNonExpired");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rawJSON = in.readLine();
            JsonElement newArray = JsonParser.parseString(rawJSON);
            JsonArray consumableListJSON = newArray.getAsJsonArray();
            nonExpiredItems = readJSONArray(consumableListJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonExpiredItems;
    }

    /**
     *
     * @return An ArrayList with all expired items in it
     */
    public ArrayList<Consumable> listExpired() {
        ArrayList<Consumable> expiredItem = new ArrayList<>();
        try {
            URL localHost = new URL("http://localhost:8080/listExpired");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rawJSON = in.readLine();
            JsonElement newArray = JsonParser.parseString(rawJSON);
            JsonArray consumableListJSON = newArray.getAsJsonArray();
            expiredItem = readJSONArray(consumableListJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expiredItem;
    }

    /**
     *
     * @return An ArrayList with all items that would be expired in 7 days
     */
    public ArrayList<Consumable> listExpiredIn7Days () {
        ArrayList<Consumable> expiredIn7Days = new ArrayList<>();
        try {
            URL localHost = new URL("http://localhost:8080/listExpiringIn7Days");
            URLConnection connection = localHost.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rawJSON = in.readLine();
            JsonElement newArray = JsonParser.parseString(rawJSON);
            JsonArray consumableListJSON = newArray.getAsJsonArray();
            expiredIn7Days = readJSONArray(consumableListJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expiredIn7Days;
    }

    /**
     * A method that would trigger the server to add the parameter item to the list
     * @param newItem New Consumable item we want to add
     */
    public void addConsumableItem(Consumable newItem) {
        String foodStorageList = myGson.toJson(newItem);
        try {
            URL url;
            if (newItem.getType().equalsIgnoreCase("food")) {
                url = new URL("http://localhost:8080/addItem/food");
            }
            else {
                url = new URL("http://localhost:8080/addItem/drink");
            }
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = foodStorageList.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that would trigger the server to remove an item based on its ID
     * @param ID ID of the item we want to remove
     */
    public void removeItem(long ID) {
        ItemID itemToBeRemovedID = new ItemID(ID);
        String itemToBeRemovedIDJSon = myGson.toJson(itemToBeRemovedID);
        try {
            URL url = new URL ("http://localhost:8080/removeItem");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = itemToBeRemovedIDJSon.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to read a JSON Array to an ArrayList of Consumable objects
     * @param consumableListJSON Consumable List as JSON Array
     * @return An ArrayList with all the item from JSONArray in it
     */
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

            //Format LocalDate expiryDate = LocalDateTime.parse(expiryDateString);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expiryDate = LocalDate.parse(expiryDateString, dateFormat); //Formatting the date to the normal format
            //Create object based on type data
            Consumable newItem = consumableFactory.getConsumable(type, name, notes, price, measurement, expiryDate);
            //Add to ArrayList
            itemList.add(newItem);
        }
        return itemList;
    }

}
