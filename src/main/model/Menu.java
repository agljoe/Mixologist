package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Codable;

public class Menu implements Codable {
    private List<Drink> menu;
    private List<Ingredient> ingredients;

    // EFFECTS: creates a new menu object with no drinks in it
    public Menu() {
        menu = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if drink is not already in menu adds drink 
    // creates an event for adding the drink and logs it
    public void addDrinkToMenu(Drink d) {
        if (!menu.contains(d)) {
            Event addToMenuEvent = new Event("Added " + d.getName() + " to menu");
            EventLog.getInstance().logEvent(addToMenuEvent);
            menu.add(d);
        }
    }


    // MODIFIES: this
    // EFFECTS: if drink is in menu removes drink 
    // creates an event for removing the drink and logs it
    public void removeDrinkFromMenu(Drink d) {
        if (menu.contains(d)) {
            Event removeDrinkEvent = new Event("Removed " + d.getName() + " from the menu");
            EventLog.getInstance().logEvent(removeDrinkEvent);
            menu.remove(d);
        }
    }

    // MODIFIES: this
    // EFFECTS: if ingredient is not already in ingredients adds ingredient 
    // creates an event for adding the ingredient and logs it
    public void addIngredient(Ingredient i) {
        if (!ingredients.contains(i)) {
            Event createIngredientEvent = new Event("Created a new ingredient: " + i.getName());
            EventLog.getInstance().logEvent(createIngredientEvent);
            ingredients.add(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: if drink is in menu removes drink and returns 
    // creates an event for removing the ingredient and logs it
    public void removeIngredient(Ingredient i) {
        if (ingredients.contains(i)) {
            Event removeIngredientEvent = new Event("Removed ingredient: " + i.getName());
            EventLog.getInstance().logEvent(removeIngredientEvent);
            ingredients.remove(i);
        }
    }

    // EFFECTS: returns all drinks available on a given menu in the order they were added,
    // if the are no drinks returns an empty list
    public List<Drink> getMenu() {
        return menu;
    }

       // EFFECTS: returns all ingredients available on a given menu in the order they were added,
    // if the are no ingredients returns an empty list
    public List<Ingredient> getIngredients() {   
        return ingredients;
    }


    @Override
    public JSONObject asJson() {
        JSONObject json = new JSONObject();

        JSONArray menuArray = new JSONArray();

        for (Drink d : menu) {
            menuArray.put(d.asJson());
        }

        json.put("menu", menuArray);
        return json;
    }
}
