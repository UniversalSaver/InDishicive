package data_access;

import entity.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FavoriteDataAccessObject.
 * Tests JSON file persistence and data access operations.
 */
class FavoriteDataAccessObjectTest {

    private static final String TEST_FILE_PATH = "test_favorites.json";
    private FavoriteDataAccessObject dao;

    @BeforeEach
    void setUp() {
        // create a new DAO with test file path
        dao = new FavoriteDataAccessObject(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        // clean up test file after each test
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testSaveFavoriteCreatesFile() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        dao.saveFavorite(recipe);

        // verify file was created
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    void testSaveFavoriteStoresRecipe() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        dao.saveFavorite(recipe);

        // verify recipe is in favorites
        List<Recipe> favorites = dao.getFavorites();
        assertEquals(1, favorites.size());
        assertEquals("Pasta", favorites.get(0).getTitle());
        assertEquals("Italian", favorites.get(0).getCategory());
    }

    @Test
    void testSaveDuplicateRecipe() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        // save same recipe twice
        dao.saveFavorite(recipe);
        dao.saveFavorite(recipe);

        // verify only one copy exists
        List<Recipe> favorites = dao.getFavorites();
        assertEquals(1, favorites.size());
    }

    @Test
    void testSaveMultipleRecipes() {
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");
        Recipe sushi = new Recipe("Sushi", new ArrayList<>(), "Roll sushi", "", "", "Japanese");

        dao.saveFavorite(pasta);
        dao.saveFavorite(pizza);
        dao.saveFavorite(sushi);

        // verify all three recipes are saved
        List<Recipe> favorites = dao.getFavorites();
        assertEquals(3, favorites.size());
    }

    @Test
    void testIsFavoriteReturnsTrueForSavedRecipe() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        dao.saveFavorite(recipe);

        // verify isFavorite returns true
        assertTrue(dao.isFavorite(recipe));
    }

    @Test
    void testIsFavoriteReturnsFalseForUnsavedRecipe() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        // verify isFavorite returns false for unsaved recipe
        assertFalse(dao.isFavorite(recipe));
    }

    @Test
    void testGetFavoritesReturnsEmptyListInitially() {
        List<Recipe> favorites = dao.getFavorites();

        assertNotNull(favorites);
        assertTrue(favorites.isEmpty());
    }

    @Test
    void testPersistenceAcrossInstances() {
        // save a recipe with first DAO instance
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        dao.saveFavorite(recipe);

        // create a new DAO instance with same file path
        FavoriteDataAccessObject newDao = new FavoriteDataAccessObject(TEST_FILE_PATH);

        // verify recipe is loaded from file
        List<Recipe> favorites = newDao.getFavorites();
        assertEquals(1, favorites.size());
        assertEquals("Pasta", favorites.get(0).getTitle());
        assertEquals("Italian", favorites.get(0).getCategory());
    }

    @Test
    void testLoadFromNonExistentFile() {
        // create DAO with non-existent file
        FavoriteDataAccessObject newDao = new FavoriteDataAccessObject("nonexistent_file.json");

        // verify it returns empty list without crashing
        List<Recipe> favorites = newDao.getFavorites();
        assertNotNull(favorites);
        assertTrue(favorites.isEmpty());
    }

    @Test
    void testLoadFromEmptyFile() throws IOException {
        // create an empty file
        File file = new File(TEST_FILE_PATH);
        file.createNewFile();

        // create DAO with empty file
        FavoriteDataAccessObject newDao = new FavoriteDataAccessObject(TEST_FILE_PATH);

        // verify it returns empty list without crashing
        List<Recipe> favorites = newDao.getFavorites();
        assertNotNull(favorites);
        assertTrue(favorites.isEmpty());
    }

    @Test
    void testRecipeOnlyStoresTitleAndCategory() throws IOException {
        // create recipe with all fields populated
        Recipe recipe = new Recipe(
            "Pasta",
            List.of(), // ingredients
            "Step 1: Boil water. Step 2: Add pasta.",
            "http://example.com/pasta.jpg",
            "http://youtube.com/watch?v=123",
            "Italian"
        );

        dao.saveFavorite(recipe);

        // create new DAO instance to load from file
        FavoriteDataAccessObject newDao = new FavoriteDataAccessObject(TEST_FILE_PATH);
        List<Recipe> favorites = newDao.getFavorites();

        // verify only title and category are preserved
        Recipe loaded = favorites.get(0);
        assertEquals("Pasta", loaded.getTitle());
        assertEquals("Italian", loaded.getCategory());

        // verify other fields are empty
        assertTrue(loaded.getIngredients().isEmpty());
        assertEquals("", loaded.getSteps());
        assertEquals("", loaded.getImageLink());
        assertEquals("", loaded.getYoutubeLink());
    }

    @Test
    void testGetFavoritesReturnsDefensiveCopy() {
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        dao.saveFavorite(recipe);

        List<Recipe> favorites = dao.getFavorites();
        favorites.clear();

        // verify original list is unchanged
        List<Recipe> favoritesAgain = dao.getFavorites();
        assertEquals(1, favoritesAgain.size());
    }
}
