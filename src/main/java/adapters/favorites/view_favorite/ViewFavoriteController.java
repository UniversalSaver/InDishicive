package adapters.favorites.view_favorite;

import logic.favorites.view_favorite.ViewFavoriteInputBoundary;

/**
 * Controller for the view favorite use case.
 * Handles user requests to view their list of favorite recipes.
 */
public class ViewFavoriteController {
    private final ViewFavoriteInputBoundary interactor;

    /**
     * Constructs a ViewFavoriteController with the specified interactor.
     *
     * @param interactor the interactor that handles the view favorite business logic
     */
    public ViewFavoriteController(ViewFavoriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the view favorite use case to retrieve and display all favorite recipes.
     */
    public void execute() {
        interactor.execute();
    }
}
