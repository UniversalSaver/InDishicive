package use_case.add_recipe;

/**
 * Uses the database to get a list of ingredients, then prompts the presenter to present them as a choice to the user.
 */
public interface AddIngredientInputBoundary {
	public void execute();
}
