package logic.inventory.remove_ingredient;

import entity.Ingredient;

/**
 * Interactor for the Remove Ingredient use case.
 * Handles the business logic for removing ingredients from the inventory.
 */
public class RemoveIngredientInteractor implements RemoveIngredientInputBoundary {

    private final RemoveIngredientOutputBoundary removeIngredientPresenter;
    private final RemoveIngredientDataAccessInterface inventoryDataAccess;

    /**
     * Constructs a RemoveIngredientInteractor.
     * @param removeIngredientPresenter the presenter for preparing output
     * @param inventoryDataAccess the data access interface for inventory operations
     */
    public RemoveIngredientInteractor(RemoveIngredientOutputBoundary removeIngredientPresenter,
                                       RemoveIngredientDataAccessInterface inventoryDataAccess) {
        this.removeIngredientPresenter = removeIngredientPresenter;
        this.inventoryDataAccess = inventoryDataAccess;
    }

    /**
     * Executes the remove ingredient use case.
     * Searches for the ingredient by name and removes it from inventory if found.
     * @param ingredientName the name of the ingredient to remove
     */
    @Override
    public void execute(String ingredientName) {
        final Ingredient toRemove = inventoryDataAccess.findIngredientByName(ingredientName);

        if (toRemove != null) {
            inventoryDataAccess.removeIngredient(toRemove);
        }

        removeIngredientPresenter.prepareSuccessView();
    }
}
