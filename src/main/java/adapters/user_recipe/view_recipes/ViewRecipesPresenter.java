package adapters.user_recipe.view_recipes;

import java.util.ArrayList;
import java.util.List;

import adapters.UserRecipesViewManagerModel;
import logic.user_recipe.view_recipes.ViewRecipesOutputBoundary;
import logic.user_recipe.view_recipes.ViewRecipesOutputData;

/**
 * Given a list of recipe data to show the user, changes the view model to show their created recipes.
 */
public class ViewRecipesPresenter implements ViewRecipesOutputBoundary {

    private final UserRecipeWindowModel userRecipeWindowModel;
    private final UserRecipesViewManagerModel userRecipesViewManagerModel;
    private final UserRecipesViewModel userRecipesViewModel;

    public ViewRecipesPresenter(UserRecipeWindowModel userRecipeWindowModel,
                                UserRecipesViewManagerModel userRecipesViewManagerModel,
                                UserRecipesViewModel userRecipesViewModel) {
        this.userRecipeWindowModel = userRecipeWindowModel;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;
        this.userRecipesViewModel = userRecipesViewModel;
    }

    @Override
    public void prepareSuccessView(List<ViewRecipesOutputData> recipeInformation) {

        final List<RecipeSummary> recipeSummaries = new ArrayList<>();

        for (ViewRecipesOutputData recipe : recipeInformation) {
            recipeSummaries.add(new RecipeSummary(recipe.getTitle(), recipe.getDescription()));
        }

        userRecipesViewModel.setState(new ViewRecipesState(recipeSummaries, recipeSummaries.size(), null));
        userRecipesViewModel.firePropertyChange(UserRecipesViewModel.SET_SUMMARIES);

        userRecipeWindowModel.firePropertyChange(UserRecipeWindowModel.SET_VISIBLE);

        userRecipesViewManagerModel.setState(UserRecipesViewModel.VIEW_NAME);
        userRecipesViewManagerModel.firePropertyChange(UserRecipesViewManagerModel.CHANGE_VIEW);
    }
}
