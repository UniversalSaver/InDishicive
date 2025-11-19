package data_access;

import entity.UserRecipe;
import use_case.view_recipes.ViewRecipesDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class MemoryDataAccessObject implements ViewRecipesDataAccessInterface {

    private final String userRecipeFile = "user_recipes.txt";
    private List<UserRecipe> userRecipes = new ArrayList<>();

    public MemoryDataAccessObject() {

    }

    @Override
    public List<UserRecipe> getUserRecipes() {
        return this.userRecipes;
    }
}
