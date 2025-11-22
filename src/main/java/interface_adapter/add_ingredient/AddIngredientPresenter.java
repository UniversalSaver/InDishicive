package interface_adapter.add_ingredient;

import use_case.add_ingredient.AddIngredientOutputBoundary;

public class AddIngredientPresenter implements AddIngredientOutputBoundary {

    private final AddIngredientViewModel addIngredientViewModel;

    public AddIngredientPresenter(AddIngredientViewModel addIngredientViewModel) {
        this.addIngredientViewModel = addIngredientViewModel;
    }

    public void prepareSuccessView() {
        addIngredientViewModel.firePropertyChange("ingredientAdded");
    }
}

