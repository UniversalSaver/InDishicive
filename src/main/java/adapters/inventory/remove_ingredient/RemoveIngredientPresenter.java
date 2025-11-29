package adapters.inventory.remove_ingredient;

import logic.inventory.remove_ingredient.RemoveIngredientOutputBoundary;

/**
 * Presenter for the Remove Ingredient use case.
 * Notifies the view when an ingredient has been successfully removed.
 */
public class RemoveIngredientPresenter implements RemoveIngredientOutputBoundary {

    private final RemoveIngredientViewModel removeIngredientViewModel;

    public RemoveIngredientPresenter(RemoveIngredientViewModel removeIngredientViewModel) {
        this.removeIngredientViewModel = removeIngredientViewModel;
    }

    /**
     * Prepares the success view after an ingredient is removed.
     * Fires a property change event to notify listeners.
     */
    public void prepareSuccessView() {
        removeIngredientViewModel.firePropertyChange(RemoveIngredientViewModel.INGREDIENT_REMOVED_PROPERTY);
    }
}

