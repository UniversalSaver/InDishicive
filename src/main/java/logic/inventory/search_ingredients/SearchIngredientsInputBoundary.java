package logic.inventory.search_ingredients;

/**
 * Input boundary for searching ingredients.
 */
public interface SearchIngredientsInputBoundary {

    /**
     * Executes the search ingredients use case.
     * @param searchQuery the search query
     */
    void execute(String searchQuery);
}
