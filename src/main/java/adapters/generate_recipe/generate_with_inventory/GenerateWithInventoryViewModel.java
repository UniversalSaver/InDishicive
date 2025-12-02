package adapters.generate_recipe.generate_with_inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.ViewModel;

/**
 * View model for the "generate with inventory" use case.
 * Holds all recipe titles, paging state, and any error message.
 */
public class GenerateWithInventoryViewModel extends ViewModel<List<String>> {

    private List<String> allTitles = new ArrayList<>();
    private int offset;
    private String errorMessage = "";

    /**
     * Creates a new view model for the generate-with-inventory view.
     */
    public GenerateWithInventoryViewModel() {
        super("generate_with_inventory");
        setState(List.of());
    }

    /**
     * Resets the stored recipe titles and paging state.
     *
     * @param titles the new list of recipe titles
     */
    public void resetTitles(List<String> titles) {
        this.allTitles = new ArrayList<>(titles);
        this.offset = 0;
        setState(new ArrayList<>(titles));
    }

    /**
     * Gets all stored recipe titles.
     *
     * @return a copy of the list of all titles
     */
    public List<String> getAllTitles() {
        return new ArrayList<>(allTitles);
    }

    /**
     * Gets the next page of recipe titles.
     *
     * @param pageSize the maximum number of titles to return
     * @return a list containing the next page of titles, or an empty list if none
     */
    public List<String> getNextPage(int pageSize) {
        final List<String> page;

        if (allTitles.isEmpty() || offset >= allTitles.size()) {
            page = Collections.emptyList();
        }
        else {
            final int to = Math.min(offset + pageSize, allTitles.size());
            page = new ArrayList<>(allTitles.subList(offset, to));
            offset = to;
        }

        return page;
    }

    /**
     * Sets the current error message.
     *
     * @param errorMessage the error message text
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the current error message.
     *
     * @return the error message text
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the recipes and updates the view state.
     *
     * @param titles the list of recipe titles to display
     */
    public void setRecipes(List<String> titles) {
        this.allTitles = new ArrayList<>(titles);
        this.offset = 0;
        setState(new ArrayList<>(titles));
        firePropertyChange("recipes");
    }
}
