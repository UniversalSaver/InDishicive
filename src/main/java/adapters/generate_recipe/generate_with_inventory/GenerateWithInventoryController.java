package adapters.generate_recipe.generate_with_inventory;

import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;

public class GenerateWithInventoryController {

    private final GenerateWithInventoryInputBoundary interactor;


    public GenerateWithInventoryController(GenerateWithInventoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
