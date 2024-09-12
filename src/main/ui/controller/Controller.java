package ui.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.*;
import persistence.*;

// Represents the controller for the ContentView class
public class Controller {

    private static final String MENU_PATH = "./data/menu.json";
    private static final String RECIPES_PATH = "./data/recipes.json";
    private static final String INGREDIENTS_PATH = "./data/ingredients.json";
    

    private static final String RECIPES_KEY = "recipes";
    private static final String INGREDIENTS_KEY = "ingredients";

    private Recipes recipes;
    private Menu menu;
    private Drink currentDrink;

    // EFFECTS: creates a new ui controller
    public Controller() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: creates a new list of ingredients = generateDefaultIngredients()
    // menu as a new menu, and recipes as empty list of drink 
    private void init() {
        menu = new Menu();
        recipes = new Recipes();
        generateDefaultIngredients();
    }

    // MODIFIES: this
    // EFFECTS: updates the current drink to be mixed
    public void createDrink(String name, String size) {
        currentDrink = new Drink(name, size);
    }
    
    // MODIFIES: this
    // EFFECTS: adds ice to current drink
    public void addIce(String ice) {
        currentDrink.addIce(ice);
    }

    // MODIFIES: this
    // EFFECTS: adds i to current drink
    public void addIngredient(Ingredient i) {
        currentDrink.addIngredient(i);
    }


    // MODIFIES: this
    // EFFECTS: stirs the drink, updates drink to be mixined
    public void stirDrink() {
        currentDrink.stir();
    }
    
    // MODIFIES: this
    // EFFECTS: shakes the drink, updates drink to be mixined
    public void shakeDrink() {
        currentDrink.shake();
    }

    // MODIFIES: this
    // EFFECTS: adds currentDrink to the end of menu
    public void addDrinkToMenu() {
        menu.addDrinkToMenu(currentDrink);
    }

    // MODIFIES: this
     // EFFECTS: adds currentDrink to the end of recipes
    public void saveDrinkRecipe() {
        recipes.add(currentDrink);
    }

    // MODIFIES: this
    // EFFECTS: removes drink from menu at index
    public void removeDrinkFromMenu(int index) {
        Drink drink = menu.getMenu().get(index);
        menu.removeDrinkFromMenu(drink);
    }

    // MODIFIES: this
    // EFFECTS: removes the recipe at the specififed index
    public void removeRecipe(int index) {
        Drink recipe = recipes.getRecipes().get(index);
        recipes.remove(recipe);
    }

    // MODIFIES: this
    // EFFECTS: creates a new 
    public void makeNewIngredient(String name, int sweetness, int strength, int thickness) {
        menu.addIngredient(new Ingredient(name, sweetness, strength, thickness));
    }

    // REQUIRES: index is in range of this.ingredients
    // MODIFIES: this
    // EFFECTS: removes the ingredient at the specified index
    public void removeIngredient(int index) {
        Ingredient ingredient = menu.getIngredients().get(index);
        menu.removeIngredient(ingredient);
    }

    // REQUIRES: input is Codable
    // EFFECTS: writes input as json to the file specified by path
    private void save(String path, Codable input) throws FileNotFoundException {
        Encoder encoder = new Encoder(path);

        try {
            encoder.encode(input);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    // EFFECTS: writes all menu to ./data/menu.json
    public void saveMenu() throws FileNotFoundException {
        try {
            save(MENU_PATH, menu);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    // EFFECTS: writes all recipes to ./data/recipes.json
    public void saveRecipes() throws FileNotFoundException {
        Encoder encoder = new Encoder(RECIPES_PATH);

        List<Codable> recipes = new ArrayList<>();

        for (Drink d : this.recipes.getRecipes()) {
            recipes.add((Codable) d);
        }
        
        try {
            encoder.encodeList(recipes, RECIPES_KEY);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    // EFFECTS: writes all ingredients to ./data/ingredients.json
    public void saveIngredients() throws FileNotFoundException {
        Encoder encoder = new Encoder(INGREDIENTS_PATH);

        List<Codable> ingredients = new ArrayList<>();

        for (Ingredient i : menu.getIngredients()) {
            ingredients.add((Codable) i);
        }

        try {
            encoder.encodeList(ingredients, INGREDIENTS_KEY);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    // MODIFIES: this
    // EFFECTS: reads the menu from ./data/menu.json and sets menu to be the decoded menu
    public void loadMenu() throws IOException {
        Decoder menuDecoder = new Decoder(MENU_PATH);

        try {
            menu = menuDecoder.decodeMenu();
        } catch (IOException e) {
            throw e;
        }
    }

    // MODIFIES: this
    // EFFECTS: reads the drink from ./data/recipes.json and sets recipes to be the decoded list of drinks
    public void loadRecipes() throws IOException {
        Decoder recipesDecoder = new Decoder(RECIPES_PATH);

        try {
            recipes.setRecipes(recipesDecoder.decodeDrinks(RECIPES_KEY));
        } catch (IOException e) {
            throw e;
        }
    }

    // MODIFIES: this
    // EFFECTS: reads the drink from ./data/ingredients.json and sets recipes to be the decoded list of drinks
    public void loadIngredients() throws IOException {
        Decoder ingredientsDecoder = new Decoder(INGREDIENTS_PATH);

        try {
            List<Ingredient> ingredients = ingredientsDecoder.decodeIngredients(INGREDIENTS_KEY);
            for (Ingredient i : ingredients) {
                menu.addIngredient(i);
            }
        } catch (IOException e) {
            throw e;
        }
    }
    
    // EFFECTS: creates the list of default ingredients provided to the user
    // default ingredients includes: syrup, soda, 
    private void generateDefaultIngredients() {
        Ingredient syrup = new Ingredient("Syrup", 2, 0, 2);
        menu.addIngredient(syrup);
        
        Ingredient soda = new Ingredient("Soda", 0, 0, 0);
        menu.addIngredient(soda);

        Ingredient fruitJuice = new Ingredient("Fruit Juice", 3, 1, 0);
        menu.addIngredient(fruitJuice);

        Ingredient lemonade = new Ingredient("Lemonade", -2, 2, -3);
        menu.addIngredient(lemonade);

        Ingredient cola = new Ingredient("Cola", 1, 1, -1);
        menu.addIngredient(cola);
    }

    // getters and setters
    public List<Drink> getRecipes() {
        return recipes.getRecipes();
    }

    public void setRecipes(List<Drink> recipes) {
       this.recipes.setRecipes(recipes);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Drink getCurrentDrink() {
        return currentDrink;
    }

    public void setCurrentDrink(Drink currentDrink) {
        this.currentDrink = currentDrink;
    }
}
