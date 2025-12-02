package logic.user_recipe.view_recipes.view_detailed_recipe;

/**
 * A data class for use in the View User Recipe Details use case.
 * To be used in looking for a User Recipe Title.
 */
public class ViewUserRecipeDetailsInputData {
    private final String title;

    public ViewUserRecipeDetailsInputData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
