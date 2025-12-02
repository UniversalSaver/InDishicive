package logic.user_recipe.add_recipe;

/**
 * An interface for what the controller should give to the interactor.
 */
public interface AddRecipeInputBoundary {
    /**
     * Given data about recipe, should add it to the database.
     * @param inputData data about recipe
     */
    void execute(AddRecipeInputData inputData);
}
