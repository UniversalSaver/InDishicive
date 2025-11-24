package interface_adapter.add_ingredient;

import use_case.add_ingredient.AddIngredientOutputBoundary;

/**
 * Presenter for the Add Ingredient use case.
 * Notifies the view when an ingredient has been successfully added.
 */
public class AddIngredientPresenter implements AddIngredientOutputBoundary {

    private final AddIngredientViewModel addIngredientViewModel;

    public AddIngredientPresenter(AddIngredientViewModel addIngredientViewModel) {
        this.addIngredientViewModel = addIngredientViewModel;
    }

    /**
     * Prepares the success view after an ingredient is added.
     * Fires a property change event to notify listeners.
     */
    public void prepareSuccessView() {
        addIngredientViewModel.firePropertyChange(AddIngredientViewModel.INGREDIENT_ADDED_PROPERTY);
    }
}

