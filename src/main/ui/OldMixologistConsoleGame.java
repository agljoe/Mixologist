package ui;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import persistence.*;

// Repesents the for the Mixologist console game
public class OldMixologistConsoleGame {  
    private static final String MENU_PATH = "./data/menu.json";
    private static final String RECIPES_PATH = "./data/recipes.json";
    private static final String INGREDIENTS_PATH = "./data/ingredients.json";

    private static final String RECIPES_KEY = "recipes";
    private static final String INGREDIENTS_KEY = "ingredients";

    private List<Ingredient> ingredients;
    private List<Drink> recipes;
    private Drink currentDrink;
    private Menu menu;

    private Scanner scanner;
    private boolean isProgramRunning;

    // EFFECTS: creates a instance of the Mixologist console game
    public OldMixologistConsoleGame() {
        init();

        printSpacer();
        System.out.println("Hello bartender.");
        printSpacer();

        while (isProgramRunning) {
            mainMenu();
        }

    }

    // MODIFIES: this
    // EFFECTS: creates a new list of ingredients = generateDefaultIngredients()
    // creates a new scanner the read the users input, sets isProgram to true,
    // menu as a new menu, and recipes as empty list of drink 
    public void init() {
        this.ingredients = generateDefaultIngredients();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
        menu = new Menu();
        recipes = new ArrayList<Drink>();
    }

    // EFFECTS: diplays the start menu and passes the users input to handleInput
    public void mainMenu() {
        printSpacer();
        displayMenu();
        String input = scanner.nextLine();
        handleInput(input);
    }

    // EFFECTS: diplays a menu of all start menu options 
    public void displayMenu() {
        System.out.println("Press d to mix a drink");
        System.out.println("Press m to view the menu");
        System.out.println("Press r to view recipes");
        System.out.println("Press i to view ingredients");
        System.out.println("Press q to quit");
        printSpacer();
    }

    // EFFECTS: calls a method based on the users input
    public void handleInput(String input) {
        switch (input) {
            case "d":
                mixDrink();
                break;
            case "m":
                viewDrinkMenu();
                break;
            case "r":
                viewRecipes();
                break;
            case "i":
                viewIngredients();
                break;
            case "q":   
                quitApplication();
                break;
            default:
                printInvalidInputMessage();
                mainMenu();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the drink mixing menu
    private void mixDrink() {
        printSpacer();

        System.out.println("Name your drink");
        String name = scanner.nextLine();

        printSpacer();

        System.out.println("Choose a size");
        displaySizeOptions();
        String input = scanner.nextLine();
        String size = chooseSize(input);

        currentDrink = new Drink(name, size);
        printSpacer();

        addIce();

        if (ingredients.isEmpty()) {
            System.out.println("No ingredients");
            mainMenu();
        }
        addIngredients();
        mix();
        saveDrinkRecipe();
        addDrinkToMenu();
        mainMenu();
    }

    // EFFECTS: diplays all options a user can chose for the size of their drink
    public void displaySizeOptions() {
        System.out.println("Press 1 for small");
        System.out.println("Press 2 for meduim");
        System.out.println("Press 3 for large");
    }

    public String chooseSize(String input) {
        switch (input) {
            case "1":
                return "Small";
            case "2":
                return "Meduim";
            case "3":
                return "Large";
            default:
                printInvalidInputMessage();
                input = scanner.nextLine();
                return chooseSize(input);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the specified amount of ice to the drink
    // prints out the amount of ice added
    public void addIce() {
        System.out.println("Add ice");
        displayIceOptions();

        String input = scanner.nextLine();
        currentDrink.addIce(handleIceInput(input));

        String iceAmount = currentDrink.getIceAmount().equals("None") ? "No" : currentDrink.getIceAmount();

        System.out.println("Added " + iceAmount + " ice");
        printSpacer();
    }

    // EFFECTS displays the amounts of ice a user can add
    public void displayIceOptions() {
        System.out.println("Press 1 for no ice");
        System.out.println("Press 2 for less ice");
        System.out.println("Press 3 for more ice");
    }

    // EFFECTS: returns the ice amount for input
    public String handleIceInput(String input) {
        switch (input) {
            case "1":
                return "None";
            case "2":
                return "Less";
            case "3":
                return "More";
            default:
                printInvalidInputMessage();
                input = scanner.nextLine();
                return handleIceInput(input);
        }
    }

    // EFFECTS: lets the user add ingredients upto maxIngredients based on the size of the drink
    public void addIngredients() {
        int maxIngredients = 0;

        switch (currentDrink.getSize()) {
            case "Small":
                maxIngredients = 3;
                break;
            case "Meduim":
                maxIngredients = 4;
                break;
            case "Large":
                maxIngredients = 5;
                break;
            default:
                printInvalidInputMessage();
                addIngredients();
                break;
        }

        while (maxIngredients > 0) {
            System.out.println("Add an ingredient");
            displayIngredients();
            String input = scanner.nextLine();
            getIngredient(input);
            maxIngredients--;
        }
    }

    // diplays all ingredients in ingredients
    public void displayIngredients() {
        int index = 1;

        for (Ingredient i : ingredients) {
            printSpacer();
            System.out.println("Press " + index + " to add " + i.getName());
            System.out.print("Sweetness: " + i.getSweetness());
            System.out.print(" Strength: " + i.getStrength());
            System.out.println(" Thickness: " + i.getThickness());
            index++;
        }
        printSpacer();
    }

    // EFFECTS: gets the ingredient at the input index
    public void getIngredient(String input) {
        int index = 0;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            addIngredients();
        }

        if (index >= ingredients.size() || index < 0) {
            System.out.println("Cannot find ingredient");
            addIngredients();
            return;
        }

        addIngredient(index);
    }

    // MODIFIES: this
    // EFFECTS: adds the specified ingredient to currentDrink
    public void addIngredient(int index) {
        Ingredient i = ingredients.get(index);
        currentDrink.addIngredient(i);

        System.out.println("Added " + i.getName());
        displayDrinkStats();
    }

    public void displayDrinkStats() {
        System.out.print("Sweetness: " + currentDrink.getSweetness());
        System.out.print(" Strength: " + currentDrink.getStrength());
        System.out.println(" Thickness: " + currentDrink.getThickness());
        printSpacer();
    }

    // EFFECTS: asks the user if the want to stir or shake their drink
    public void mix() {
        displayMixingOptions();
    }

    // EFFECTS: displays mixing options and handles the users input
    public void displayMixingOptions() {
        System.out.println("Stir drink? y/n");
        printSpacer();
        String input = scanner.nextLine();
        stirDrink(input);
        displayDrinkStats();

        if (currentDrink.isDrinkMixed()) {
            return;
        }

        System.out.println("Shake drink? y/n");
        input = scanner.nextLine();
        shakeDrink(input);
        displayDrinkStats();
        printSpacer();
    }

    // MODIFIES: this
    // EFFECTS: stirs the drink if the user choose yes, otherwise does nothing
    public void stirDrink(String input) {
        switch (input) {
            case "y":
                currentDrink.stir();
                break;
            case "n":
                break;
            default:
                printInvalidInputMessage();
                displayMixingOptions();
                break;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: shakes the drink if the user choose yes, otherwise does nothing
    public void shakeDrink(String input) {
        switch (input) {
            case "y":
                currentDrink.shake();
                break;
            case "n":
                break;
            default:
                printInvalidInputMessage();
                displayMixingOptions();
                break;
        }
    }

    // EFFECTS: saves the drink if the user choose yes, otherwise does nothing
    public void saveDrinkRecipe() {
        printSpacer();
        displaySaveOptions();
        String input = scanner.nextLine();
        handleSave(input);
    }

    // EFFECTS: prints a message asking if the user wants to save their drink
    public void displaySaveOptions() {
        System.out.println("Save this drink? y/n");
    }

    // MODIFIES: this
    // EFFECTS adds currenDrink to recipes if the user picks y, otherwise does nothing
    public void handleSave(String input) {
        switch (input) {
            case "y":
                recipes.add(currentDrink);
                break;
            case "n":
                break;
            default:
                printInvalidInputMessage();
                saveDrinkRecipe();
                break;
        }
    }
 
    // EFFECTS: adds currenDrink to the end of menu and prints a message indicating if it was successfully added or not
    public void addDrinkToMenu() {
        printSpacer();
        displayDrinkMenuOptions();
        String input = scanner.nextLine();
        if (handleDrinkMenu(input)) {
            System.out.println("Successfully added " + currentDrink.getName() + " to menu");
        } else {
            System.out.println(currentDrink.getName() + " is already on the menu");
        }
    }

    // EFFECTS returns true if the drink was successfully added to the menu or not
    public boolean handleDrinkMenu(String input) {
        switch (input) {
            case "y":
                //return menu.addDrinkToMenu(currentDrink);
            case "n":
                return false;
            default:
                printInvalidInputMessage();
                return false;
        }
    }

    // EFFECTS: prints a message asking if the user wants to add their drink to the menu
    public void displayDrinkMenuOptions() {
        System.out.println("Add this drink to the menu? y/n");
    }

    // EFFECTS: shows the drink menu
    public void viewDrinkMenu() {
        printSpacer();
        displayDrinkMenu();
        String input = scanner.nextLine();
        handleEditDrinkMenu(input);
    }

    // EFFECTS: prints the names of all the drinks on the menu, and the options to go back 
    // or remove a drink the menu
    public void displayDrinkMenu() {
        printSpacer();

        List<Drink> drinks = menu.getMenu();
        int index = 1;

        System.out.println("Your menu:");

        for (Drink d : drinks) {
            System.out.print(index + ". " + d.getName());
            System.out.print("  [Size: " + d.getSweetness());
            System.out.print(", Ice: " + d.getIceAmount());
            System.out.print(", Sweetness: " + d.getSweetness());
            System.out.print(", Strength: " + d.getSweetness());
            System.out.println(", Thickness: " + d.getSweetness() + "]");
            index++;
        }

        System.out.println("Press r to remove a drink from the menu");
        System.out.println("Press s to save your menu");
        System.out.println("Press l to load your saved menu");
        System.out.println("Press q to return to main menu");
    }

    // EFFECTS: performs the method based on the user input 
    public void handleEditDrinkMenu(String input) {
        switch (input) {
            case "r": 
                removeDrinkFromMenu();
                break;
            case "s": 
                saveMenu();
                viewDrinkMenu();
                break;
            case "l" :
                loadMenu();
                viewDrinkMenu();
                break;
            case "q":
                mainMenu();
                break;
            default:
                printInvalidInputMessage();
                viewDrinkMenu();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes drink from menu at index
    public void removeDrinkFromMenu() {
        if (menu.getMenu().isEmpty()) {
            printSpacer();
            System.out.println("No drinks in menu");
            mainMenu();
        }

        System.out.println("Remove drink at index: ");

        String input = scanner.nextLine();
        int index = 0;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            viewDrinkMenu();
        }
        
        if (index >= menu.getMenu().size() || index < 0) {
            System.out.println("Cannot find drink");
            removeDrinkFromMenu();
            return;
        }

        menu.removeDrinkFromMenu(menu.getMenu().get(index));
        System.out.println("Removed drink");
        printSpacer();
        viewDrinkMenu();
    }
   
    // EFFECTS: shows the collection of recipes
    public void viewRecipes() {
        printSpacer();
        displayRecipes();
        String input = scanner.nextLine();
        handleEditRecipes(input);
    }

    // prints the names of all the drink recipces, and the options to go back, remove, or veiw a recipes
    public void displayRecipes() {
        printSpacer();

        int index = 1;

        System.out.println("Recipes:");

        for (Drink d : recipes) {
            System.out.println(index + ". " + d.getName());
            index++;
        }

        System.out.println("Press r to remove a recipe");
        System.out.println("Press v to view a recipe");
        System.out.println("Press s to save your recipes");
        System.out.println("Press l to load your recipes");
        System.out.println("Press q to return to main menu");
    }

    // EFFECTS: performs the method based on the user input 
    public void handleEditRecipes(String input) {
        switch (input) {
            case "r":
                removeRecipe();
                break;
            case "v":
                viewRecipe();
                break;
            case "s":
                saveRecipes();
                viewRecipes();
                break;
            case "l":
                loadRecipes();
                viewRecipes();
                break;
            case "q":
                mainMenu();
                break;
            default:
                printInvalidInputMessage();
                viewRecipes();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the recipe at the specififed index
    public void removeRecipe() {
        if (recipes.isEmpty()) {
            printSpacer();
            System.out.println("No recipes");
            mainMenu();
        }

        System.out.println("Remove recipe at index: ");

        String input = scanner.nextLine();
        int index = 0;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            viewRecipes();
        }

        if (index >= recipes.size()) {
            System.out.println("Cannot recipe");
            removeDrinkFromMenu();
        }

        recipes.remove(index);
        System.out.println("Removed recipe");
        printSpacer();
        viewRecipes();
    }

    // EFFECTS: prints the recipe for a specified drink
    public void viewRecipe() {
        if (recipes.isEmpty()) {
            printSpacer();
            System.out.println("No recipes");
            mainMenu();
        }

        System.out.println("View a recipe at index: ");

        String input = scanner.nextLine();
        int index = 0;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            viewRecipes();
            return;
        }

        List<String> recipe = recipes.get(index).getRecipe();

        for (String instruction : recipe) {
            System.out.println(instruction);
        }

        viewRecipes();
    }

    // EFFECTS: displays all available ingredients, and the options to add / remove ingredients
    // or return to the main menu
    public void viewIngredients() {
        printIngredients();
        displayIngredentsMenu();
        printSpacer();
        String input = scanner.nextLine();
        handleIngredientMenu(input);
    }

    // EFFECTS: prints out all ingredients in the form :
    // ingredient.getName [ingredient.getSweetness(), ingredient.getStrength(), ingredient.getThickness()]
    public void printIngredients() {
        int index = 1;

        for (Ingredient i : ingredients) {
            System.out.print(index + ". " + i.getName());
            System.out.print(" [Sweetness: " + i.getSweetness());
            System.out.print(", Strength: " + i.getStrength());
            System.out.println(", Thickness: " + i.getThickness() + "]");
            index++;
        }
    }

    // EFFECTS: prints the available commands for users in the ingredients menu
    public void displayIngredentsMenu() {
        System.out.println("Press a to add a new ingredient");
        System.out.println("Press r to remove an ingredeint");
        System.out.println("Press s to save your ingredients");
        System.out.println("Press l to load your ingredeints");
        System.out.println("Press q to return to main menu");
    }

    // EFFECTS: calls a method based on the users input
    public void handleIngredientMenu(String input) {
        switch (input) {
            case "a":
                makeNewIngredient();
                break;
            case "r":
                removeIngredient();
                break;
            case "s":
                saveIngredients();
                viewIngredients();
                break;
            case "l" :
                loadIngredients();
                viewIngredients();
                break;
            case "q":
                mainMenu();
                break;
            default:
                printInvalidInputMessage();
                viewIngredients();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new 
    public void makeNewIngredient() {
        printSpacer();

        System.out.println("Choose a name");
        String input = scanner.nextLine();
        String name = input;

        System.out.println("Choose a sweetness level between -5 & 5");
        printSpacer();
        input = scanner.nextLine();
        int sweetness = setTrait(input, "sweetness");

        System.out.println("Choose a strength level between -5 & 5");
        printSpacer();
        input = scanner.nextLine();
        int strength = setTrait(input, "strength");

        System.out.println("Choose a thickness level between -5 & 5");
        printSpacer();
        input = scanner.nextLine();
        int thickness = setTrait(input, "thickness");

        Ingredient i = new Ingredient(name, sweetness, strength, thickness);
        ingredients.add(i);
        System.out.println("Added ingredient " + name);
        printSpacer();
        viewIngredients();
    }

    // MODIFIES: this
    // EFFECTS: removes the ingredient at the specified index
    public void removeIngredient() {
        if (ingredients.isEmpty()) {
            printSpacer();
            System.out.println("No ingredients");
            mainMenu();
        }

        System.out.println("Remove ingredient at index: ");

        String input = scanner.nextLine();
        int index = 0;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            viewIngredients();
        }
        
        if (index >= ingredients.size() || index < 0) {
            System.out.println("Cannot find ingredient");
            removeIngredient();
            return;
        }

        ingredients.remove(index);
        System.out.println("Removed ingredient");
        printSpacer();
        viewIngredients();
    }

    // REQUIRES: trait is one of "sweetness, strength, or thickness"
    // MODIFIES: this, input
    // EFFECTS sets the value for a trait, if input is out of bounds, 
    // sets value to the lower or upper bound respectively
    private int setTrait(String input, String trait) {
        int value = 0;

        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            printInvalidInputMessage();
            System.out.println("Choose a " + trait + " level between -5 & 5");
            printSpacer();
            input = scanner.nextLine();
            return setTrait(input, trait);
        }

        if (value > 5) {
            System.out.println("Set " + trait + " to 5");
            return 5;
        } else if (value < -5) {
            System.out.println("Set " + trait + " to -5");
            return -5;
        } else {
            return value;
        }
    }

    // EFFECTS: writes all menu to ./data/menu.json
    private void saveMenu() {
        try {
            save(MENU_PATH, menu);
            System.out.println("Saved menu");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to locate file");
        }
    }

    // EFFECTS: writes all recipes to ./data/recipes.json
    private void saveRecipes() {
        Encoder encoder = new Encoder(RECIPES_PATH);

        List<Codable> recipes = new ArrayList<>();

        for (Drink d : this.recipes) {
            recipes.add((Codable) d);
        }
        
        try {
            encoder.encodeList(recipes, RECIPES_KEY);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to locate file");
        }

        System.out.println("Saved recipes");
    }

    // EFFECTS: writes all ingredients to ./data/ingredients.json
    private void saveIngredients() {
        Encoder encoder = new Encoder(INGREDIENTS_PATH);

        List<Codable> ingredients = new ArrayList<>();

        for (Ingredient i : this.ingredients) {
            ingredients.add((Codable) i);
        }

        try {
            encoder.encodeList(ingredients, INGREDIENTS_KEY);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to locate file");
        }

        System.out.println("Saved ingredients");
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

    // MODIFIES: this
    // EFFECTS: reads the menu from ./data/menu.json and sets menu to be the decoded menu
    private void loadMenu() {
        Decoder menuDecoder = new Decoder(MENU_PATH);

        try {
            menu = menuDecoder.decodeMenu();
            System.out.println("Loaded menu");
        } catch (IOException e) {
            System.out.println("Unable to load menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: reads the drink from ./data/recipes.json and sets recipes to be the decoded list of drinks
    private void loadRecipes() {
        Decoder recipesDecoder = new Decoder(RECIPES_PATH);

        try {
            recipes = recipesDecoder.decodeDrinks(RECIPES_KEY);
            System.out.println("Loaded recipes");
        } catch (IOException e) {
            System.out.println("Unable to load menu");
        }
    }

    private void loadIngredients() {
        Decoder ingredientsDecoder = new Decoder(INGREDIENTS_PATH);

        try {
            ingredients = ingredientsDecoder.decodeIngredients(INGREDIENTS_KEY);
            System.out.println("Loaded ingredients");
        } catch (IOException e) {
            System.out.println("Unable to load ingredients");
        }
    }

    // MODIFIES: this
    // EFFECTS: ends the game
    private void quitApplication() {
        isProgramRunning = false;
    }

    // EFFECTS: creates the list of default ingredients provided to the user
    // default ingredients includes: syrup, soda, 
    private List<Ingredient> generateDefaultIngredients() {
        List<Ingredient> defauIngredients = new ArrayList<Ingredient>();

        Ingredient syrup = new Ingredient("Syrup", 2, 0, 2);
        defauIngredients.add(syrup);
        
        Ingredient soda = new Ingredient("Soda", 0, 0, 0);
        defauIngredients.add(soda);

        Ingredient fruitJuice = new Ingredient("Fruit Juice", 3, 1, 0);
        defauIngredients.add(fruitJuice);

        Ingredient lemonade = new Ingredient("Lemonade", -2, 2, -3);
        defauIngredients.add(lemonade);

        Ingredient cola = new Ingredient("Cola", 1, 1, -1);
        defauIngredients.add(cola);

        return defauIngredients;
    }

    // EFFECTS: prints a message for unhandled inputs
    private void printInvalidInputMessage() {
        System.out.println("Invalid input");
        printSpacer();
    }

    // EFFECTS: prints out a spacer to separate lines of text
    private void printSpacer() {
        System.out.println("------------------------------------------------");
    }

}
