package adapters.generate_recipe.filter_by_cuisine;

import logic.generate_recipe.filter_by_cuisine.FilterByCuisineInputBoundary;

public class FilterByCuisineController {

    private final FilterByCuisineInputBoundary interactor;

    public FilterByCuisineController(FilterByCuisineInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String cuisine) {
        interactor.execute(cuisine);
    }
}