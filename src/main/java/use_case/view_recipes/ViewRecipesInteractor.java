package use_case.view_recipes;

import entity.UserRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

        List<UserRecipe> userRecipes = viewRecipesDataAccess.getUserRecipes();

        List<ViewRecipesOutputData> viewRecipesOutputData = getRecipeData(userRecipes);

        viewRecipesPresenter.prepareSuccessView(viewRecipesOutputData);
    }

    @NotNull
    private static List<ViewRecipesOutputData> getRecipeData(List<UserRecipe> userRecipes) {
        List<ViewRecipesOutputData> result = new ArrayList<>();

        for (UserRecipe userRecipe : userRecipes) {
            result.add(new ViewRecipesOutputData(userRecipe.getTitle(), userRecipe.getDescription()));
        }
        return result;
    }
}
