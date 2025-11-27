package use_cases.add_diet_res;

import entity.Ingredient;
import org.junit.jupiter.api.Test;
import use_case.dietary_restriction.add_restrictions.AddDietResInputData;
import use_case.dietary_restriction.add_restrictions.AddDietResInteractor;
import use_case.dietary_restriction.add_restrictions.AddDietResOutputBoundary;
import use_case.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddDietResInteractorTest {

    @Test
    void successTest() {
        DietResDataAccessInterface mockDao = new MockDietResDataAccess();
        MockAddDietResPresenter mockPresenter = new MockAddDietResPresenter();
        AddDietResInteractor interactor = new AddDietResInteractor(mockDao, mockPresenter);

        Ingredient ingredient = new Ingredient("Garlic", "");
        AddDietResInputData inputData = new AddDietResInputData(ingredient);

        interactor.execute(inputData);

        assertTrue(mockPresenter.isSuccessVerified());
        assertTrue(mockDao.isRestricted(ingredient));
    }

    @Test
    void failureDuplicateTest() {
        DietResDataAccessInterface mockDao = new MockDietResDataAccess();
        Ingredient existing = new Ingredient("Garlic", "");
        mockDao.saveRestriction(existing);

        MockAddDietResPresenter mockPresenter = new MockAddDietResPresenter();
        AddDietResInteractor interactor = new AddDietResInteractor(mockDao, mockPresenter);
        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("Garlic", ""));

        interactor.execute(inputData);

        assertEquals("Already in Dietary Restricted Ingredients List", mockPresenter.getErrorMessage());
    }

    @Test
    void failureInvalidIngredientTest() {
        DietResDataAccessInterface mockDao = new MockDietResDataAccess();
        MockAddDietResPresenter mockPresenter = new MockAddDietResPresenter();
        AddDietResInteractor interactor = new AddDietResInteractor(mockDao, mockPresenter);

        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("NotARealIngredient123", ""));
        interactor.execute(inputData);

        assertEquals("Ingredient not found in database", mockPresenter.getErrorMessage());
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

    private static class MockAddDietResPresenter implements AddDietResOutputBoundary {
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