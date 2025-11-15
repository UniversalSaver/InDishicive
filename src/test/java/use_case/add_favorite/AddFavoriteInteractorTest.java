package use_case.add_favorite;

import entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.favorite_recipes.FavoriteDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the AddFavoriteInteractor use case.
 * Tests the logic for adding recipes to favorites.
 */
class AddFavoriteInteractorTest {

    private AddFavoriteInteractor interactor;
    private MockFavoriteDataAccess mockDataAccess;
    private MockPresenter mockPresenter;

    @BeforeEach
    void setUp() {
        mockDataAccess = new MockFavoriteDataAccess();
        mockPresenter = new MockPresenter();
        interactor = new AddFavoriteInteractor(mockDataAccess, mockPresenter);
    }

    @Test
    void testAddNewFavoriteSuccess() {
        // create a new recipe
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);

        // execute the use case
        interactor.execute(inputData);

        // verify success view was called
        assertTrue(mockPresenter.successCalled);
        assertFalse(mockPresenter.failCalled);
        assertNull(mockPresenter.errorMessage);

        // verify recipe was saved
        assertTrue(mockDataAccess.savedRecipes.contains(recipe));
    }

    @Test
    void testAddDuplicateFavorite() {
        // create a recipe and add it to favorites first
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(recipe);

        AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);

        // try to add the same recipe again
        interactor.execute(inputData);

        // verify fail view was called with correct message
        assertFalse(mockPresenter.successCalled);
        assertTrue(mockPresenter.failCalled);
        assertEquals("Already in Favorites!", mockPresenter.errorMessage);

        // verify recipe was not saved again
        assertEquals(1, mockDataAccess.favorites.size());
    }

    @Test
    void testAddMultipleDifferentFavorites() {
        // create multiple different recipes
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");

        // add first recipe
        interactor.execute(new AddFavoriteInputData(pasta));
        assertTrue(mockPresenter.successCalled);
        mockPresenter.reset();

        // add second recipe
        interactor.execute(new AddFavoriteInputData(pizza));
        assertTrue(mockPresenter.successCalled);

        // verify both recipes are saved
        assertEquals(2, mockDataAccess.savedRecipes.size());
        assertTrue(mockDataAccess.savedRecipes.contains(pasta));
        assertTrue(mockDataAccess.savedRecipes.contains(pizza));
    }

    /**
     * Mock implementation of FavoriteDataAccessInterface for testing.
     * Simulates the data access layer without actual file operations.
     */
    private static class MockFavoriteDataAccess implements FavoriteDataAccessInterface {
        List<Recipe> favorites = new ArrayList<>();
        List<Recipe> savedRecipes = new ArrayList<>();

        @Override
        public void saveFavorite(Recipe recipe) {
            savedRecipes.add(recipe);
            favorites.add(recipe);
        }

        @Override
        public List<Recipe> getFavorites() {
            return new ArrayList<>(favorites);
        }

        @Override
        public boolean isFavorite(Recipe recipe) {
            return favorites.contains(recipe);
        }
    }

    /**
     * Mock implementation of AddFavoriteOutputBoundary for testing.
     * Tracks which methods were called and with what parameters.
     */
    private static class MockPresenter implements AddFavoriteOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        String errorMessage = null;

        @Override
        public void prepareSuccessView() {
            successCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            this.errorMessage = errorMessage;
        }

        void reset() {
            successCalled = false;
            failCalled = false;
            errorMessage = null;
        }
    }
}
