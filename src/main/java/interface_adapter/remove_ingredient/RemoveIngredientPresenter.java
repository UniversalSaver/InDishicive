package interface_adapter.remove_ingredient;

import use_case.remove_ingredient.RemoveIngredientOutputBoundary;

public class RemoveIngredientPresenter implements RemoveIngredientOutputBoundary {

    private final RemoveIngredientViewModel removeIngredientViewModel;

    public RemoveIngredientPresenter(RemoveIngredientViewModel removeIngredientViewModel) {
        this.removeIngredientViewModel = removeIngredientViewModel;
    }

    public void prepareSuccessView() {
        removeIngredientViewModel.firePropertyChange("ingredientRemoved");
    }
}

