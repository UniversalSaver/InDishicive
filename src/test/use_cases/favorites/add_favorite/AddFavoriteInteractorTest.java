package use_cases.favorites.add_favorite;

import entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;
import logic.favorites.add_favorite.*;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;

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
    private MockRecipeDetailsGateway mockGateway;

    @BeforeEach
    void setUp() {
        mockDataAccess = new MockFavoriteDataAccess();
        mockPresenter = new MockPresenter();
        mockGateway = new MockRecipeDetailsGateway();
        interactor = new AddFavoriteInteractor(mockDataAccess, mockPresenter, mockGateway);
    }

    @Test
    void testAddNewFavoriteSuccess() {
        // create a new recipe
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);

        interactor.execute(inputData);

        // check if success view was called
        assertTrue(mockPresenter.successCalled);
        assertFalse(mockPresenter.failCalled);
        assertNull(mockPresenter.errorMessage);

        // chec if recipe was saved
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

        // check if fail view was called with correct message
        assertFalse(mockPresenter.successCalled);
        assertTrue(mockPresenter.failCalled);
        assertEquals("Already in Favorites!", mockPresenter.errorMessage);

        // check if recipe was not saved again
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

        // check if both recipes are saved
        assertEquals(2, mockDataAccess.savedRecipes.size());
        assertTrue(mockDataAccess.savedRecipes.contains(pasta));
        assertTrue(mockDataAccess.savedRecipes.contains(pizza));
    }

    /**
     * Mock implementation of FavoriteDataAccessInterface for testing.
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

    /**
     * Mock implementation of RecipeDetailsGateway for testing.
     */
    private static class MockRecipeDetailsGateway implements RecipeDetailsGateway {
        @Override
        public Recipe findByTitle(String title) {
            // Not needed for these tests that pass Recipe objects directly
            return null;
        }
    }
}
