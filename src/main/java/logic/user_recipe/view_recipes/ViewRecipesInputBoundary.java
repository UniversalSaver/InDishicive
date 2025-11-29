package logic.user_recipe.view_recipes;

/**
 * Defines expectations of the interactor.
 */
public interface ViewRecipesInputBoundary {

    /**
     * Queries database for user recipes, before prompting presenter to show all recipes.
     */
    void execute();
}
