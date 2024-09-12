package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.json.JSONArray;

import model.*;

// Represents a decoder object that converts JSON to java objects
public class Decoder {
    
    private String source;

    // EFFECTS: creates a new decoder object for the file at source
    public Decoder(String source) {
        this.source = source;
    }

    // EFFECTS: returns a decoded Drink object
    // throws an IOException if an error occurs
    public Drink decodeDrink() throws IOException {
        String data = read(source);
        JSONObject jsonData = new JSONObject(data);
        return parseDrink(jsonData);
    }

    // EFFECTS: returns a decoded Ingredient object
    // throws an IOException if an error occurs
    public Ingredient decodeIngredient() throws IOException {
        String data = read(source);
        JSONObject jsonData = new JSONObject(data);
        return parseIngredient(jsonData);
    }

    // EFFECTS: returns a decoded Menu object
    // throws an IOException if an error occurs
    public Menu decodeMenu() throws IOException {
        String data = read(source);
        JSONObject jsonData = new JSONObject(data);
        return parseMenu(jsonData);
    }

    // EFFECTS: returns a decoded list of drinks
    // throws an IOException if an error occurs
    public List<Drink> decodeDrinks(String key) throws IOException {
        String data = read(source);
        JSONObject jsonData = new JSONObject(data);
        return parseListOfDrink(jsonData, key);
    }

    // EFFECTS: returns a decoded list of ingredients
    // throws an IOException if an error occurs
    public List<Ingredient> decodeIngredients(String key) throws IOException {
        String data = read(source);
        JSONObject jsonData = new JSONObject(data);
        return parseListOfIngredient(jsonData, key);
    }

    // EFFECTS: reads source file as string and returns it
    // FROM: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
    private String read(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: returns parsed json data as a drink object
    private Drink parseDrink(JSONObject data) {
        String name = data.getString("name");
        String size = data.getString("size");
        String iceAmount = data.getString("iceAmount");
        
        boolean isMixed = data.getBoolean("isMixed");

        Drink d = new Drink(name, size);
        d.setSize(size);
        d.addIce(iceAmount);
        addIngredients(d, data);

        if (isMixed) {
            List<String> recipe = parseRecipe(data);

            if (recipe.contains("Stir")) {
                d.stir();
            } else {
                d.shake();
            }
        }

        return d;
    }

    // MODIFIES: d
    // EFFECTS: parses the ingredients array from json and adds them to d, 
    // and builds the recipe (except for mixing) for d due to the local implementation of d.addIngredient()
    private void addIngredients(Drink d, JSONObject data) {
        JSONArray ingredientsArray = data.getJSONArray("ingredients");

        for (Object ingredient : ingredientsArray) {
            JSONObject jsonIngredient = (JSONObject) ingredient;
            
            Ingredient i = parseIngredient(jsonIngredient);

            d.addIngredient(i);
        }
    }

    /// EFFECTS: returns parsed json data as an Recipe (list of drink) object
    private List<String> parseRecipe(JSONObject data) {
        JSONArray jsonRecipe = data.getJSONArray("recipe");
        List<String> recipe = new ArrayList<String>();

        for (Object direction : jsonRecipe) {
            String jsonDirection =  direction.toString();
            recipe.add(jsonDirection);
        }
        
        return recipe;
    }

    // EFFECTS: returns parsed json data as an Ingredient object
    private Ingredient parseIngredient(JSONObject data) {
        String name = data.getString("name");
        int sweetness = data.getInt("sweetness");
        int strength = data.getInt("strength");
        int thickness = data.getInt("thickness");

        return new Ingredient(name, sweetness, strength, thickness);
    }

    // EFFECTS: returns parsed json data as a Menu object
    private Menu parseMenu(JSONObject data) {
        JSONArray menuArray = data.getJSONArray("menu");
        Menu menu = new Menu();

        for (Object drink : menuArray) {
            JSONObject jsonDrink = (JSONObject) drink;

            Drink d = parseDrink(jsonDrink);
            menu.addDrinkToMenu(d);
        }

        return menu;
    }

    // EFFECTS: returns parsed json data as a list of drinks
    private List<Drink> parseListOfDrink(JSONObject data, String key) {
        JSONArray drinkArray = data.getJSONArray(key);
        List<Drink> drinks = new ArrayList<Drink>();

        for (Object drink : drinkArray) {
            JSONObject jsonDrink = (JSONObject) drink;

            Drink d = parseDrink(jsonDrink);
            drinks.add(d);
        }

        return drinks;
    }

    // EFFECTS: returns parsed json data as a list of ingredient
    private List<Ingredient> parseListOfIngredient(JSONObject data, String key) {
        JSONArray ingredientArray = data.getJSONArray(key);
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        for (Object ingredient : ingredientArray) {
            JSONObject jsonIngredient = (JSONObject) ingredient;

            Ingredient i = parseIngredient(jsonIngredient);
            ingredients.add(i);
        }

        return ingredients;
    }
}   
