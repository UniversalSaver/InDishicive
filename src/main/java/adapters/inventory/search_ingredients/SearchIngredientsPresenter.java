package adapters.inventory.search_ingredients;

import java.util.List;

import logic.inventory.search_ingredients.SearchIngredientsOutputBoundary;

/**
 * Presenter for the Search Ingredients use case.
 * Prepares data for display and notifies the view of state changes.
 */
public class SearchIngredientsPresenter implements SearchIngredientsOutputBoundary {

    private final SearchIngredientsViewModel searchIngredientsViewModel;

    public SearchIngredientsPresenter(SearchIngredientsViewModel searchIngredientsViewModel) {
        this.searchIngredientsViewModel = searchIngredientsViewModel;
    }

    @Override
    public void prepareSuccessView(List<String> ingredients) {
        final SearchIngredientsState state = new SearchIngredientsState();
        state.setIngredients(ingredients);
        searchIngredientsViewModel.setState(state);
        searchIngredientsViewModel.firePropertyChange(SearchIngredientsViewModel.SEARCH_SUCCESS_PROPERTY);
    }

    @Override
    public void prepareFailView(String error) {
        final SearchIngredientsState state = new SearchIngredientsState();
        state.setError(error);
        searchIngredientsViewModel.setState(state);
        searchIngredientsViewModel.firePropertyChange(SearchIngredientsViewModel.SEARCH_FAIL_PROPERTY);
    }
}
