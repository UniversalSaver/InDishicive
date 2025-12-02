package data_access;

import databases.inventory.InventoryDataAccessObject;
import entity.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDataAccessObjectTest {

    private static final String TEST_FILE_PATH = "test_inventory.json";
    private InventoryDataAccessObject dao;

    @BeforeEach
    void setUp() {
        dao = new InventoryDataAccessObject(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddIngredientCreatesFile() {
        Ingredient ingredient = new Ingredient("Milk", "1 gallon");

        dao.addIngredient(ingredient);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    void testAddIngredientStoresIngredient() {
        Ingredient ingredient = new Ingredient("Milk", "1 gallon");

        dao.addIngredient(ingredient);

        List<Ingredient> ingredients = dao.getAllIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("Milk", ingredients.get(0).getName());
        assertEquals("1 gallon", ingredients.get(0).getAmount());
    }

    @Test
    void testAddDuplicateIngredient() {
        Ingredient ingredient = new Ingredient("Milk", "1 gallon");

        dao.addIngredient(ingredient);
        dao.addIngredient(ingredient);

        List<Ingredient> ingredients = dao.getAllIngredients();
        assertEquals(1, ingredients.size());
    }

    @Test
    void testAddDuplicateIngredientCaseInsensitive() {
        Ingredient milk1 = new Ingredient("Milk", "1 gallon");
        Ingredient milk2 = new Ingredient("MILK", "2 gallons");

        dao.addIngredient(milk1);
        dao.addIngredient(milk2);

        List<Ingredient> ingredients = dao.getAllIngredients();
        assertEquals(1, ingredients.size());
    }

    @Test
    void testAddMultipleIngredients() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        Ingredient eggs = new Ingredient("Eggs", "12");
        Ingredient flour = new Ingredient("Flour", "2 cups");

        dao.addIngredient(milk);
        dao.addIngredient(eggs);
        dao.addIngredient(flour);

        List<Ingredient> ingredients = dao.getAllIngredients();
        assertEquals(3, ingredients.size());
    }

    @Test
    void testRemoveIngredient() {
        Ingredient ingredient = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(ingredient);

        boolean removed = dao.removeIngredient(ingredient);

        assertTrue(removed);
        List<Ingredient> ingredients = dao.getAllIngredients();
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testRemoveNonExistentIngredient() {
        Ingredient ingredient = new Ingredient("Milk", "1 gallon");

        boolean removed = dao.removeIngredient(ingredient);

        assertFalse(removed);
    }

    @Test
    void testRemoveIngredientCaseInsensitive() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(milk);

        Ingredient MILK = new Ingredient("MILK", "");
        boolean removed = dao.removeIngredient(MILK);

        assertTrue(removed);
        assertTrue(dao.getAllIngredients().isEmpty());
    }

    @Test
    void testFindIngredientByName() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(milk);

        Ingredient found = dao.findIngredientByName("Milk");

        assertNotNull(found);
        assertEquals("Milk", found.getName());
        assertEquals("1 gallon", found.getAmount());
    }

    @Test
    void testFindIngredientByNameCaseInsensitive() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(milk);

        Ingredient found = dao.findIngredientByName("MILK");

        assertNotNull(found);
        assertEquals("Milk", found.getName());
    }

    @Test
    void testFindIngredientByNameNotFound() {
        Ingredient found = dao.findIngredientByName("Milk");

        assertNull(found);
    }

    @Test
    void testGetAllIngredientsReturnsEmptyListInitially() {
        List<Ingredient> ingredients = dao.getAllIngredients();

        assertNotNull(ingredients);
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testPersistenceAcrossInstances() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(milk);

        InventoryDataAccessObject newDao = new InventoryDataAccessObject(TEST_FILE_PATH);

        List<Ingredient> ingredients = newDao.getAllIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("Milk", ingredients.get(0).getName());
        assertEquals("1 gallon", ingredients.get(0).getAmount());
    }

    @Test
    void testLoadFromNonExistentFile() {
        InventoryDataAccessObject newDao = new InventoryDataAccessObject("nonexistent_inventory.json");

        List<Ingredient> ingredients = newDao.getAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testLoadFromEmptyFile() {
        try {
            Files.createFile(new File(TEST_FILE_PATH).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InventoryDataAccessObject newDao = new InventoryDataAccessObject(TEST_FILE_PATH);

        List<Ingredient> ingredients = newDao.getAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testGetAllIngredientsReturnsDefensiveCopy() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        dao.addIngredient(milk);

        List<Ingredient> ingredients = dao.getAllIngredients();
        ingredients.clear();

        List<Ingredient> ingredientsAgain = dao.getAllIngredients();
        assertEquals(1, ingredientsAgain.size());
    }

    @Test
    void testRemovePersistsToFile() {
        Ingredient milk = new Ingredient("Milk", "1 gallon");
        Ingredient eggs = new Ingredient("Eggs", "12");
        dao.addIngredient(milk);
        dao.addIngredient(eggs);

        dao.removeIngredient(milk);

        InventoryDataAccessObject newDao = new InventoryDataAccessObject(TEST_FILE_PATH);
        List<Ingredient> ingredients = newDao.getAllIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("Eggs", ingredients.get(0).getName());
    }
}

