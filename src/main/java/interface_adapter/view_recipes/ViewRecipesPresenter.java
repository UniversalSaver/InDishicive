package interface_adapter.view_recipes;

import interface_adapter.*;
import use_case.view_recipes.ViewRecipesOutputBoundary;

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

    public void prepareSuccessView() {

        userRecipeWindowModel.firePropertyChange(UserRecipeWindowModel.SET_VISIBLE);

        userRecipesViewManagerModel.setState(UserRecipesViewModel.VIEW_NAME);
        userRecipesViewManagerModel.firePropertyChange(UserRecipesViewManagerModel.CHANGE_VIEW);
    }
}
