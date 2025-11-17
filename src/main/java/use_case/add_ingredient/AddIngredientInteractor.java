package use_case.add_ingredient;

import entity.Ingredient;
import entity.Inventory;

public class AddIngredientInteractor implements AddIngredientInputBoundary {

    private final AddIngredientOutputBoundary addIngredientPresenter;
    private final Inventory inventory;

    public AddIngredientInteractor(AddIngredientOutputBoundary addIngredientPresenter, Inventory inventory) {
        this.addIngredientPresenter = addIngredientPresenter;
        this.inventory = inventory;
    }

    @Override
    public void execute(String ingredientName, String amount) {
        Ingredient ingredient = new Ingredient(ingredientName, amount);
        inventory.addIngredient(ingredient);
        addIngredientPresenter.prepareSuccessView();
    }
}

