package logic.generate_recipe.generate_with_inventory;

public interface GenerateWithInventoryOutputBoundary {
    /**
     * Prepares the view for the generated recipes.
     * @param outputData the output data containing the list of recipe titles that match the inventory
     *                   and pass dietary restriction checks.
     */
    void present(GenerateWithInventoryOutputData outputData);
}
