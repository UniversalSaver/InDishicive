package logic.generate_recipe.view_recipe_details;

import java.util.List;
import java.util.stream.Collectors;

import entity.Recipe;

/**
 * Interactor for viewing recipe details.
 */
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
        final String title = inputData.getTitle();

        final Recipe recipe = gateway.findByTitle(title);

        if (recipe == null) {
            presenter.present(new ViewRecipeDetailsOutputData(
                    "",
                    List.of(),
                    "",
                    ""
            ));
        }
        else {
            presenter.present(new ViewRecipeDetailsOutputData(
                    recipe.getTitle(),
                    recipe.getIngredients().stream()
                            .map(ingredient -> {
                                return ingredient.getName() + ": " + ingredient.getAmount();
                            })
                            .collect(Collectors.toList()),
                    recipe.getSteps(),
                    recipe.getYoutubeLink()
            ));
        }
    }
}
