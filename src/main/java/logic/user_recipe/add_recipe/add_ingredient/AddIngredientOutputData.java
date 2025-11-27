package logic.user_recipe.add_recipe.add_ingredient;

import java.util.List;

/**
 * A representative object for the list of ingredient names gotten.
 */
public class AddIngredientOutputData {
	private final List<String> possibleIngredients;

	public AddIngredientOutputData(List<String> possibleIngredients) {
		this.possibleIngredients = possibleIngredients;
	}

	public List<String> getPossibleIngredients() {
		return possibleIngredients;
	}
}
