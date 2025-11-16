package interface_adapter.generate_with_inventory;

import use_case.generate_with_inventory.GenerateWithInventoryInputBoundary;
import use_case.generate_with_inventory.GenerateWithInventoryInputData;

public class GenerateWithInventoryController {

    private final GenerateWithInventoryInputBoundary interactor;

    public GenerateWithInventoryController(GenerateWithInventoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        GenerateWithInventoryInputData inputData = new GenerateWithInventoryInputData();
        interactor.execute(inputData);
    }
}