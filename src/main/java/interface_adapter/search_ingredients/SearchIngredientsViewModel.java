package interface_adapter.search_ingredients;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Search Ingredients feature.
 * Contains constants for property change events.
 */
public class SearchIngredientsViewModel extends ViewModel<SearchIngredientsState> {

    /** Property name for successful ingredient search */
    public static final String SEARCH_SUCCESS_PROPERTY = "searchSuccess";
    
    /** Property name for failed ingredient search */
    public static final String SEARCH_FAIL_PROPERTY = "searchFail";

    public SearchIngredientsViewModel() {
        super("search_ingredients");
    }
}

