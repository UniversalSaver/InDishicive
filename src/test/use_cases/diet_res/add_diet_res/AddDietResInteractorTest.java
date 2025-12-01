package use_cases.diet_res.add_diet_res;

import entity.Ingredient;
import logic.dietary_restriction.add_restrictions.AddDietResInputData;
import logic.dietary_restriction.add_restrictions.AddDietResInteractor;
import logic.dietary_restriction.add_restrictions.AddDietResOutputBoundary;
import logic.dietary_restriction.add_restrictions.IngredientGateway;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddDietResInteractorTest {

    private MockDietResDataAccess mockDao;
    private MockAddDietResPresenter mockPresenter;
    private MockIngredientGateway mockGateway;
    private AddDietResInteractor interactor;

    @BeforeEach
    void setUp() {
        mockDao = new MockDietResDataAccess();
        mockPresenter = new MockAddDietResPresenter();
        mockGateway = new MockIngredientGateway();

        // Inject the mock gateway into the interactor
        interactor = new AddDietResInteractor(mockDao, mockPresenter, mockGateway);
    }

    @Test
    void successTest() {
        Ingredient ingredient = new Ingredient("Garlic", "");
        AddDietResInputData inputData = new AddDietResInputData(ingredient);

        interactor.execute(inputData);

        assertTrue(mockPresenter.isSuccessVerified());
        assertTrue(mockDao.isRestricted(ingredient));
    }

    @Test
    void failureInvalidIngredientTest() {
        mockGateway.setValid(false);
        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("FakeFood", ""));
        interactor.execute(inputData);

        assertEquals("Ingredient not found in database", mockPresenter.getErrorMessage());
        // Verify we didn't accidentally save it
        assertTrue(mockDao.getResIngredients().isEmpty());
    }

    @Test
    void failureDuplicateTest() {
        Ingredient existing = new Ingredient("Garlic", "");
        mockDao.saveRestriction(existing);

        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("Garlic", ""));
        interactor.execute(inputData);

        assertEquals("Already in Dietary Restricted Ingredients List", mockPresenter.getErrorMessage());
        // Verify database size didn't increase
        assertEquals(1, mockDao.getResIngredients().size());
    }

    @Test
    void failureDuplicateCaseInsensitiveTest() {
        // Add "Garlic" (Capitalized) to the DB
        Ingredient existing = new Ingredient("Garlic", "");
        mockDao.saveRestriction(existing);

        // Try to add "garlic" (Lowercase)
        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("garlic", ""));
        interactor.execute(inputData);

        // Should still fail as duplicate
        assertEquals("Already in Dietary Restricted Ingredients List", mockPresenter.getErrorMessage());

        // Ensure the duplicate is not saved accidentally
        assertEquals(1, mockDao.getResIngredients().size());
    }

    @Test
    void failureEmptyStringTest() {
        mockGateway.setValid(false);

        AddDietResInputData inputData = new AddDietResInputData(new Ingredient("", ""));
        interactor.execute(inputData);

        assertEquals("Ingredient not found in database", mockPresenter.getErrorMessage());
        assertFalse(mockPresenter.isSuccessVerified());
    }

    private static class MockIngredientGateway implements IngredientGateway {
        private boolean isValid = true;

        public void setValid(boolean valid) {
            this.isValid = valid;
        }

        @Override
        public boolean isValidIngredient(String ingredient) {
            if (ingredient == null || ingredient.isEmpty()) {
                return false;
            }
            return isValid;
        }
    }

    private static class MockDietResDataAccess implements DietResDataAccessInterface {
        private final List<Ingredient> ingredients = new ArrayList<>();

        @Override
        public void saveRestriction(Ingredient ingredient) { ingredients.add(ingredient); }

        @Override
        public boolean isRestricted(Ingredient ingredient) {
            return ingredients.stream().anyMatch(i -> i.getName().equalsIgnoreCase(ingredient.getName()));
        }

        @Override public void removeRestriction(Ingredient ingredient) { /* Doesn't need to return anything */ }
        @Override public List<Ingredient> getResIngredients() { return ingredients; }
    }

    private static class MockAddDietResPresenter implements AddDietResOutputBoundary {
        private boolean successVerified = false;
        private String errorMessage = null;

        @Override public void prepareSuccessView() { successVerified = true; }
        @Override public void prepareFailView(String error) { this.errorMessage = error; }
        public boolean isSuccessVerified() { return successVerified; }
        public String getErrorMessage() { return errorMessage; }
    }
}