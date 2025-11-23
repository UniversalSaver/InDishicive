package use_case.view_restrictions;

import entity.Ingredient;
import use_case.diet_res_ingredients.DietResDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

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
        List<Ingredient> ingredients = dietResDataAccessObject.getResIngredients();

        List<String> ingredientNames = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
        }

        ViewRestrictionsOutputData outputData = new ViewRestrictionsOutputData(ingredientNames);

        viewRestrictionsPresenter.prepareSuccessView(outputData);
    }
}
