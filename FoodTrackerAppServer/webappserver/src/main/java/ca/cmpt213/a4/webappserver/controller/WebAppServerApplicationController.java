package ca.cmpt213.a4.webappserver.controller;


import ca.cmpt213.a4.webappserver.control.WebAppServerItemHandler;
import ca.cmpt213.a4.webappserver.model.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebAppServerApplicationController {
    WebAppServerItemHandler itemHandler = new WebAppServerItemHandler();

    @GetMapping("/ping")
    public String pingUser() {
        return "System is up";
    }

    @GetMapping("/getPreviousSaveFile")
    public List<Consumable> loadSaveFile() {
        return itemHandler.readPreviousJSONSaveFile();
    }

    @GetMapping("/saveToJSONFile")
    public void saveFile() {
        itemHandler.saveItemsAsJSON();
    }

    @GetMapping("/listAll")
    public List<Consumable> listAllItem() {
         return itemHandler.getConsumableList();
    }

    @PostMapping("/addItem/food")
    public List<Consumable> createNewFoodItem(@RequestBody Food newFood) {
        return itemHandler.addFoodItem(newFood);
    }

    @PostMapping("/addItem/drink")
    public List<Consumable> createNewDrinkItem(@RequestBody Drink newDrink) {
        return itemHandler.addDrinkItem(newDrink);
    }

    @PostMapping("/removeItem")
    public List<Consumable> removeItem (@RequestBody ItemID itemToBeRemovedID) {
        return itemHandler.removeItem(itemToBeRemovedID.getItemID());
    }

    @GetMapping("/listExpired")
    public List<Consumable> listExpiredItems() {
        return itemHandler.listExpiredItems();
    }

    @GetMapping("/listNonExpired")
    public List<Consumable> listNonExpiredItems() {
        return itemHandler.listNonExpiredItems();
    }

    @GetMapping("/listExpiringIn7Days")
    public List<Consumable> listExpiredIn7Days() {
        return itemHandler.listExpiredIn7Days();
    }

    @GetMapping("/exit")
    public void exit() {
        itemHandler.saveItemsAsJSON();
    }
}
