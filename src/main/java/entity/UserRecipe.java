package entity;

import java.util.List;

/**
 * An entity representing a user made recipe. Implements attributes which could be reasonably given by a user
 */
public class UserRecipe {
    private final String title;
    private final List<Ingredient> ingredients;
    private final String steps;
    private final String description;

    /**
     * Creates a new user recipe from the given information.
     * @param title the name of the dish
     * @param ingredients the list of ingredients
     * @param steps a paragraph of the steps required to create the dish
     * @param description a quick description of the dish, as the user may give a nonsensical name
     */
    public UserRecipe(String title, List<Ingredient> ingredients, String steps, String description) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

	public String getSteps() {
		return steps;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public boolean equals(Object obj) {
        boolean result = false;
		if (obj instanceof UserRecipe other) {

			result = getTitle().equals(other.getTitle()) && getIngredients().equals(other.getIngredients())
                    && getSteps().equals(other.getSteps()) && getDescription().equals(other.getDescription());
		}
        return result;
	}

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
