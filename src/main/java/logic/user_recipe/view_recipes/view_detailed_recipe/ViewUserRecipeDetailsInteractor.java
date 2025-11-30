package logic.user_recipe.view_recipes.view_detailed_recipe;

import entity.Ingredient;
import entity.UserRecipe;
import logic.user_recipe.view_recipes.ViewRecipesInputBoundary;

/**
 * A concrete interactor.
 * Finds the user recipe given a title, then calls a presenter to show it to the user.
 */
public class ViewUserRecipeDetailsInteractor implements ViewUserRecipeDetailsInputBoundary {
    private final ViewUserRecipeDetailsDataAccessInterface viewUserRecipeDetailsDataAccess;
    private final ViewUserRecipeDetailsOutputBoundary viewUserRecipeDetailsPresenter;
    private final ViewRecipesInputBoundary viewRecipesInteractor;

    public ViewUserRecipeDetailsInteractor(ViewUserRecipeDetailsDataAccessInterface viewUserRecipeDetailsDataAccess,
                                           ViewUserRecipeDetailsOutputBoundary viewUserRecipeDetailsPresenter,
                                           ViewRecipesInputBoundary viewRecipesInteractor) {
        this.viewUserRecipeDetailsDataAccess = viewUserRecipeDetailsDataAccess;
        this.viewUserRecipeDetailsPresenter = viewUserRecipeDetailsPresenter;
        this.viewRecipesInteractor = viewRecipesInteractor;
    }

    @Override
    public void execute(ViewUserRecipeDetailsInputData inputData) {
        final UserRecipe gottenRecipe = viewUserRecipeDetailsDataAccess.getRecipeByTitle(inputData.getTitle());

        if (gottenRecipe == null) {
            // To refresh the data in the summaries.
            viewRecipesInteractor.execute();

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
