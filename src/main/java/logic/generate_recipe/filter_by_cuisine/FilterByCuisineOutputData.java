package logic.generate_recipe.filter_by_cuisine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterByCuisineOutputData {

    private final String cuisine;
    private final List<String> titles;

    public FilterByCuisineOutputData(final String cuisine, final List<String> titles) {
        this.cuisine = cuisine;
        this.titles = (titles == null) ? new ArrayList<>() : new ArrayList<>(titles);
    }

    public String getCuisine() {
        return cuisine;
    }

    public List<String> getTitles() {
        return Collections.unmodifiableList(titles);
    }

    // Alias for older tests/callers that expect this name
    public List<String> getRecipeTitles() {
        return getTitles();
    }
}