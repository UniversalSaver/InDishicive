package use_case.generate_recipe.filter_by_cuisine;

import java.util.List;

public class FilterByCuisineOutputData {

    private final String cuisine;
    private final List<String> recipeTitles;

    public FilterByCuisineOutputData(String cuisine, List<String> recipeTitles) {
        this.cuisine = cuisine;
        this.recipeTitles = recipeTitles;
    }

    public String getCuisine() {
        return cuisine;
    }

    public List<String> getRecipeTitles() {
        return recipeTitles;
    }
}