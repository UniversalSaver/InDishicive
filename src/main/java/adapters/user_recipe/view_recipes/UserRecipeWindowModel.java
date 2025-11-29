package adapters.user_recipe.view_recipes;

import adapters.ViewModel;

/**
 * The model for the User Recipe Window, with information on property change names.
 */
public class UserRecipeWindowModel extends ViewModel<Boolean> {

    public static final String SET_VISIBLE = "set_visible";
    public static final String VIEW_NAME = "user_recipe_window";

    public UserRecipeWindowModel() {
        super(VIEW_NAME);
    }
}
