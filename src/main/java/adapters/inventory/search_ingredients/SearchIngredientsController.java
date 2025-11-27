package adapters.inventory.search_ingredients;

import use_case.search_ingredients.SearchIngredientsInputBoundary;

public class SearchIngredientsController {

    private final SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor;

    public SearchIngredientsController(SearchIngredientsInputBoundary searchIngredientsUseCaseInteractor) {
        this.searchIngredientsUseCaseInteractor = searchIngredientsUseCaseInteractor;
    }

    public void execute(String searchQuery) {
        searchIngredientsUseCaseInteractor.execute(searchQuery);
    }
}

