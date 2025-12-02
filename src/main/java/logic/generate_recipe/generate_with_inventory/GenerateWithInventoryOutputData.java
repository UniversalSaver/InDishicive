package logic.generate_recipe.generate_with_inventory;

import java.util.List;

/**
 * Output data for the generate with inventory use case.
 */
public class GenerateWithInventoryOutputData {
    private final List<String> recipeTitles;

    public GenerateWithInventoryOutputData(List<String> recipeTitles) {
        this.recipeTitles = recipeTitles;
    }

    public List<String> getRecipeTitles() {
        return recipeTitles;
    }
}
