package model;

public abstract class Drinkable {

    protected int sweetness;
    protected int strength;
    protected int thickness;

     // EFFECTS: returns the sweetness of a given drink
    public int getSweetness() {
        return sweetness;
    }

    // EFFECTS: returns the strength of a given drink
    public int getStrength() {
        return strength;
    }

    // EFFECTS: returns the thickness of a given drink
    public int getThickness() {
        return thickness;
    }
}
