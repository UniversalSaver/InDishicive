package use_case.user_recipe.view_recipes;

import entity.UserRecipe;

import java.util.List;

public interface ViewRecipesDataAccessInterface {

    List<UserRecipe> getUserRecipes();
}
