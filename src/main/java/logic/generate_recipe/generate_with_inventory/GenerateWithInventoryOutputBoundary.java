package logic.generate_recipe.generate_with_inventory;

/**
 * Output boundary for generating recipes with inventory.
 */
public interface GenerateWithInventoryOutputBoundary {
    /**
     * Presents the output data.
     * @param outputData the output data to present
     */
    void present(GenerateWithInventoryOutputData outputData);
}
