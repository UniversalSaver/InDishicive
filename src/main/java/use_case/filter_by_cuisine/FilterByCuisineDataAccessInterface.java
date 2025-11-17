package use_case.filter_by_cuisine;

import java.util.List;

public interface FilterByCuisineDataAccessInterface {

    /**
     * Returns a list of recipe titles for the given cuisine
     * (e.g., "Italian", "Canadian", etc.).
     */
    List<String> getRecipeTitlesByCuisine(String cuisine);
}