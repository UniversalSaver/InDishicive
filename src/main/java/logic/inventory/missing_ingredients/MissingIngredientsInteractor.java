package logic.inventory.missing_ingredients;

import java.util.ArrayList;
import java.util.List;

import entity.Ingredient;

/**
 * Interactor for the missing ingredients use case.
 */
public class MissingIngredientsInteractor implements MissingIngredientsInputBoundary {

    private final InventoryReaderInterface inventoryReader;
    private final MissingIngredientsOutputBoundary presenter;

    public MissingIngredientsInteractor(InventoryReaderInterface inventoryReader,
                                        MissingIngredientsOutputBoundary presenter) {
        this.inventoryReader = inventoryReader;
        this.presenter = presenter;
    }

    @Override
    public void execute(List<String> recipeIngredients) {
        final List<Ingredient> availableIngredients = inventoryReader.getAllIngredients();

        final List<String> availableNames = new ArrayList<>();
        for (Ingredient ingredient : availableIngredients) {
            availableNames.add(ingredient.getName().toLowerCase());
        }

        final List<String> missing = new ArrayList<>();
        for (String recipeIngredient : recipeIngredients) {
            final String ingredientName = extractIngredientName(recipeIngredient);
            if (!availableNames.contains(ingredientName.toLowerCase())) {
                missing.add(recipeIngredient);
            }
        }

        if (missing.isEmpty()) {
            presenter.presentAllIngredientsAvailable();
        }
        else {
            presenter.presentMissingIngredients(missing);
        }
    }

    private String extractIngredientName(String recipeIngredient) {
        final String[] parts = recipeIngredient.split("-");
        return parts[0].trim();
    }
}
