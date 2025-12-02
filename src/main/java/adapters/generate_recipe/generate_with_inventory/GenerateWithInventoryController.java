package adapters.generate_recipe.generate_with_inventory;

import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;

/**
 * Controller for the "generate with inventory" use case.
 */
public class GenerateWithInventoryController {

    private final GenerateWithInventoryInputBoundary interactor;


    public GenerateWithInventoryController(GenerateWithInventoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the use case.
     */
    public void execute() {
        interactor.execute();
    }
}
