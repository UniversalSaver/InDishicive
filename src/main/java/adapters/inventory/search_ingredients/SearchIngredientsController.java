package adapters.inventory.search_ingredients;

import logic.inventory.search_ingredients.SearchIngredientsInputBoundary;

/**
 * Controller for searching ingredients.
 */
public class SearchIngredientsController {

    private final SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor;

    public SearchIngredientsController(SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor) {
        this.searchIngredientsUseCaseInteractor = searchIngredientsUseCaseInteractor;
    }

    /**
     * Executes the search ingredients use case.
     * @param searchQuery the search query
     */
    public void execute(String searchQuery) {
        searchIngredientsUseCaseInteractor.execute(searchQuery);
    }
}
