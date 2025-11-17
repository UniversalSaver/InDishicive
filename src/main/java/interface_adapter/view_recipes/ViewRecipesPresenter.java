package interface_adapter.view_recipes;

import interface_adapter.*;
import use_case.view_recipes.ViewRecipesOutputBoundary;
import window.UserRecipesWindow;

public class ViewRecipesPresenter implements ViewRecipesOutputBoundary {

    private final ViewRecipesViewModel viewRecipesViewModel;
    private final UserRecipesViewManagerModel userRecipesViewManagerModel;

    public ViewRecipesPresenter(ViewRecipesViewModel viewRecipesViewModel,
                                UserRecipesViewManagerModel userRecipesViewManagerModel) {
        this.viewRecipesViewModel = viewRecipesViewModel;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;
    }

    public void prepareSuccessView() {
        viewRecipesViewModel.firePropertyChange(UserRecipesWindow.SET_VISIBLE);
    }
}
