package adapters.generate_recipe.random_recipe;

import logic.generate_recipe.random_recipe.RandomRecipeInputBoundary;

public class RandomRecipeController {
    private final RandomRecipeInputBoundary interactor;

    public RandomRecipeController(RandomRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}