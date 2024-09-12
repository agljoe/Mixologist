package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest {
    
    private Menu testMenu;
    private Drink d1;
    private Drink d2;
    private Drink d3;
    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;

    @BeforeEach
    void runBefore() {
        testMenu = new Menu();
        d1 = new Drink("d1", "Small");
        d2 = new Drink("d2", "Meduim");
        d3 = new Drink("d3", "Large");

        i1 = new Ingredient("i1", 0, 0, 0);
        i2 = new Ingredient("i2", 0, 0, 0);
        i3 = new Ingredient("i3", 0, 0, 0);
    }

    @Test
    void testConstructor() {
        assertTrue(testMenu.getMenu().isEmpty());
        assertTrue(testMenu.getIngredients().isEmpty());
    }

    @Test
    void testAddDrinkToMenu() {
        testMenu.addDrinkToMenu(d1);
        assertEquals(d1, testMenu.getMenu().get(0));
    }

    @Test
    void testAddIngredient() {
        testMenu.addIngredient(i1);
        assertEquals(i1, testMenu.getIngredients().get(0));
    }

    @Test
    void testAddOneDrinkToMenuTwice() {
        testMenu.addDrinkToMenu(d1);
        testMenu.addDrinkToMenu(d1);

        assertEquals(d1, testMenu.getMenu().get(0));
    }

    @Test
    void testAddOneIngredientTwice() {
        testMenu.addIngredient(i1);
        testMenu.addIngredient(i1);
        assertEquals(i1, testMenu.getIngredients().get(0));
    }

    @Test
    void testAddTwoDiffDrinksToMenu() {
        testMenu.addDrinkToMenu(d2);
        testMenu.addDrinkToMenu(d3);

        List<Drink> drinks = testMenu.getMenu();

        assertEquals(d2, drinks.get(0));
        assertEquals(d3, drinks.get(1));
    }

    @Test
    void testAddTwoDiffIngredients() {
        testMenu.addIngredient(i2);
        testMenu.addIngredient(i3);

        List<Ingredient> ingredients = testMenu.getIngredients();

        assertEquals(i2, ingredients.get(0));
        assertEquals(i3, ingredients.get(1));
    }

    @Test
    void testRemoveDrink() {
        testMenu.addDrinkToMenu(d2);
        List<Drink> drinks = testMenu.getMenu();
        assertEquals(d2, drinks.get(0));

        testMenu.removeDrinkFromMenu(d2);
        drinks = testMenu.getMenu();
        assertTrue(drinks.isEmpty());
    }

    @Test
    void testRemovenIngredient() {
        testMenu.addIngredient(i1);
        List<Ingredient> ingredients = testMenu.getIngredients();
        assertEquals(i1, ingredients.get(0));
        testMenu.removeIngredient(i1);
        ingredients = testMenu.getIngredients();
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testRemoveDrinkNotThere() {
        List<Drink> drinks = testMenu.getMenu();
        assertTrue(drinks.isEmpty());
        testMenu.removeDrinkFromMenu(d1);
        drinks = testMenu.getMenu();
        assertTrue(drinks.isEmpty());
    }

    @Test
    void testRemoveIngredientsNotThere() {
        List<Ingredient> ingredients = testMenu.getIngredients();
        assertTrue(ingredients.isEmpty());
        testMenu.removeIngredient(i1);
        ingredients = testMenu.getIngredients();
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testRemoveAllDrinksFromMenu() {
        testMenu.addDrinkToMenu(d1);
        testMenu.addDrinkToMenu(d2);
        testMenu.addDrinkToMenu(d3);
        List<Drink> drinks = testMenu.getMenu();
        assertEquals(3, drinks.size());

        testMenu.removeDrinkFromMenu(d1);
        testMenu.removeDrinkFromMenu(d2);
        testMenu.removeDrinkFromMenu(d3);
        drinks = testMenu.getMenu();
        assertTrue(drinks.isEmpty());
    }

    @Test
    void testRemoveAllIngredients() {
        testMenu.addIngredient(i1);
        testMenu.addIngredient(i2);
        testMenu.addIngredient(i3);
        List<Ingredient> ingredients = testMenu.getIngredients();
        assertEquals(3, ingredients.size());

        testMenu.removeIngredient(i1);
        testMenu.removeIngredient(i2);
        testMenu.removeIngredient(i3);
        ingredients = testMenu.getIngredients();
        assertTrue(ingredients.isEmpty());
    }
}
