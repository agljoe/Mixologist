package model;

import java.util.ArrayList;
import java.util.List;

// Represents recipes as a list of drinks
public class Recipes {

    private List<Drink> recipes;

    // Constructs a new Recipes object with and empty array list of drink
    public Recipes() {
        recipes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds drink to recipes and logs the event
    public void add(Drink d) {
        recipes.add(d);
        Event addRecipeEvent = new Event("Added recipe for: " + d.getName());
        EventLog.getInstance().logEvent(addRecipeEvent);
    }
    
    
    // MODIFIES: this
    // EFFECTS if drink is in recipes, removes drink from recipes and logs the event,
    // otherwise does nothing, 
    public void remove(Drink d) {
        if (recipes.contains(d)) {
            Event removeRecipeEvent = new Event("Removed recipe for: " + d.getName());
            EventLog.getInstance().logEvent(removeRecipeEvent);
            recipes.remove(d);
        }
    }

    //getters and setters
    public List<Drink> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Drink> recipes) {
        this.recipes = recipes;
    }
}
