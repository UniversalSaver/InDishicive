package use_case.missing_ingredients;

import entity.Ingredient;
import logic.inventory.missing_ingredients.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissingIngredientsInteractorTest {

    @Test
    void allIngredientsAvailableTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        mockReader.addIngredient(new Ingredient("Milk", "1 gallon"));
        mockReader.addIngredient(new Ingredient("Eggs", "12"));
        mockReader.addIngredient(new Ingredient("Flour", "2 cups"));

        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(Arrays.asList("Milk - 1 cup", "Eggs - 2"));

        assertTrue(mockPresenter.isAllAvailableCalled());
        assertFalse(mockPresenter.isMissingCalled());
    }

    @Test
    void someMissingIngredientsTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        mockReader.addIngredient(new Ingredient("Milk", "1 gallon"));
        mockReader.addIngredient(new Ingredient("Eggs", "12"));

        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(Arrays.asList("Milk - 1 cup", "Flour - 2 cups", "Sugar - 1 cup"));

        assertFalse(mockPresenter.isAllAvailableCalled());
        assertTrue(mockPresenter.isMissingCalled());
        assertEquals(2, mockPresenter.getMissingIngredients().size());
        assertTrue(mockPresenter.getMissingIngredients().contains("Flour - 2 cups"));
        assertTrue(mockPresenter.getMissingIngredients().contains("Sugar - 1 cup"));
    }

    @Test
    void allMissingIngredientsTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(Arrays.asList("Milk - 1 cup", "Eggs - 2"));

        assertFalse(mockPresenter.isAllAvailableCalled());
        assertTrue(mockPresenter.isMissingCalled());
        assertEquals(2, mockPresenter.getMissingIngredients().size());
    }

    @Test
    void caseInsensitiveMatchTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        mockReader.addIngredient(new Ingredient("MILK", "1 gallon"));
        mockReader.addIngredient(new Ingredient("eggs", "12"));

        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(Arrays.asList("milk - 1 cup", "EGGS - 2"));

        assertTrue(mockPresenter.isAllAvailableCalled());
        assertFalse(mockPresenter.isMissingCalled());
    }

    @Test
    void emptyRecipeIngredientsTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        mockReader.addIngredient(new Ingredient("Milk", "1 gallon"));

        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(new ArrayList<>());

        assertTrue(mockPresenter.isAllAvailableCalled());
        assertFalse(mockPresenter.isMissingCalled());
    }

    @Test
    void ingredientNameExtractionTest() {
        MockInventoryReader mockReader = new MockInventoryReader();
        mockReader.addIngredient(new Ingredient("Chicken Breast", "2 lbs"));

        MockPresenter mockPresenter = new MockPresenter();
        MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(mockReader, mockPresenter);

        interactor.execute(Arrays.asList("Chicken Breast - 1 lb"));

        assertTrue(mockPresenter.isAllAvailableCalled());
        assertFalse(mockPresenter.isMissingCalled());
    }

    private static class MockInventoryReader implements InventoryReaderInterface {
        private final List<Ingredient> ingredients = new ArrayList<>();

        public void addIngredient(Ingredient ingredient) {
            ingredients.add(ingredient);
        }

        @Override
        public List<Ingredient> getAllIngredients() {
            return new ArrayList<>(ingredients);
        }
    }

    private static class MockPresenter implements MissingIngredientsOutputBoundary {
        private boolean allAvailableCalled = false;
        private boolean missingCalled = false;
        private List<String> missingIngredients = null;

        @Override
        public void presentMissingIngredients(List<String> missingIngredients) {
            this.missingCalled = true;
            this.missingIngredients = missingIngredients;
        }

        @Override
        public void presentAllIngredientsAvailable() {
            this.allAvailableCalled = true;
        }

        public boolean isAllAvailableCalled() {
            return allAvailableCalled;
        }

        public boolean isMissingCalled() {
            return missingCalled;
        }

        public List<String> getMissingIngredients() {
            return missingIngredients;
        }
    }
}
