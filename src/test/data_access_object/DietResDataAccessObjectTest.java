package data_access_object;

import data_access.dietary_restriction.DietResDataAccessObject;
import entity.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class DietResDataAccessObjectTest {

    private static final String TEST_FILE_PATH = "test_restrictions.json";
    private File testFile;

    @BeforeEach
    void setUp() {
        // Ensure we start with a clean state
        testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testInitializationWithNoFile() {
        // Test that a new DAO creates an empty list if file doesn't exist
        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);
        assertTrue(dao.getResIngredients().isEmpty(), "New DAO should start with empty list");
    }

    @Test
    void testSaveAndLoadPersistence() {
        DietResDataAccessObject dao1 = new DietResDataAccessObject(TEST_FILE_PATH);
        Ingredient peanut = new Ingredient("Peanuts", "");
        dao1.saveRestriction(peanut);

        DietResDataAccessObject dao2 = new DietResDataAccessObject(TEST_FILE_PATH);

        assertTrue(dao2.isRestricted(peanut), "Data should persist across DAO instances");
        assertEquals(1, dao2.getResIngredients().size());
        assertEquals("Peanuts", dao2.getResIngredients().get(0).getName());
    }

    @Test
    void testIsRestrictedCaseInsensitivity() {
        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);
        dao.saveRestriction(new Ingredient("Milk", ""));

        // Check various casing
        assertTrue(dao.isRestricted(new Ingredient("milk", "")), "Should detect lowercase");
        assertTrue(dao.isRestricted(new Ingredient("MILK", "")), "Should detect uppercase");
        assertTrue(dao.isRestricted(new Ingredient("Milk", "")), "Should detect exact match");

        assertFalse(dao.isRestricted(new Ingredient("Eggs", "")), "Should not detect unrelated ingredient");
    }

    @Test
    void testSaveDuplicate() {
        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);

        dao.saveRestriction(new Ingredient("Gluten", ""));
        dao.saveRestriction(new Ingredient("Gluten", ""));
        dao.saveRestriction(new Ingredient("GLUTEN", ""));

        assertEquals(1, dao.getResIngredients().size(), "Should not allow duplicate ingredients");
    }

    @Test
    void testRemoveRestriction() {
        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);
        Ingredient ingredient = new Ingredient("Shellfish", "");
        dao.saveRestriction(ingredient);

        assertTrue(dao.isRestricted(ingredient), "Setup failed: Ingredient not added");

        dao.removeRestriction(ingredient);

        assertFalse(dao.isRestricted(ingredient), "Ingredient should be removed from memory");
        assertTrue(dao.getResIngredients().isEmpty());

        DietResDataAccessObject dao2 = new DietResDataAccessObject(TEST_FILE_PATH);
        assertTrue(dao2.getResIngredients().isEmpty(), "Removal should persist to file");
    }

    @Test
    void testRemoveRestrictionCaseInsensitive() {
        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);
        dao.saveRestriction(new Ingredient("Beef", ""));

        dao.removeRestriction(new Ingredient("beef", ""));

        assertFalse(dao.isRestricted(new Ingredient("Beef", "")), "Should remove regardless of case");
    }

    @Test
    void testHandleCorruptedFile() throws IOException {
        Files.writeString(testFile.toPath(), "{ not valid json }");

        DietResDataAccessObject dao = new DietResDataAccessObject(TEST_FILE_PATH);

        assertTrue(dao.getResIngredients().isEmpty(), "Should handle corrupted file by initializing empty list");
    }
}