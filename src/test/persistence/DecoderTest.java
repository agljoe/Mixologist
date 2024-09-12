package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.*;

public class DecoderTest {

    private Decoder decoder;

    @Test
    void testDecoderFileNotThere() {
        Decoder decoder = new Decoder("./data/thisFileDoesNotExist");

        try {
            decoder.decodeDrink();
            fail("No IO exception when one was expected");
        } catch (IOException e) {
            // pass 
        }

        try {
            decoder.decodeIngredient();
            fail("No IO exception when one was expected");
        } catch (IOException e) {
            // pass 
        }

        try {
            decoder.decodeMenu();
            fail("No IO exception when one was expected");
        } catch (IOException e) {
            // pass 
        }
    }

    @Test 
    void testDecodeDrink() {
        decoder = new Decoder("./data/test/testDrink.json");

        try {
            Drink testDrink = decoder.decodeDrink();
            assertEquals(testDrink.getName(), "Test Drink");
            assertEquals(testDrink.getIceAmount(), "None");
            assertEquals(testDrink.getSize(), "Small");
            assertEquals(3, testDrink.getIngredients().size());
            assertEquals(5, testDrink.getRecipe().size());
        } catch (IOException e) {
            fail("Caught unexpected error when decoding");
        }
    }

    @Test
    void testDecodeStirredDrink() {
        decoder = new Decoder("./data/test/testStirredDrink.json");

        try {
            Drink stirredDrink = decoder.decodeDrink();
            assertTrue(stirredDrink.isDrinkMixed());
            assertEquals("Stir", stirredDrink.getRecipe().get(5));
        } catch (IOException e) {
            fail("Caught unexpected error when decoding");
        }
    }

    @Test
    void testDecodeShakenDrink() {
        decoder = new Decoder("./data/test/testShakenDrink.json");

        try {
            Drink shakenDrink = decoder.decodeDrink();
            assertTrue(shakenDrink.isDrinkMixed());
            assertEquals("Shake", shakenDrink.getRecipe().get(5));
        } catch (IOException e) {
            fail("Caught unexpected error when decoding");
        }
    }

    @Test void testDecodeIngredient() {
        decoder = new Decoder("./data/test/testIngredient.json");

        try {
            Ingredient testIngredient = decoder.decodeIngredient();
            assertEquals("Test Ingredient", testIngredient.getName());
            assertEquals(-1, testIngredient.getSweetness());
            assertEquals(5, testIngredient.getStrength());
            assertEquals(2, testIngredient.getThickness());
        } catch (IOException e) {
            fail("Caught unexpected error when decoding");
        }
    }
}
