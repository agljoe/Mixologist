package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientTest {
    private Ingredient testIngredient;

    @BeforeEach
    void runBefore() {
        testIngredient = new Ingredient("Nothing", 0, 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Nothing", testIngredient.getName());
        assertEquals(0, testIngredient.getSweetness());
        assertEquals(0, testIngredient.getStrength());
        assertEquals(0, testIngredient.getThickness());
    }

    @Test
    void testSetSweetness() {
        assertEquals(0, testIngredient.getSweetness());
        testIngredient.setSweetness(1);
        assertEquals(1, testIngredient.getSweetness());
    }

    @Test
    void testSetStrength() {
        assertEquals(0, testIngredient.getStrength());
        testIngredient.setStrength(1);
        assertEquals(1, testIngredient.getStrength());
    }


    @Test
    void testSetThickness() {
        assertEquals(0, testIngredient.getThickness());
        testIngredient.setThickness(1);
        assertEquals(1, testIngredient.getThickness());
    }

    @Test
    void testSetName() {
        assertEquals("Nothing", testIngredient.getName());
        testIngredient.setName("Something");
        assertEquals("Something", testIngredient.getName());
    }


   
}
