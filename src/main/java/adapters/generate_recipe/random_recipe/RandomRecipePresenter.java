package adapters.generate_recipe.random_recipe;

import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsState;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import entity.Recipe;
import logic.generate_recipe.random_recipe.RandomRecipeOutputBoundary;

import javax.swing.JOptionPane;

/**
 * A presenter for the random recipe use case.
 */
public class RandomRecipePresenter implements RandomRecipeOutputBoundary {
    private final ViewRecipeDetailsViewModel recipeDetailsViewModel;

    public RandomRecipePresenter(ViewRecipeDetailsViewModel recipeDetailsViewModel) {
        this.recipeDetailsViewModel = recipeDetailsViewModel;
    }

    @Override
    public void prepareSuccessView(Recipe recipe) {
        ViewRecipeDetailsState state = createState(recipe);

        recipeDetailsViewModel.setState(state);
        recipeDetailsViewModel.firePropertyChange(ViewRecipeDetailsViewModel.DETAILS_PROPERTY);
    }

    private ViewRecipeDetailsState createState(Recipe recipe) {
        ViewRecipeDetailsState state = new ViewRecipeDetailsState();
        state.setTitle(recipe.getTitle());
        state.setInstructions(recipe.getSteps());
        state.setIngredients(recipe.getIngredients().stream()
                .map(i -> i.getName() + ": " + i.getAmount()).toList());
        return state;
    }

    @Override
    public void prepareFailView(String error) {
        JOptionPane.showMessageDialog(null, error, "Random Recipe Error", JOptionPane.ERROR_MESSAGE);
    }
}