package use_cases.generate_recipe.generate_with_inventory;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInteractor;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputData;
import logic.generate_recipe.generate_with_inventory.InventoryReader;
import logic.generate_recipe.generate_with_inventory.RecipeGateway;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GenerateWithInventoryInteractor.
 */
class GenerateWithInventoryInteractorTest {

    private static class RecordingInventoryReader implements InventoryReader {
        Set<String> toReturn = new LinkedHashSet<>();
        int callCount = 0;

        @Override
        public Set<String> getAll() {
            callCount++;
            return new LinkedHashSet<>(toReturn);
        }
    }

    private static class RecordingGateway implements RecipeGateway {
        List<Set<String>> calls = new ArrayList<>();
        List<Recipe> recipesToReturn = new ArrayList<>();

        @Override
        public List<Recipe> findByInventory(Set<String> have) {
            calls.add(new LinkedHashSet<>(have));
            return recipesToReturn;
        }
    }

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

    private static class RecordingChecker implements DietaryRestrictionCheckerInterface {
        List<Recipe> checkedRecipes = new ArrayList<>();
        List<List<Ingredient>> checkedRestrictions = new ArrayList<>();
        Set<String> restrictedTitles = new HashSet<>();

        @Override
        public boolean containsRestrictedIngredient(Recipe recipe, List<Ingredient> restrictions) {
            checkedRecipes.add(recipe);
            checkedRestrictions.add(restrictions == null ? List.of() : new ArrayList<>(restrictions));
            return restrictedTitles.contains(recipe.getTitle());
        }
    }

    private static class RecordingPresenter implements GenerateWithInventoryOutputBoundary {
        GenerateWithInventoryOutputData lastOutput;
        int callCount = 0;

        @Override
        public void present(GenerateWithInventoryOutputData outputData) {
            lastOutput = outputData;
            callCount++;
        }
    }


    @Test
    void execute_noRecipesFromGateway_presentsEmptyList() {
        RecordingInventoryReader inventoryReader = new RecordingInventoryReader();
        inventoryReader.toReturn = new LinkedHashSet<>(List.of("Cheese", "Tomato"));

        RecordingGateway gateway = new RecordingGateway();
        gateway.recipesToReturn = List.of(); // no recipes

        RecordingDietResDAO dietResDAO = new RecordingDietResDAO();
        dietResDAO.setRestrictions(List.of()); // no restrictions

        RecordingChecker checker = new RecordingChecker();
        RecordingPresenter presenter = new RecordingPresenter();

        GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(
                        inventoryReader,
                        gateway,
                        presenter,
                        dietResDAO,
                        checker
                );

        interactor.execute();

        // Inventory was read once
        assertEquals(1, inventoryReader.callCount);

        // Gateway called once with the same set
        assertEquals(1, gateway.calls.size());
        assertEquals(inventoryReader.toReturn, gateway.calls.get(0));

        // DAO asked once for restrictions
        assertEquals(1, dietResDAO.getResIngredientsCallCount);

        // Checker never called (no recipes)
        assertTrue(checker.checkedRecipes.isEmpty());

        // Presenter called once with empty titles
        assertEquals(1, presenter.callCount);
        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.getRecipeTitles().isEmpty());
    }

    @Test
    void execute_withRecipesAndNoRestrictions_presentsAllTitles() {
        RecordingInventoryReader inventoryReader = new RecordingInventoryReader();
        inventoryReader.toReturn = new LinkedHashSet<>(List.of("Eggs", "Flour"));

        RecordingGateway gateway = new RecordingGateway();
        gateway.recipesToReturn = List.of(
                new Recipe("Pancakes", List.of(), "", "", "", ""),
                new Recipe("Omelette", List.of(), "", "", "", "")
        );

        RecordingDietResDAO dietResDAO = new RecordingDietResDAO();
        dietResDAO.setRestrictions(List.of()); // no restrictions

        RecordingChecker checker = new RecordingChecker();

        RecordingPresenter presenter = new RecordingPresenter();

        GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(
                        inventoryReader,
                        gateway,
                        presenter,
                        dietResDAO,
                        checker
                );

        interactor.execute();

        assertEquals(1, gateway.calls.size());
        assertEquals(inventoryReader.toReturn, gateway.calls.get(0));

        assertEquals(1, dietResDAO.getResIngredientsCallCount);

        assertEquals(2, checker.checkedRecipes.size());
        assertEquals("Pancakes", checker.checkedRecipes.get(0).getTitle());
        assertEquals("Omelette", checker.checkedRecipes.get(1).getTitle());

        assertEquals(1, presenter.callCount);
        List<String> titles = presenter.lastOutput.getRecipeTitles();
        assertEquals(List.of("Pancakes", "Omelette"), titles);
    }

}