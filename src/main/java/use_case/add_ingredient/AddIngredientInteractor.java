package use_case.add_ingredient;

import entity.Ingredient;

/**
 * Interactor for the Add Ingredient use case.
 * Handles the business logic for adding ingredients to the inventory.
 */
public class AddIngredientInteractor implements AddIngredientInputBoundary {

    private final AddIngredientOutputBoundary addIngredientPresenter;
    private final InventoryDataAccessInterface inventoryDataAccess;

    /**
     * Constructs an AddIngredientInteractor.
     * @param addIngredientPresenter the presenter for preparing output
     * @param inventoryDataAccess the data access interface for inventory operations
     */
    public AddIngredientInteractor(AddIngredientOutputBoundary addIngredientPresenter, 
                                    InventoryDataAccessInterface inventoryDataAccess) {
        this.addIngredientPresenter = addIngredientPresenter;
        this.inventoryDataAccess = inventoryDataAccess;
    }

    /**
     * Executes the add ingredient use case.
     * @param ingredientName the name of the ingredient to add
     * @param amount the amount/quantity of the ingredient
     */
    @Override
    public void execute(String ingredientName, String amount) {
        Ingredient ingredient = new Ingredient(ingredientName, amount);
        inventoryDataAccess.addIngredient(ingredient);
        addIngredientPresenter.prepareSuccessView();
    }
}

