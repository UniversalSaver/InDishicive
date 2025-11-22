package use_case.remove_ingredient;

import entity.Ingredient;
import entity.Inventory;

public class RemoveIngredientInteractor implements RemoveIngredientInputBoundary {

    private final RemoveIngredientOutputBoundary removeIngredientPresenter;
    private final Inventory inventory;

    public RemoveIngredientInteractor(RemoveIngredientOutputBoundary removeIngredientPresenter, Inventory inventory) {
        this.removeIngredientPresenter = removeIngredientPresenter;
        this.inventory = inventory;
    }

    @Override
    public void execute(String ingredientName) {
        Ingredient toRemove = null;
        for (Ingredient ingredient : inventory.getIngredients()) {
            if (ingredient.getName().equals(ingredientName)) {
                toRemove = ingredient;
                break;
            }
        }
        
        if (toRemove != null) {
            inventory.removeIngredient(toRemove);
        }
        
        removeIngredientPresenter.prepareSuccessView();
    }
}

