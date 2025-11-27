package use_cases.remove_diet_res;

import entity.Ingredient;
import org.junit.jupiter.api.Test;
import use_case.diet_res_ingredients.DietResDataAccessInterface;
import use_case.remove_restriction.RemoveDietResInputData;
import use_case.remove_restriction.RemoveDietResInteractor;
import use_case.remove_restriction.RemoveDietResOutputBoundary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemoveDietResInteractorTest {

    @Test
    void successRemoveTest() {
        MockDietResDataAccess mockDao = new MockDietResDataAccess();
        mockDao.saveRestriction(new Ingredient("Peanuts", ""));

        MockRemovePresenter mockPresenter = new MockRemovePresenter();

        RemoveDietResInteractor interactor = new RemoveDietResInteractor(mockDao, mockPresenter);
        RemoveDietResInputData inputData = new RemoveDietResInputData(new Ingredient("Peanuts", ""));

        interactor.execute(inputData);

        assertTrue(mockPresenter.isSuccessVerified());
        assertFalse(mockDao.isRestricted(new Ingredient("Peanuts", "")));
    }

    @Test
    void failRemoveNotFoundTest() {
        MockDietResDataAccess mockDao = new MockDietResDataAccess();
        MockRemovePresenter mockPresenter = new MockRemovePresenter();

        RemoveDietResInteractor interactor = new RemoveDietResInteractor(mockDao, mockPresenter);
        RemoveDietResInputData inputData = new RemoveDietResInputData(new Ingredient("Gold", ""));

        interactor.execute(inputData);

        assertFalse(mockPresenter.isSuccessVerified());
        assertEquals("Ingredient not found in restrictions list.", mockPresenter.getErrorMessage());
    }

    private static class MockDietResDataAccess implements DietResDataAccessInterface {
        private final List<Ingredient> ingredients = new ArrayList<>();

        @Override
        public void saveRestriction(Ingredient ingredient) {
            ingredients.add(ingredient);
        }

        @Override
        public void removeRestriction(Ingredient ingredient) {
            ingredients.removeIf(i -> i.getName().equalsIgnoreCase(ingredient.getName()));
        }

        @Override
        public List<Ingredient> getResIngredients() {
            return ingredients;
        }

        @Override
        public boolean isRestricted(Ingredient ingredient) {
            return ingredients.stream()
                    .anyMatch(i -> i.getName().equalsIgnoreCase(ingredient.getName()));
        }
    }

    private static class MockRemovePresenter implements RemoveDietResOutputBoundary {
        private boolean successVerified = false;
        private String errorMessage = null;

        @Override
        public void prepareSuccessView() {
            successVerified = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public boolean isSuccessVerified() {
            return successVerified;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}