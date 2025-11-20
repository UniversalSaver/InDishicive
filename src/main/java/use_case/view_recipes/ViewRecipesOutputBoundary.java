package use_case.view_recipes;

import java.util.List;

public interface ViewRecipesOutputBoundary {

    void prepareSuccessView(List<ViewRecipesOutputData> recipeInformation);
}
