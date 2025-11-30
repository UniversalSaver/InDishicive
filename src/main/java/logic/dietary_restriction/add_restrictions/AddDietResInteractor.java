package logic.dietary_restriction.add_restrictions;

import entity.Ingredient;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

public class AddDietResInteractor implements AddDietResInputBoundary {
    private final DietResDataAccessInterface dietResDataAccessInterface;
    private final AddDietResOutputBoundary addDietResOutputBoundary;
    private final IngredientGateway ingredientGateway; // New Dependency

    public AddDietResInteractor(DietResDataAccessInterface dietResDataAccessInterface,
                                AddDietResOutputBoundary addDietResOutputBoundary,
                                IngredientGateway ingredientGateway) {
        this.dietResDataAccessInterface = dietResDataAccessInterface;
        this.addDietResOutputBoundary = addDietResOutputBoundary;
        this.ingredientGateway = ingredientGateway;
    }

    @Override
    public void execute(AddDietResInputData inputData) {
        Ingredient ingredient = inputData.getIngredient();
        String ingredientName = ingredient.getName();

        if (!ingredientGateway.isValidIngredient(ingredientName)) {
            addDietResOutputBoundary.prepareFailView("Ingredient not found in database");
            return;
        }

        if (dietResDataAccessInterface.isRestricted(ingredient)) {
            addDietResOutputBoundary.prepareFailView("Already in Dietary Restricted Ingredients List");
            return;
        }

        dietResDataAccessInterface.saveRestriction(ingredient);
        addDietResOutputBoundary.prepareSuccessView();
    }
}
