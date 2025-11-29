package adapters.user_recipe.add_recipe;

import adapters.ViewModel;

import java.util.List;

/**
 * The Add Recipe View's model, which has a state of what ingredients can be added.
 */
public class AddRecipeViewModel extends ViewModel<List<String>> {

	public static final String VIEW_NAME = "add_recipe";

	public static final String WIPE_VIEW = "wipe_view";

	public static final String ADD_INGREDIENT = "add_recipe_ingredient";

	public static final String DATABASE_NOT_FOUND = "database_not_found";

	public static final String ADD_RECIPE_FAIL = "add_recipe_fail";

	public AddRecipeViewModel() {
		super(VIEW_NAME);
	}
}
