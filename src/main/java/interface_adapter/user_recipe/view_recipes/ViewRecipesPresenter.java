package interface_adapter.user_recipe.view_recipes;

import interface_adapter.*;
import use_case.user_recipe.view_recipes.ViewRecipesOutputBoundary;
import use_case.user_recipe.view_recipes.ViewRecipesOutputData;

import java.util.ArrayList;
import java.util.List;

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

        List<RecipeSummary> recipeSummaries = new ArrayList<>();

        for (ViewRecipesOutputData recipe : recipeInformation) {
            recipeSummaries.add(new RecipeSummary(recipe.getTitle(), recipe.getDescription()));
        }

        userRecipesViewModel.setState(new ViewRecipesState(recipeSummaries, recipeSummaries.size()));
        userRecipesViewModel.firePropertyChange(UserRecipesViewModel.SET_SUMMARIES);

        userRecipeWindowModel.firePropertyChange(UserRecipeWindowModel.SET_VISIBLE);

        userRecipesViewManagerModel.setState(UserRecipesViewModel.VIEW_NAME);
        userRecipesViewManagerModel.firePropertyChange(UserRecipesViewManagerModel.CHANGE_VIEW);
    }
}
