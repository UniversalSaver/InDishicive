package use_case.search_ingredients;

import java.util.List;

public class SearchIngredientsInteractor implements SearchIngredientsInputBoundary {

    private final SearchIngredientsOutputBoundary searchIngredientsPresenter;
    private final SearchIngredientsDataAccessInterface ingredientDataAccess;

    public SearchIngredientsInteractor(SearchIngredientsOutputBoundary searchIngredientsPresenter,
                                       SearchIngredientsDataAccessInterface ingredientDataAccess) {
        this.searchIngredientsPresenter = searchIngredientsPresenter;
        this.ingredientDataAccess = ingredientDataAccess;
    }

    @Override
    public void execute(String searchQuery) {
        try {
            List<String> allIngredients = ingredientDataAccess.getAllIngredients();
            
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                searchIngredientsPresenter.prepareSuccessView(allIngredients);
            } else {
                String query = searchQuery.toLowerCase().trim();
                List<String> filteredIngredients = allIngredients.stream()
                        .filter(ingredient -> ingredient.toLowerCase().contains(query))
                        .toList();
                searchIngredientsPresenter.prepareSuccessView(filteredIngredients);
            }
        } catch (Exception e) {
            searchIngredientsPresenter.prepareFailView("Failed to search ingredients: " + e.getMessage());
        }
    }
}

