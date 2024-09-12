package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrinkTest {
    private Drink testDrink;
    private Ingredient soda;
    private Ingredient syrup;
    Ingredient juice;
    
    @BeforeEach
    void runBefore() {
        testDrink = new Drink("Test", "Small");
        soda = new Ingredient("Soda", 0, 0, -1);
        syrup = new Ingredient("Syrup", 4, 2, 1);
        juice = new Ingredient("Apple Juice", 3, 1, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", testDrink.getName());
        assertEquals("Small", testDrink.getSize());
        assertEquals("None", testDrink.getIceAmount());
        assertEquals(0, testDrink.getSweetness());
        assertEquals(0, testDrink.getThickness());
        assertEquals(0, testDrink.getStrength());
        assertTrue(testDrink.getIngredients().isEmpty());
        assertTrue(testDrink.getRecipe().isEmpty());
    }

    @Test
    void testAddIce() {
        assertEquals("None", testDrink.getIceAmount());

        testDrink.addIce("less");
        assertEquals("less", testDrink.getIceAmount());
        assertEquals("Add less ice", testDrink.getRecipe().get(0));
    }

    @Test
    void testStir() {
        int thickness = testDrink.getThickness();

        testDrink.stir();
        assertEquals("Stir", testDrink.getRecipe().get(0));
        assertEquals(thickness, testDrink.getThickness());
    }

    @Test
    void testShake() {
        int thickness = testDrink.getThickness();

        testDrink.shake();
        assertEquals("Shake", testDrink.getRecipe().get(0));
        assertEquals(thickness - 1, testDrink.getThickness());
    }

    @Test
    void testSetName() {
        assertEquals("Test", testDrink.getName());
        testDrink.setName("Water");
        assertEquals("Water", testDrink.getName());
    }

    @Test
    void testAddIngredientNoIngredients() {
        Ingredient testIngredient = new Ingredient("Water", 0, 0, 0);

        assertTrue(testDrink.getIngredients().isEmpty());

        testDrink.addIngredient(testIngredient);

        List<Ingredient> testIngredients = testDrink.getIngredients();
        assertEquals(testIngredient, testIngredients.get(0));
        assertFalse(testDrink.getRecipe().isEmpty());
    }

    @Test
    void testAddTwoDiffIngredients() {
        assertTrue(testDrink.getIngredients().isEmpty());

        testDrink.addIngredient(soda);
        assertEquals(-1, testDrink.getThickness());
        testDrink.addIngredient(syrup);
        assertEquals(4, testDrink.getSweetness());
        assertEquals(2, testDrink.getStrength());
        assertEquals(0, testDrink.getThickness());

        List<Ingredient> testIngredients = testDrink.getIngredients();
        assertEquals(soda, testIngredients.get(0));
        assertEquals(syrup, testIngredients.get(1));

        List<String> recipe = testDrink.getRecipe();

        assertEquals("Add soda", recipe.get(0));
        assertEquals("Add syrup", recipe.get(1));
    }

    @Test
    void testAddSameIngridentTwice() {
        assertTrue(testDrink.getIngredients().isEmpty());

        testDrink.addIngredient(juice);
        assertEquals(3, testDrink.getSweetness());
        assertEquals(1, testDrink.getStrength());
        assertEquals(0, testDrink.getThickness());

        testDrink.addIngredient(juice);
        assertEquals(6, testDrink.getSweetness());
        assertEquals(2, testDrink.getStrength());
        assertEquals(0, testDrink.getThickness());

        List<Ingredient> testIngredients = testDrink.getIngredients();
        assertEquals(juice, testIngredients.get(0));
        assertEquals(juice, testIngredients.get(1));

        List<String> recipe = testDrink.getRecipe();

        assertEquals("Add apple juice", recipe.get(0));
        assertEquals("Add apple juice", recipe.get(1));
    }

    @Test
    void testMixADrink() {
        testDrink.setSize("Large");
        testDrink.addIce("Less");

        testDrink.addIngredient(juice);
        assertEquals(3, testDrink.getSweetness());
        assertEquals(1, testDrink.getStrength());
        assertEquals(0, testDrink.getThickness());

        testDrink.addIngredient(syrup);
        assertEquals(7, testDrink.getSweetness());
        assertEquals(3, testDrink.getStrength());
        assertEquals(1, testDrink.getThickness());

        testDrink.shake();
        assertEquals(0, testDrink.getThickness());

        List<String> recipe = testDrink.getRecipe();
        assertEquals("Add less ice", recipe.get(1));
        assertEquals("Choose large", recipe.get(0));
        assertEquals("Add apple juice", recipe.get(2));
        assertEquals("Add syrup", recipe.get(3));
        assertEquals("Shake", recipe.get(4));
        assertTrue(testDrink.isDrinkMixed());

        testDrink.addIngredient(soda);
        assertEquals(-1, testDrink.getThickness());
        assertEquals("Add soda", recipe.get(5));
    }

    @Test
    void testResetDrink() {
        testDrink.addIce("None");
        testDrink.addIngredient(juice);
        testDrink.addIngredient(soda);

        List<Ingredient> ingredients = testDrink.getIngredients();

        assertEquals("None", testDrink.getIceAmount());
        assertEquals(juice, ingredients.get(0));
        assertEquals(soda, ingredients.get(1));

        testDrink.shake();

        assertTrue(testDrink.isDrinkMixed());

        testDrink.resetDrink();

        assertEquals("Test", testDrink.getName());
        assertEquals("Small", testDrink.getSize());
        assertEquals("None", testDrink.getIceAmount());
        assertEquals(0, testDrink.getSweetness());
        assertEquals(0, testDrink.getThickness());
        assertEquals(0, testDrink.getStrength());
        assertTrue(testDrink.getIngredients().isEmpty());
        assertTrue(testDrink.getRecipe().isEmpty());
    }
}
