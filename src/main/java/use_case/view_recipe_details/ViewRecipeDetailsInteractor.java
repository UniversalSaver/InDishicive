package use_case.view_recipe_details;

import entity.Recipe;

import java.util.stream.Collectors;

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
                    java.util.List.of(),
                    ""
            ));
            return;
        }

        presenter.present(new ViewRecipeDetailsOutputData(
                recipe.getTitle(),
                recipe.getIngredients().stream()
                        .map(i -> i.getName() + ": " + i.getAmount())
                        .collect(Collectors.toList()),
                recipe.getSteps()
        ));
    }
}