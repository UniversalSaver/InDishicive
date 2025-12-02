package logic.dietary_restriction.remove_restriction;

import entity.Ingredient;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

public class RemoveDietResInteractor implements RemoveDietResInputBoundary {
    private final DietResDataAccessInterface dietResDataAccessObject;
    private final RemoveDietResOutputBoundary removeDietResPresenter;

    public RemoveDietResInteractor(DietResDataAccessInterface dietResDataAccessObject,
                                   RemoveDietResOutputBoundary removeDietResPresenter) {
        this.dietResDataAccessObject = dietResDataAccessObject;
        this.removeDietResPresenter = removeDietResPresenter;
    }

    @Override
    public void execute(RemoveDietResInputData inputData) {
        final Ingredient ingredient = inputData.getIngredient();

        if (dietResDataAccessObject.isRestricted(ingredient)) {
            dietResDataAccessObject.removeRestriction(ingredient);
            removeDietResPresenter.prepareSuccessView();
        }
        else {
            removeDietResPresenter.prepareFailView("Ingredient not found in restrictions list.");
        }
    }
}
