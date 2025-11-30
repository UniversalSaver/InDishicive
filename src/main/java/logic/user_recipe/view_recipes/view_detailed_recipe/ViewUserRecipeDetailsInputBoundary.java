package logic.user_recipe.view_recipes.view_detailed_recipe;

/**
 * Defines expected operation of the interactor for viewing user recipe details.
 */
public interface ViewUserRecipeDetailsInputBoundary {
    /**
     * Given a title, executes the presenter with the related user recipe's details.
     * @param inputData title input data
     */
    void execute(ViewUserRecipeDetailsInputData inputData);
}
