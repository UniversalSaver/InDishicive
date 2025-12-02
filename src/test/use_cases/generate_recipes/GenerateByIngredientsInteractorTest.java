package use_cases.generate_recipe.generate_by_ingredients;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputData;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInteractor;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputData;
import logic.generate_recipe.generate_by_ingredients.RecipeByIngredientsGateway;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GenerateByIngredientsInteractor.
 */
class GenerateByIngredientsInteractorTest {


    private static class RecordingGateway implements RecipeByIngredientsGateway {

        List<List<String>> calls = new ArrayList<>();
        List<Recipe> recipesToReturn = new ArrayList<>();

        @Override
        public List<Recipe> findByIngredients(List<String> ingredients) {
            calls.add(new ArrayList<>(ingredients));
            return recipesToReturn;
        }
    }

    /**
     * Presenter record last output
     */
    private static class RecordingPresenter implements GenerateByIngredientsOutputBoundary {

        GenerateByIngredientsOutputData lastOutput;
        int callCount = 0;

        @Override
        public void present(GenerateByIngredientsOutputData outputData) {
            this.lastOutput = outputData;
            this.callCount++;
        }
    }

    /**
     * Fake DietRes DAO
     */
    private static class RecordingDietResDAO implements DietResDataAccessInterface {

        private final List<Ingredient> restrictions = new ArrayList<>();
        int getResIngredientsCallCount = 0;

        void setRestrictions(List<Ingredient> list) {
            restrictions.clear();
            if (list != null) {
                restrictions.addAll(list);
            }
        }

        @Override
        public void saveRestriction(Ingredient ingredient) {
            if (ingredient != null && !restrictions.contains(ingredient)) {
                restrictions.add(ingredient);
            }
        }

        @Override
        public void removeRestriction(Ingredient ingredient) {
            restrictions.remove(ingredient);
        }

        @Override
        public List<Ingredient> getResIngredients() {
            getResIngredientsCallCount++;
            return new ArrayList<>(restrictions);
        }

        @Override
        public boolean isRestricted(Ingredient ingredient) {
            return restrictions.contains(ingredient);
        }
    }

    @Test
    void execute_nullInputData_callsGatewayWithEmptyList_andPresentsEmptyTitles() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        RecordingDietResDAO dietResDAO = new RecordingDietResDAO();

        dietResDAO.setRestrictions(List.of());

        GenerateByIngredientsInputBoundary interactor =
                new GenerateByIngredientsInteractor(gateway, presenter, dietResDAO);

        interactor.execute(null);

        assertEquals(1, gateway.calls.size());
        assertTrue(gateway.calls.get(0).isEmpty());

        assertEquals(1, dietResDAO.getResIngredientsCallCount);

        assertEquals(1, presenter.callCount);
        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.getRecipeTitles().isEmpty());
    }

    @Test
    void execute_cleansInput_trimsAndDeduplicatesAndPassesToGateway_andPresentsAllTitlesWhenNoRestrictions() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        RecordingDietResDAO dietResDAO = new RecordingDietResDAO();

        dietResDAO.setRestrictions(List.of());

        gateway.recipesToReturn = List.of(
                new Recipe("R1", List.of(), "", "", "", ""),
                new Recipe("R2", List.of(), "", "", "", "")
        );

        GenerateByIngredientsInputBoundary interactor =
                new GenerateByIngredientsInteractor(gateway, presenter, dietResDAO);

        List<String> raw = Arrays.asList(
                " chicken ",
                "",
                null,
                "chicken",
                "  beef"
        );
        GenerateByIngredientsInputData inputData =
                new GenerateByIngredientsInputData(raw);

        interactor.execute(inputData);

        assertEquals(1, gateway.calls.size());
        List<String> passedToGateway = gateway.calls.get(0);
        assertEquals(List.of("chicken", "beef"), passedToGateway);

        assertEquals(1, presenter.callCount);
        assertNotNull(presenter.lastOutput);
        assertEquals(List.of("R1", "R2"),
                presenter.lastOutput.getRecipeTitles());
    }
}