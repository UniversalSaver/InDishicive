package logic.user_recipe.view_recipes.view_detailed_recipe;

import entity.Ingredient;
import entity.UserRecipe;

/**
 * A concrete interactor.
 * Finds the user recipe given a title, then calls a presenter to show it to the user.
 */
public class ViewUserRecipeDetailsInteractor implements ViewUserRecipeDetailsInputBoundary {
    private final ViewUserRecipeDetailsDataAccessInterface viewUserRecipeDetailsDataAccess;
    private final ViewUserRecipeDetailsOutputBoundary viewUserRecipeDetailsPresenter;

    public ViewUserRecipeDetailsInteractor(ViewUserRecipeDetailsDataAccessInterface viewUserRecipeDetailsDataAccess,
                                           ViewUserRecipeDetailsOutputBoundary viewUserRecipeDetailsPresenter) {
        this.viewUserRecipeDetailsDataAccess = viewUserRecipeDetailsDataAccess;
        this.viewUserRecipeDetailsPresenter = viewUserRecipeDetailsPresenter;
    }

    @Override
    public void execute(ViewUserRecipeDetailsInputData inputData) {
        final UserRecipe gottenRecipe = viewUserRecipeDetailsDataAccess.getRecipeByTitle(inputData.getTitle());

        if (gottenRecipe == null) {
            viewUserRecipeDetailsPresenter.presentFailureView();
        } else {
            viewUserRecipeDetailsPresenter.presentSuccessView(createOutputData(gottenRecipe));
        }
    }

    private static ViewUserRecipeOutputData createOutputData(UserRecipe gottenRecipe) {
        final OutputDataBuilder builder = new OutputDataBuilder();

        builder.addTitle(gottenRecipe.getTitle())
                .addSteps(gottenRecipe.getSteps());

        for (Ingredient ingredient : gottenRecipe.getIngredients()) {
            builder.addIngredient(ingredient);
        }

        return builder.build();
    }
}
