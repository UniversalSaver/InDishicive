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
        List<Ingredient> restrictedIngredients = dietResDataAccess.getResIngredients();
        int maxRetries = 5;

        boolean recipeFound = findAndPresentSafeRecipe(restrictedIngredients, maxRetries);

        if (!recipeFound) {
            presenter.prepareFailView("Could not find a recipe matching your restrictions after multiple attempts.");
        }
    }

    private boolean findAndPresentSafeRecipe(List<Ingredient> restrictedIngredients, int maxRetries) {
        boolean found = false;
        for (int i = 0; i < maxRetries && !found; i++) {
            Optional<Recipe> recipeOpt = randomRecipeGateway.getRandomRecipe();

            if (recipeOpt.isPresent()) {
                Recipe recipe = recipeOpt.get();
                if (isSafeToEat(recipe, restrictedIngredients)) {
                    presenter.prepareSuccessView(recipe);
                    found = true;
                }
            }
        }
        return found;
    }

    private boolean isSafeToEat(Recipe recipe, List<Ingredient> restrictedList) {
        if (restrictedList.isEmpty()) return true;

        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            for (Ingredient restricted : restrictedList) {
                if (recipeIngredient.getName().equalsIgnoreCase(restricted.getName())) {
                    return false;
                }
            }
        }
        return true;
    }
}