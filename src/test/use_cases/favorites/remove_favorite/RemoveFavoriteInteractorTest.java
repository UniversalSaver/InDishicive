package use_cases.favorites.remove_favorite;

import entity.Recipe;
import logic.favorites.remove_favorite.RemoveFavoriteInteractor;
import logic.favorites.remove_favorite.RemoveFavoriteOutputBoundary;
import logic.favorites.remove_favorite.RemoveFavoriteOutputData;
import logic.favorites.remove_favorite.RemoveFavoriteInputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the RemoveFavoriteInteractor use case.
 * Tests the logic for removing recipes from favorites.
 */
class RemoveFavoriteInteractorTest {

    private RemoveFavoriteInteractor interactor;
    private MockFavoriteDataAccess mockDataAccess;
    private MockPresenter mockPresenter;

    @BeforeEach
    void setUp() {
        mockDataAccess = new MockFavoriteDataAccess();
        mockPresenter = new MockPresenter();
        interactor = new RemoveFavoriteInteractor(mockDataAccess, mockDataAccess, mockPresenter);
    }

    @Test
    void testRemoveFavoriteSuccess() {
        // create a recipe and add it to favorites first
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(recipe);

        RemoveFavoriteInputData inputData = new RemoveFavoriteInputData(recipe);

        interactor.execute(inputData);

        // check if success view was called
        assertTrue(mockPresenter.successCalled);
        assertFalse(mockPresenter.failCalled);
        assertNull(mockPresenter.errorMessage);

        // check if recipe was removed
        assertFalse(mockDataAccess.favorites.contains(recipe));
        assertTrue(mockDataAccess.removedRecipes.contains(recipe));
    }

    @Test
    void testRemoveNonFavoriteRecipe() {
        // create a recipe but dot add it to favorites
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");

        RemoveFavoriteInputData inputData = new RemoveFavoriteInputData(recipe);

        // try to remove a recipe that isn't a favorite
        interactor.execute(inputData);

        // check if fail view was called with correct message
        assertFalse(mockPresenter.successCalled);
        assertTrue(mockPresenter.failCalled);
        assertEquals("Recipe is not in favorites!", mockPresenter.errorMessage);

        // check that no recipe was removed
        assertEquals(0, mockDataAccess.removedRecipes.size());
    }

    @Test
    void testRemoveMultipleFavorites() {
        // create multiple recipes and add them to favorites
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");
        Recipe salad = new Recipe("Salad", new ArrayList<>(), "Toss salad", "", "", "American");

        mockDataAccess.favorites.add(pasta);
        mockDataAccess.favorites.add(pizza);
        mockDataAccess.favorites.add(salad);

        // remove first recipe
        interactor.execute(new RemoveFavoriteInputData(pasta));
        assertTrue(mockPresenter.successCalled);
        assertEquals(2, mockDataAccess.favorites.size());
        mockPresenter.reset();

        // remove second recipe
        interactor.execute(new RemoveFavoriteInputData(pizza));
        assertTrue(mockPresenter.successCalled);
        assertEquals(1, mockDataAccess.favorites.size());

        // check that salad is the only one remaining
        assertTrue(mockDataAccess.favorites.contains(salad));
        assertFalse(mockDataAccess.favorites.contains(pasta));
        assertFalse(mockDataAccess.favorites.contains(pizza));
    }

    @Test
    void testRemoveLastFavorite() {
        // create a single recipe and add it to favorites
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(recipe);

        RemoveFavoriteInputData inputData = new RemoveFavoriteInputData(recipe);

        interactor.execute(inputData);

        // check if success view was called
        assertTrue(mockPresenter.successCalled);

        // check that favorites list is empty
        assertEquals(0, mockDataAccess.favorites.size());

        // check that output data contains empty list
        assertNotNull(mockPresenter.outputData);
        assertEquals(0, mockPresenter.outputData.getRemainingFavorites().size());
    }

    @Test
    void testRemainingFavoritesAfterRemoval() {
        // create multiple recipes
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");
        Recipe salad = new Recipe("Salad", new ArrayList<>(), "Toss salad", "", "", "American");

        mockDataAccess.favorites.add(pasta);
        mockDataAccess.favorites.add(pizza);
        mockDataAccess.favorites.add(salad);

        // remove one recipe
        interactor.execute(new RemoveFavoriteInputData(pizza));

        // check that output data contains correct remaining favorites
        assertNotNull(mockPresenter.outputData);
        List<Recipe> remaining = mockPresenter.outputData.getRemainingFavorites();
        assertEquals(2, remaining.size());
        assertTrue(remaining.contains(pasta));
        assertTrue(remaining.contains(salad));
        assertFalse(remaining.contains(pizza));
    }

    /**
     * Mock implementation of FavoriteDataAccessInterface for testing.
     */
    private static class MockFavoriteDataAccess implements FavoriteDataAccessInterface {
        List<Recipe> favorites = new ArrayList<>();
        List<Recipe> removedRecipes = new ArrayList<>();

        @Override
        public void saveFavorite(Recipe recipe) {
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

        @Override
        public void removeFavorite(Recipe recipe) {
            removedRecipes.add(recipe);
            favorites.remove(recipe);
        }
    }

    /**
     * Mock implementation of RemoveFavoriteOutputBoundary for testing.
     */
    private static class MockPresenter implements RemoveFavoriteOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        String errorMessage = null;
        RemoveFavoriteOutputData outputData = null;

        @Override
        public void prepareSuccessView(RemoveFavoriteOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            failCalled = true;
            this.errorMessage = errorMessage;
        }

        void reset() {
            successCalled = false;
            failCalled = false;
            errorMessage = null;
            outputData = null;
        }
        
    }
}
