package adapters.inventory.search_ingredients;

import logic.inventory.search_ingredients.SearchIngredientsInputBoundary;

public class SearchIngredientsController {

    private final SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor;

    public SearchIngredientsController(SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor) {
        this.searchIngredientsUseCaseInteractor = searchIngredientsUseCaseInteractor;
    }

    public void execute(String searchQuery) {
        searchIngredientsUseCaseInteractor.execute(searchQuery);
    }
}

