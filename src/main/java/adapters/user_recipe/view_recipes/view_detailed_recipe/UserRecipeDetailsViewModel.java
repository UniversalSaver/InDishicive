package adapters.user_recipe.view_recipes.view_detailed_recipe;

import adapters.ViewModel;

public class UserRecipeDetailsViewModel extends ViewModel<RecipeDetails> {

    public static final String USER_RECIPE_VIEW_NAME = "user_recipe";
    public static final String SHOW_RECIPE_DETAILS = "show_recipe_details";

    public UserRecipeDetailsViewModel() {
        super(USER_RECIPE_VIEW_NAME);
    }
}
