package use_case.add_recipe;

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
