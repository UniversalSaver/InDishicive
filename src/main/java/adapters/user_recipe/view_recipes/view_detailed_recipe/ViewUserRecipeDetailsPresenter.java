package adapters.user_recipe.view_recipes.view_detailed_recipe;

import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesState;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsOutputBoundary;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeOutputData;

/**
 * Creates a new window for the detailed user recipe, or tells the user something went wrong.
 */
public class ViewUserRecipeDetailsPresenter implements ViewUserRecipeDetailsOutputBoundary {

    private final UserRecipesViewModel userRecipesViewModel;
    private final UserRecipeDetailsViewModel userRecipeDetailsViewModel;

    public ViewUserRecipeDetailsPresenter(UserRecipesViewModel userRecipesViewModel,
                                          UserRecipeDetailsViewModel userRecipeDetailsViewModel) {
        this.userRecipesViewModel = userRecipesViewModel;
        this.userRecipeDetailsViewModel = userRecipeDetailsViewModel;
    }

    @Override
    public void presentSuccessView(ViewUserRecipeOutputData outputData) {
        final RecipeDetails recipeDetails = new RecipeDetails(
                outputData.getTitle(),
                outputData.getSteps(),
                outputData.getIngredientNames(),
                outputData.getIngredientAmounts());

        userRecipeDetailsViewModel.setState(recipeDetails);
        userRecipeDetailsViewModel.firePropertyChange(UserRecipeDetailsViewModel.SHOW_RECIPE_DETAILS);
    }

    @Override
    public void presentFailureView() {
        final ViewRecipesState viewState = new ViewRecipesState(null, 0,
                "Couldn't find recipe");

        userRecipesViewModel.setState(viewState);
        userRecipesViewModel.firePropertyChange(UserRecipesViewModel.DETAILS_ERROR_MESSAGE);
    }
}
