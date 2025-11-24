package use_cases.generate_with_inventory;

import entity.Recipe;
import org.junit.jupiter.api.Test;
import use_case.generate_with_inventory.GenerateWithInventoryInputBoundary;
import use_case.generate_with_inventory.GenerateWithInventoryInteractor;
import use_case.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import use_case.generate_with_inventory.GenerateWithInventoryOutputData;
import use_case.generate_with_inventory.InventoryReader;
import use_case.generate_with_inventory.RecipeGateway;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GenerateWithInventoryInteractorTest {

    @Test
    void emptyInventory_noRecipes_presentsEmptyList() {
        InventoryReader inventoryReader = new InventoryReader() {
            @Override
            public Set<String> getAll() {
                return Collections.emptySet();
            }
        };

        RecipeGateway recipeGateway = new RecipeGateway() {
            @Override
            public List<Recipe> findByInventory(Set<String> have) {
                assertTrue(have.isEmpty());
                return Collections.emptyList();
            }
        };

        List<List<String>> capturedTitlesCalls = new ArrayList<>();

        GenerateWithInventoryOutputBoundary presenter =
                new GenerateWithInventoryOutputBoundary() {
                    @Override
                    public void present(GenerateWithInventoryOutputData outputData) {
                        capturedTitlesCalls.add(outputData.getRecipeTitles());
                    }
                };

        GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(inventoryReader, recipeGateway, presenter);

        interactor.execute();
        assertEquals(1, capturedTitlesCalls.size());
        assertTrue(capturedTitlesCalls.get(0).isEmpty());
    }

    @Test
    void successFlow_passesInventoryAndPresentsTitlesInOrder() {
        Set<String> inventorySet = new LinkedHashSet<>(List.of("Chicken", "Rice"));

        InventoryReader inventoryReader = new InventoryReader() {
            @Override
            public Set<String> getAll() {
                return inventorySet;
            }
        };

        List<Set<String>> capturedHaveCalls = new ArrayList<>();

        RecipeGateway recipeGateway = new RecipeGateway() {
            @Override
            public List<Recipe> findByInventory(Set<String> have) {
                capturedHaveCalls.add(new LinkedHashSet<>(have));

                return List.of(
                        new Recipe("A",
                                List.of(), "", "", "", ""),
                        new Recipe("B",
                                List.of(), "", "", "", "")
                );
            }
        };

        List<String> presentedTitles = new ArrayList<>();

        GenerateWithInventoryOutputBoundary presenter =
                new GenerateWithInventoryOutputBoundary() {
                    @Override
                    public void present(GenerateWithInventoryOutputData outputData) {
                        presentedTitles.addAll(outputData.getRecipeTitles());
                    }
                };

        GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(inventoryReader, recipeGateway, presenter);

        interactor.execute();

        assertEquals(1, capturedHaveCalls.size());
        assertEquals(inventorySet, capturedHaveCalls.get(0));

        assertEquals(List.of("A", "B"), presentedTitles);
    }
}