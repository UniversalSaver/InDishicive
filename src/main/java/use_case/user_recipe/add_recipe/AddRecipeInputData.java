package use_case.user_recipe.add_recipe;

import java.util.List;

public class AddRecipeInputData {
    private final List<String> ingredientNames;
    private final List<String> ingredientAmounts;

    private final String title;

    private final String description;

	private final String steps;

	public AddRecipeInputData(List<String> ingredientNames, List<String> ingredientAmounts,
							  String title, String description, String steps) {
		this.ingredientNames = ingredientNames;
		this.ingredientAmounts = ingredientAmounts;
		this.title = title;
		this.description = description;
		this.steps = steps;
	}

	public List<String> getIngredientNames() {
		return ingredientNames;
	}

	public List<String> getIngredientAmounts() {
		return ingredientAmounts;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getSteps() {
		return steps;
	}
}