package logic.generate_recipe.filter_by_cuisine;

import java.util.List;

public class FilterByCuisineInteractor implements FilterByCuisineInputBoundary {

    private final FilterByCuisineDataAccessInterface dataAccess;
    private final FilterByCuisineOutputBoundary presenter;

    public FilterByCuisineInteractor(FilterByCuisineDataAccessInterface dataAccess,
                                     FilterByCuisineOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(String cuisine) {
        if (cuisine == null || cuisine.isBlank() || cuisine.equalsIgnoreCase("Any")) {
            presenter.present(new FilterByCuisineOutputData("Any", List.of()));
            return;
        }

        List<String> titles = dataAccess.getRecipeTitlesByCuisine(cuisine);
        presenter.present(new FilterByCuisineOutputData(cuisine, titles));
    }
}