package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

public class EncoderTest {

    private Menu testMenu;

    private Drink d1;
    private Drink d2;

    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;

    @BeforeEach
    void setUp() {
        d1 = new Drink("d1", "Small");
        d2 = new Drink("d2", "Small");

        i1 = new Ingredient("i1", 2, 3, -1);
        i2 = new Ingredient("i2", 3, -2, 4);
        i3 = new Ingredient("i3", 1, 0, 0);

        testMenu = new Menu();
    }

    @Test
    void testEncoderFileNotThere() {
        Encoder encoder = new Encoder("/data/test/definetlyNonExistantFile.json");

        try {
            encoder.encode(d1);
            fail("No exception thrown when one was expected");
        } catch (FileNotFoundException e) {
            // pass
        }

        try {
            encoder.encodeList(new ArrayList<Codable>(), "key");
            fail("No exception thrown when one was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }   

    @Test
    void testEncodeDrink() {
        String path = "./data/test/testEncodeDrink.json";
        Encoder encoder = new Encoder(path);

        d1.setSize("Small");
        d1.addIce("none");
        d1.addIngredient(i1);
        d1.addIngredient(i2);
        d1.addIngredient(i3);
        d1.shake();

        try {
            encoder.encode(d1);

            Decoder decoder = new Decoder(path);

            Drink decodedDrink = decoder.decodeDrink();
            assertEquals(d1.getName(), decodedDrink.getName());
            assertEquals(d1.getSize(), decodedDrink.getSize());
            assertEquals(d1.getIceAmount(), decodedDrink.getIceAmount());
            assertEquals(d1.getSweetness(), decodedDrink.getSweetness());
            assertEquals(d1.getStrength(), decodedDrink.getStrength());
            assertEquals(d1.getThickness(), decodedDrink.getThickness());
            assertTrue(decodedDrink.isDrinkMixed());
            assertEquals("Shake", decodedDrink.getRecipe().get(5));
        } catch (IOException e) {
            fail("Caught exception when none was expected");
        }
    }

    @Test
    void testEncodeMenu() {
        String path = "./data/test/testEncodeMenu.json";
        Encoder encoder = new Encoder(path);

        d1.addIce("None");
        d1.addIngredient(i1);
        d1.addIngredient(i1);
        d1.addIngredient(i1);

        d2.addIce("Less");
        d2.addIngredient(i2);
        d2.addIngredient(i2);
        d2.addIngredient(i2);

        testMenu.addDrinkToMenu(d1);
        testMenu.addDrinkToMenu(d2);

        try {
            encoder.encode(testMenu);
            Decoder decoder = new Decoder(path);
            Menu decodedMenu = decoder.decodeMenu();
            assertEquals(2, decodedMenu.getMenu().size());
        } catch (IOException e) {
            fail("Caught exception when none was expected");
        }
    }

    @Test
    void tryEncodingListOfDrink() {
        Encoder encoder = new Encoder("./data/test/testListOfDrink.json");
        Decoder decoder = new Decoder("./data/test/testListOfDrink.json");
        List<Codable> drinks = new ArrayList<>();
        drinks.add(d1);
        drinks.add(d2);

        try {
            encoder.encodeList(drinks, "recipes");
            List<Drink> decodedDrinks = decoder.decodeDrinks("recipes");
            assertEquals(2, decodedDrinks.size());
        } catch (IOException e) {
            fail("Caught exception when none was expected");
        }
    }


    @Test
    void tryEncodingListOfIngredient() {
        Encoder encoder = new Encoder("./data/test/testListOfIngredient.json");
        Decoder decoder = new Decoder("./data/test/testListOfIngredient.json");
        List<Codable> ingredients = new ArrayList<>();
        ingredients.add(i1);
        ingredients.add(i2);

        try {
            encoder.encodeList(ingredients, "ingredients");
            List<Ingredient> decodedIngredients = decoder.decodeIngredients("ingredients");
            assertEquals(2, decodedIngredients.size());
        } catch (IOException e) {
            fail("Caught exception when none was expected");
        }
    }
}
