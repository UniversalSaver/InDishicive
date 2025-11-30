package logic.generate_recipe.random_recipe;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

import java.util.List;
import java.util.Optional;

public class RandomRecipeInteractor implements RandomRecipeInputBoundary {
    private final RandomRecipeGateway randomRecipeGateway;
    private final DietResDataAccessInterface dietResDataAccess;
    private final RandomRecipeOutputBoundary presenter;

    public RandomRecipeInteractor(RandomRecipeGateway randomRecipeGateway,
                                  DietResDataAccessInterface dietResDataAccess,
                                  RandomRecipeOutputBoundary presenter) {
        this.randomRecipeGateway = randomRecipeGateway;
        this.dietResDataAccess = dietResDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        // Business Logic: Try up to 5 times to get a recipe that doesn't violate restrictions
        List<Ingredient> restrictedIngredients = dietResDataAccess.getResIngredients();
        int maxRetries = 5;

        for (int i = 0; i < maxRetries; i++) {
            Optional<Recipe> recipeOpt = randomRecipeGateway.getRandomRecipe();

            if (recipeOpt.isPresent()) {
                Recipe recipe = recipeOpt.get();
                if (isSafeToEat(recipe, restrictedIngredients)) {
                    presenter.prepareSuccessView(recipe);
                    return;
                }
            }
        }
        presenter.prepareFailView("Could not find a recipe matching your restrictions after multiple attempts.");
    }

    private boolean isSafeToEat(Recipe recipe, List<Ingredient> restrictedList) {
        if (restrictedList.isEmpty()) return true;

        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            for (Ingredient restricted : restrictedList) {
                // Simple case-insensitive check
                if (recipeIngredient.getName().equalsIgnoreCase(restricted.getName())) {
                    return false;
                }
            }
        }
        return true;
    }
}