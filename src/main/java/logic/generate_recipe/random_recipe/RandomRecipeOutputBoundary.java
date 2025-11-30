package logic.generate_recipe.random_recipe;

import entity.Recipe;

public interface RandomRecipeOutputBoundary {
    void prepareSuccessView(Recipe recipe);
    void prepareFailView(String error);
}