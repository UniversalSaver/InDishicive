package use_case.view_recipe_details;

import entity.Ingredient;
import entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipeDetailsInteractor implements ViewRecipeDetailsInputBoundary {

    private final RecipeDetailsGateway gateway;
    private final ViewRecipeDetailsOutputBoundary presenter;

    public ViewRecipeDetailsInteractor(RecipeDetailsGateway gateway,
                                       ViewRecipeDetailsOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewRecipeDetailsInputData inputData) {
        String title = inputData.getTitle();

        Recipe recipe = gateway.findByTitle(title);

        if (recipe == null) {
            presenter.present(new ViewRecipeDetailsOutputData(
                    "",
                    List.of(),
                    ""
            ));
            return;
        }

        List<String> ingredientsList = new ArrayList<>();
        for (Ingredient i : recipe.getIngredients()) {
            ingredientsList.add(i.getName() + ": " + i.getAmount());
        }

        presenter.present(new ViewRecipeDetailsOutputData(
                recipe.getTitle(),
                ingredientsList,
                recipe.getSteps()
        ));
    }
}