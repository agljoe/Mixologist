package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Codable;

// Represents a drink that has a name, ingredients, and a recipe
public class Drink extends Drinkable implements Codable {

    private String name;
    private String size; 
    private String iceAmount;
    private boolean isMixed;
    private List<Ingredient> ingredients;
    private List<String> recipe;
   
    /* REQUIRES: name is a non-empty string, size is one of "Small", "Medium" or "Large", 
    *  and iceAmount is one of "None", "More", or "Less"
    *  EFFECTS: creates a new drink object with name, size, no ice, and no ingredients, and
    *  sweetness, strength, and thickness of 0
    */
    public Drink(String name, String size) {
        this.name = name;
        this.size = size;
        iceAmount = "None";
        sweetness = 0;
        strength = 0;
        thickness = 0;
        isMixed = false;
        ingredients = new ArrayList<Ingredient>();
        recipe = new ArrayList<String>();
    }

    // REQUIRES: amount is either "None", "More" or "Less"
    // MODIFIES: this
    // EFFECTS: sets ice amount to amount, adds add amount of ice direction to recipe
    public void addIce(String amount) {
        if (amount.equals("None")) {
            recipe.add("Add no ice");
        } else {
            recipe.add("Add " + amount.toLowerCase() + " ice");
        }
        iceAmount = amount;

        amount = amount.equals("None") ? "no" : amount.toLowerCase();
        Event addIceEvent = new Event("Added " + amount + " ice to " + name);
        EventLog.getInstance().logEvent(addIceEvent);
    }


    // REQUIRES: drink is not yet mixed
    // MODIFIES: this
    // EFFECTS:  mixes the ingredients, without changing the thickness, adds the stir direction to recipe
    public void stir() {
        isMixed = true;
        recipe.add("Stir");
        Event stirEvent = new Event("Stirred " + name);
        EventLog.getInstance().logEvent(stirEvent);
    }

    // REQUIRES: drink is not yet mixed
    // MODIFIES: this
    // EFFECTS: mixes the ingredients, without decreases thickness by 1, added the shake direction to recipe
    public void shake() {
        isMixed = true;
        recipe.add("Shake");
        thickness--;
        Event shakeEvent = new Event("Shook " + name);
        EventLog.getInstance().logEvent(shakeEvent);
    }

    // EFFECTS: returns the name of a given drink
    public String getName() {
        return name; // stub
    }

    // EFFECTS: returns the size of a given drink
    public String getSize() {
        return size;
    }

    // EFFECTS: returns the amount of ice in a given drink
    public String getIceAmount() {
        return iceAmount;
    }

    // EFFECTS: returns wether or not a drink has been mixed
    public boolean isDrinkMixed() {
        return isMixed;
    }

    // EFFECTS: returns a list of all ingredients in a given drink in the order they were added
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    // EFFECTS: changes the name of a given drink
    public void setName(String name) {
        this.name = name;
    }  


    // REQUIRES: size is one of "Small", "Meduim", or "Large"
    // MODIFIES: this
    // EFFECTS: changes the size of a drink, adds direction to choose size to recipe
    public void setSize(String size) {
        recipe.add("Choose " + size.toLowerCase());
        this.size = size;
        Event sizeEvent = new Event("Created a new " + size.toLowerCase() + " drink: " + name);
        EventLog.getInstance().logEvent(sizeEvent);
    }  

    // MODIFIES: this
    // EFFECTS: adds an ingredient to the list of ingredients in a drink,
    // and add the traits of the ingredient to the drinks traits
    // adds direction to add ingredient to recipe
    // creates an event for creating a new ingredient and logs it
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
        sweetness += i.getSweetness();
        strength += i.getStrength();
        thickness += i.getThickness();
        recipe.add("Add " + i.getName().toLowerCase());
        Event addIngredientEvent = new Event("Added " + i.getName() + " to " + name);
        EventLog.getInstance().logEvent(addIngredientEvent);
    }

     // EFFECTS: returns a list of directions used to make a drink in the order they were executed
    public List<String> getRecipe() {
        return recipe;
    }

    // MODIFIES: this
    // EFFECTS: keeps the name of the current drink, but resets size to small, ice to none,
    // sets sweetness, strength, and thickness to 0, sets isMixed to false,
    // and sets ingredents and recipe to new empty lists
    public void resetDrink() {
        this.size = "Small";
        iceAmount = "None";
        sweetness = 0;
        strength = 0;
        thickness = 0;
        isMixed = false;
        ingredients = new ArrayList<>();
        recipe = new ArrayList<>();
    }


    @Override
    public JSONObject asJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("size", size);
        json.put("iceAmount", iceAmount);
        json.put("isMixed", isMixed);
        json.put("sweetness", sweetness);
        json.put("strength", strength);
        json.put("thickness", thickness);
        json.put("ingredients", ingredientArray());
        json.put("recipe", recipeArray());
        return json;
    }

    // EFFECTS: returns all ingredients in a drink as a JSONArray
    private JSONArray ingredientArray() {
        JSONArray ingredientsArray = new JSONArray();

        for (Ingredient i : ingredients) {
            ingredientsArray.put(i.asJson());
        }

        return ingredientsArray;
    }

    // EFFECTS: returns all ingredients in a drink as a JSONArray
    private JSONArray recipeArray() {
        JSONArray instructions = new JSONArray();

        for (String s : recipe) {
            instructions.put(s);
        }

        return instructions;
    }
}
