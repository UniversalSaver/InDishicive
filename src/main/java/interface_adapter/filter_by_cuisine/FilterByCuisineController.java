package interface_adapter.filter_by_cuisine;

import use_case.filter_by_cuisine.FilterByCuisineInputBoundary;

public class FilterByCuisineController {

    private final FilterByCuisineInputBoundary interactor;

    public FilterByCuisineController(FilterByCuisineInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String cuisine) {
        interactor.execute(cuisine);
    }
}