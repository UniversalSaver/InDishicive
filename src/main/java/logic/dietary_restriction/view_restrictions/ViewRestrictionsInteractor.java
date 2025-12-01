package logic.dietary_restriction.view_restrictions;

import java.util.ArrayList;
import java.util.List;

import entity.Ingredient;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

public class ViewRestrictionsInteractor implements ViewRestrictionsInputBoundary {

    private final DietResDataAccessInterface dietResDataAccessObject;
    private final ViewRestrictionsOutputBoundary viewRestrictionsPresenter;

    public ViewRestrictionsInteractor(DietResDataAccessInterface dietResDataAccessObject,
                                      ViewRestrictionsOutputBoundary viewRestrictionsPresenter) {
        this.dietResDataAccessObject = dietResDataAccessObject;
        this.viewRestrictionsPresenter = viewRestrictionsPresenter;
    }

    @Override
    public void execute() {
        final List<Ingredient> ingredients = dietResDataAccessObject.getResIngredients();

        final List<String> ingredientNames = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
        }

        final ViewRestrictionsOutputData outputData = new ViewRestrictionsOutputData(ingredientNames);

        viewRestrictionsPresenter.prepareSuccessView(outputData);
    }
}
