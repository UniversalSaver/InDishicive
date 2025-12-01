package logic.generate_recipe.generate_with_inventory;

import java.util.List;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

public class GenerateWithInventoryInteractor implements GenerateWithInventoryInputBoundary {

    private final InventoryReader inventoryReader;
    private final RecipeGateway recipeGateway;
    private final GenerateWithInventoryOutputBoundary presenter;
    private final DietResDataAccessInterface dietResDataAccessInterface;
    private final DietaryRestrictionCheckerInterface dietaryRestrictionCheckerInterface;

    public GenerateWithInventoryInteractor(InventoryReader inventoryReader,
                                           RecipeGateway recipeGateway,
                                           GenerateWithInventoryOutputBoundary presenter,
                                           DietResDataAccessInterface dietResDataAccessInterface,
                                           DietaryRestrictionCheckerInterface dietaryRestrictionCheckerInterface) {
        this.inventoryReader = inventoryReader;
        this.recipeGateway = recipeGateway;
        this.presenter = presenter;
        this.dietResDataAccessInterface = dietResDataAccessInterface;
        this.dietaryRestrictionCheckerInterface = dietaryRestrictionCheckerInterface;
    }

    /**
     * Executes the generate recipe with inventory use case.
     */
    public void execute() {
        // Get potential recipes based on inventory
        final List<Recipe> allRecipes = recipeGateway.findByInventory(inventoryReader.getAll());

        // Get the user's dietary restrictions
        final List<Ingredient> restrictions = dietResDataAccessInterface.getResIngredients();

        // Filter recipes using the DietaryRestrictionChecker
        final List<String> titles = allRecipes.stream()
                .filter(recipe -> !dietaryRestrictionCheckerInterface.containsRestrictedIngredient(recipe, restrictions))
                .map(Recipe::getTitle)
                .toList();

        presenter.present(new GenerateWithInventoryOutputData(titles));
    }
}
