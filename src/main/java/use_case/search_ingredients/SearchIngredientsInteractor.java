package use_case.search_ingredients;

import data_access.IngredientDataAccessException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interactor for the Search Ingredients use case.
 * Handles the business logic for searching and filtering ingredients.
 */
public class SearchIngredientsInteractor implements SearchIngredientsInputBoundary {

    private final SearchIngredientsOutputBoundary searchIngredientsPresenter;
    private final SearchIngredientsDataAccessInterface ingredientDataAccess;

    /**
     * Constructs a SearchIngredientsInteractor.
     * @param searchIngredientsPresenter the presenter for preparing output
     * @param ingredientDataAccess the data access interface for fetching ingredients
     */
    public SearchIngredientsInteractor(SearchIngredientsOutputBoundary searchIngredientsPresenter,
                                       SearchIngredientsDataAccessInterface ingredientDataAccess) {
        this.searchIngredientsPresenter = searchIngredientsPresenter;
        this.ingredientDataAccess = ingredientDataAccess;
    }

    /**
     * Executes the search ingredients use case.
     * If no search query is provided, returns all ingredients.
     * Otherwise, filters ingredients based on the search query.
     * @param searchQuery the search query to filter ingredients (can be null or empty)
     */
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
                        .collect(Collectors.toList());
                searchIngredientsPresenter.prepareSuccessView(filteredIngredients);
            }
        } catch (IngredientDataAccessException e) {
            searchIngredientsPresenter.prepareFailView("Failed to search ingredients: " + e.getMessage());
        }
    }
}

