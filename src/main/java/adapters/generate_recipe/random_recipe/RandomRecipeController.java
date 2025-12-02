package adapters.generate_recipe.random_recipe;

import logic.generate_recipe.random_recipe.RandomRecipeInputBoundary;

/**
 * A controller for the random recipe use case.
 */
public class RandomRecipeController {
    private final RandomRecipeInputBoundary interactor;

    public RandomRecipeController(RandomRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}