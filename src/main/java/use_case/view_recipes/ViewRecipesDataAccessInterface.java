package use_case.view_recipes;

import entity.UserRecipe;

import java.util.List;

public interface ViewRecipesDataAccessInterface {

    List<UserRecipe> getUserRecipes();
}
