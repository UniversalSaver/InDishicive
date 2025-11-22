package interface_adapter.search_ingredients;

import use_case.search_ingredients.SearchIngredientsOutputBoundary;

import java.util.List;

public class SearchIngredientsPresenter implements SearchIngredientsOutputBoundary {

    private final SearchIngredientsViewModel searchIngredientsViewModel;

    public SearchIngredientsPresenter(SearchIngredientsViewModel searchIngredientsViewModel) {
        this.searchIngredientsViewModel = searchIngredientsViewModel;
    }

    @Override
    public void prepareSuccessView(List<String> ingredients) {
        SearchIngredientsState state = new SearchIngredientsState();
        state.setIngredients(ingredients);
        searchIngredientsViewModel.setState(state);
        searchIngredientsViewModel.firePropertyChange("searchSuccess");
    }

    @Override
    public void prepareFailView(String error) {
        SearchIngredientsState state = new SearchIngredientsState();
        state.setError(error);
        searchIngredientsViewModel.setState(state);
        searchIngredientsViewModel.firePropertyChange("searchFail");
    }
}

