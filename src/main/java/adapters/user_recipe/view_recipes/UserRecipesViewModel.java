package adapters.user_recipe.view_recipes;

import adapters.ViewModel;

/**
 * The View Recipes view model, whose state stores information on what recipes should be shown to user.
 */
public class UserRecipesViewModel extends ViewModel<ViewRecipesState> {
    public static final String VIEW_NAME = "user_recipes";
    public static final String SET_SUMMARIES = "set_summaries";
    public static final String DETAILS_ERROR_MESSAGE = "details_error_message";

    public UserRecipesViewModel() {
        super(VIEW_NAME);
    }
}
