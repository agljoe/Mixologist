package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class RecipesTest {

    private Recipes testRecipes;
    private Drink d1;

    @BeforeEach
    void setUp() {
        testRecipes = new Recipes();
        d1 = new Drink("d1", "Small");
    }

    @Test
    void testConstructor() {
        assertTrue(testRecipes.getRecipes().isEmpty());
    }

    @Test
    void testAddDrink() {
        testRecipes.add(d1);
        assertEquals(d1, testRecipes.getRecipes().get(0));
    }

    @Test
    void testRemoveDrink() {
        testRecipes.add(d1);
        assertEquals(d1, testRecipes.getRecipes().get(0));
        testRecipes.remove(d1);
        assertTrue(testRecipes.getRecipes().isEmpty());
    }

    @Test
    void removeRecipeNotThere() {
        testRecipes.remove(d1);
        assertTrue(testRecipes.getRecipes().isEmpty());
    }
}
