package logic.generate_recipe.generate_with_inventory;

import java.util.List;

import entity.Recipe;

/**
 * Interactor for generating recipes based on inventory.
 */
public class GenerateWithInventoryInteractor implements GenerateWithInventoryInputBoundary {

    private final InventoryReader inventoryReader;
    private final RecipeGateway recipeGateway;
    private final GenerateWithInventoryOutputBoundary presenter;

    public GenerateWithInventoryInteractor(InventoryReader inventoryReader,
                                           RecipeGateway recipeGateway,
                                           GenerateWithInventoryOutputBoundary presenter) {
        this.inventoryReader = inventoryReader;
        this.recipeGateway = recipeGateway;
        this.presenter = presenter;
    }

    /**
     * Executes the use case to generate recipes based on inventory.
     */
    public void execute() {
        final List<String> titles = recipeGateway.findByInventory(inventoryReader.getAll())
                .stream()
                .map(Recipe::getTitle)
                .toList();

        presenter.present(new GenerateWithInventoryOutputData(titles));
    }
}
