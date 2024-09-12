package model;

import org.json.JSONObject;

import persistence.Codable;

// Represents an ingredient that has a name, and traits (traits effect the ...)
public class Ingredient extends Drinkable implements Codable {

    String name;

    // REQUIRES: name is a non empty string, and sweetness, strength, and thickness are between -5 and 5
    // EFFECTS: creates a new ingredient object with a name, sweetness, strength and thickness
    public Ingredient(String name, int sweetness, int strength, int thickness) {
        this.name = name;
        this.sweetness = sweetness;
        this.strength = strength;
        this.thickness = thickness;
    }

    // EFFECTS: returns the name of an ingredient
    public String getName() {
        return name;
    }

    // REQUIRES: sweetness is between -5 and 5
    // MODIFIES: this
    // EFFECTS: changes the sweetness of an ingredient
    public void setSweetness(int sweetness) {
        this.sweetness = sweetness;
    }

    // REQUIRES: strength is between -5 and 5
    // MODIFIES: this
    // EFFECTS: changes the thickness of an ingredient
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    // REQUIRES: thickness is between -5 and 5
    // MODIFIES: this
    // EFFECTS: changes the strength of an ingredient
    public void setStrength(int strenght) {
        this.strength = strenght;
    }

    // REQUIRES: name is non-empty string
    // MODIFIES: this
    // EFFECTS: changes the name of an ingredient
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject asJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sweetness", sweetness);
        json.put("strength", strength);
        json.put("thickness", thickness);
        return json;
    }
}
