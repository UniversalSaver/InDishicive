package logic.user_recipe.add_recipe;

/**
 * An interface for the presenter, for what the Interactor can expect to do.
 */
public interface AddRecipeOutputBoundary {
    /**
     * Prepares the view for success. Likely telling the user a recipe was created, and showing it.
     */
    void prepareSuccessView();

    /**
     * Prepares the view for failure. Telling user what went wrong, and if applicable, how to fix it
     * given a message.
     * @param message message for why the call failed, and if applicable, how to fix it.
     */
    void prepareFailureView(String message);
}
