package adapters.user_recipe.view_recipes;

/**
 * A data object meant for use by the ViewRecipesPresenter when showing User Recipes.
 */
public class RecipeSummary {
    private final String title;
    private final String description;

    RecipeSummary(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
