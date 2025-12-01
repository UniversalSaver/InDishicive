package logic.user_recipe.view_recipes;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import entity.UserRecipe;

/**
 * An implementation of the respective interface.
 */
public class ViewRecipesInteractor implements ViewRecipesInputBoundary {

    private final ViewRecipesOutputBoundary viewRecipesPresenter;
    private final ViewRecipesDataAccessInterface viewRecipesDataAccess;

    public ViewRecipesInteractor(ViewRecipesOutputBoundary viewRecipesPresenter,
                                 ViewRecipesDataAccessInterface viewRecipesDataAccess) {
        this.viewRecipesPresenter = viewRecipesPresenter;
        this.viewRecipesDataAccess = viewRecipesDataAccess;
    }

    @Override
    public void execute() {

        final List<UserRecipe> userRecipes = viewRecipesDataAccess.getUserRecipes();

        final List<ViewRecipesOutputData> viewRecipesOutputData = getRecipeData(userRecipes);

        viewRecipesPresenter.prepareSuccessView(viewRecipesOutputData);
    }

    @NotNull
    private static List<ViewRecipesOutputData> getRecipeData(List<UserRecipe> userRecipes) {
        final List<ViewRecipesOutputData> result = new ArrayList<>();

        for (UserRecipe userRecipe : userRecipes) {
            result.add(new ViewRecipesOutputData(userRecipe.getTitle(), userRecipe.getDescription()));
        }
        return result;
    }
}
