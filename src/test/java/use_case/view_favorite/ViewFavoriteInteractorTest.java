package use_case.view_favorite;

import entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.favorite_recipes.FavoriteDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ViewFavoriteInteractor use case.
 * Tests the logic for retrieving and displaying favorite recipes.
 */
class ViewFavoriteInteractorTest {

    private ViewFavoriteInteractor interactor;
    private MockFavoriteDataAccess mockDataAccess;
    private MockPresenter mockPresenter;

    @BeforeEach
    void setUp() {
        mockDataAccess = new MockFavoriteDataAccess();
        mockPresenter = new MockPresenter();
        interactor = new ViewFavoriteInteractor(mockDataAccess, mockPresenter);
    }

    @Test
    void testExecuteWithEmptyFavorites() {
        // execute with no favorites
        interactor.execute();

        // check if empty view was prepared
        assertTrue(mockPresenter.emptyViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertNull(mockPresenter.outputData);
    }

    @Test
    void testExecuteWithSingleFavorite() {
        // add one favorite
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(recipe);

        interactor.execute();

        // check if success view was prepared with correct data
        assertTrue(mockPresenter.successViewCalled);
        assertFalse(mockPresenter.emptyViewCalled);
        assertNotNull(mockPresenter.outputData);
        assertEquals(1, mockPresenter.outputData.getFavoriteRecipes().size());
        assertEquals("Pasta", mockPresenter.outputData.getFavoriteRecipes().get(0).getTitle());
    }

    @Test
    void testExecuteWithMultipleFavorites() {
        // add multiple favorites
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");
        Recipe sushi = new Recipe("Sushi", new ArrayList<>(), "Roll sushi", "", "", "Japanese");

        mockDataAccess.favorites.add(pasta);
        mockDataAccess.favorites.add(pizza);
        mockDataAccess.favorites.add(sushi);

        interactor.execute();

        // check if success view was prepared with all recipes
        assertTrue(mockPresenter.successViewCalled);
        assertFalse(mockPresenter.emptyViewCalled);
        assertNotNull(mockPresenter.outputData);
        assertEquals(3, mockPresenter.outputData.getFavoriteRecipes().size());
    }

    @Test
    void testExecuteCalledMultipleTimes() {
        // first call with empty favorites
        interactor.execute();
        assertTrue(mockPresenter.emptyViewCalled);
        mockPresenter.reset();

        // add a favorite
        Recipe recipe = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(recipe);

        // second call with one favorite
        interactor.execute();
        assertTrue(mockPresenter.successViewCalled);
        assertFalse(mockPresenter.emptyViewCalled);
    }

    @Test
    void testExecuteRetrievesCurrentFavorites() {
        // add initial favorites
        Recipe pasta = new Recipe("Pasta", new ArrayList<>(), "Boil pasta", "", "", "Italian");
        mockDataAccess.favorites.add(pasta);

        interactor.execute();
        assertEquals(1, mockPresenter.outputData.getFavoriteRecipes().size());
        mockPresenter.reset();

        // add more favorites
        Recipe pizza = new Recipe("Pizza", new ArrayList<>(), "Bake pizza", "", "", "Italian");
        mockDataAccess.favorites.add(pizza);
        
        interactor.execute();
        assertEquals(2, mockPresenter.outputData.getFavoriteRecipes().size());
    }

    @Test
    void testOutputDataContainsCorrectRecipes() {
        // add favorites with specific data
        Recipe recipe = new Recipe(
            "Chicken Curry",
            new ArrayList<>(),
            "Cook chicken with curry",
            "http://example.com/curry.jpg",
            "http://youtube.com/watch?v=123",
            "Indian"
        );
        mockDataAccess.favorites.add(recipe);

        interactor.execute();

        // check if output data contains the correct recipe
        List<Recipe> recipes = mockPresenter.outputData.getFavoriteRecipes();
        assertEquals(1, recipes.size());
        Recipe returnedRecipe = recipes.get(0);
        assertEquals("Chicken Curry", returnedRecipe.getTitle());
        assertEquals("Indian", returnedRecipe.getCategory());
    }

    /**
     * Mock implementation of FavoriteDataAccessInterface for testing.
     */
    private static class MockFavoriteDataAccess implements FavoriteDataAccessInterface {
        List<Recipe> favorites = new ArrayList<>();

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
    }

    /**
     * Mock implementation of ViewFavoriteOutputBoundary for testing.
     */
    private static class MockPresenter implements ViewFavoriteOutputBoundary {
        boolean successViewCalled = false;
        boolean emptyViewCalled = false;
        ViewFavoriteOutputData outputData = null;

        @Override
        public void prepareSuccessView(ViewFavoriteOutputData outputData) {
            successViewCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareEmptyView() {
            emptyViewCalled = true;
        }

        void reset() {
            successViewCalled = false;
            emptyViewCalled = false;
            outputData = null;
        }
    }
}
