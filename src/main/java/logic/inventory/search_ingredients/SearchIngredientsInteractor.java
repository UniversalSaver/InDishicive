package logic.inventory.search_ingredients;

import java.util.List;
import java.util.stream.Collectors;

import databases.inventory.IngredientDataAccessException;

/**
 * Interactor for the search ingredients use case.
 */
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
            final List<String> allIngredients = ingredientDataAccess.getAllIngredients();

            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                searchIngredientsPresenter.prepareSuccessView(allIngredients);
            }
            else {
                final String query = searchQuery.toLowerCase().trim();
                final List<String> filteredIngredients = allIngredients.stream()
                        .filter(ingredient -> ingredient.toLowerCase().contains(query))
                        .collect(Collectors.toList());
                searchIngredientsPresenter.prepareSuccessView(filteredIngredients);
            }
        }
        catch (IngredientDataAccessException ex) {
            searchIngredientsPresenter.prepareFailView("Failed to search ingredients: " + ex.getMessage());
        }
    }
}
