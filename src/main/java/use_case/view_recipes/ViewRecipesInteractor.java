package use_case.view_recipes;

import entity.UserRecipe;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipesInteractor implements ViewRecipesInputBoundary {

    private final ViewRecipesOutputBoundary viewRecipesPresenter;
    private final ViewRecipesDataAccessInterface viewRecipesDataAccess;

    public ViewRecipesInteractor(ViewRecipesOutputBoundary ViewRecipesPresenter,
                                 ViewRecipesDataAccessInterface ViewRecipesDataAccess) {
        this.viewRecipesPresenter = ViewRecipesPresenter;
        this.viewRecipesDataAccess = ViewRecipesDataAccess;
    }

    @Override
    public void execute() {

        List<UserRecipe> userRecipes = viewRecipesDataAccess.getUserRecipes();

        List<ViewRecipesOutputData> viewRecipesOutputData = new ArrayList<>();

        for (UserRecipe userRecipe : userRecipes) {
            viewRecipesOutputData.add(new ViewRecipesOutputData(userRecipe.getTitle(), userRecipe.getDescription()));
        }

        viewRecipesPresenter.prepareSuccessView(viewRecipesOutputData);
    }
}
