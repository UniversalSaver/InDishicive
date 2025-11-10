package interface_adapter.view_recipes;

import interface_adapter.ViewModel;
import use_case.view_recipes.ViewRecipesOutputBoundary;
import window.UserRecipesWindow;

public class ViewRecipesPresenter implements ViewRecipesOutputBoundary {

    private final UserRecipesWindow userRecipesWindow;
    private final ViewRecipesViewModel viewRecipesViewModel;

    public ViewRecipesPresenter(UserRecipesWindow userRecipesWindow, ViewRecipesViewModel viewRecipesViewModel) {
        this.userRecipesWindow = userRecipesWindow;
        this.viewRecipesViewModel = viewRecipesViewModel;
    }

    public void prepareSuccessView() {
        viewRecipesViewModel.firePropertyChange("set visible");
    }
}
