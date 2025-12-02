package logic.inventory.search_ingredients;

import java.util.List;

/**
 * Output boundary for search ingredients use case.
 */
public interface SearchIngredientsOutputBoundary {

    /**
     * Prepares the success view with found ingredients.
     * @param ingredients the list of found ingredients
     */
    void prepareSuccessView(List<String> ingredients);

    /**
     * Prepares the failure view with an error message.
     * @param error the error message
     */
    void prepareFailView(String error);
}
