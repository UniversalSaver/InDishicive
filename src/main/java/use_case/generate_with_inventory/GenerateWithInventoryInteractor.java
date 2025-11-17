package use_case.generate_with_inventory;

import entity.Recipe;

import java.util.List;

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

    public void execute() {
        List<String> titles = recipeGateway.findByInventory(inventoryReader.getAll())
                .stream()
                .map(Recipe::getTitle)
                .toList();

        presenter.present(new GenerateWithInventoryOutputData(titles));
    }
}