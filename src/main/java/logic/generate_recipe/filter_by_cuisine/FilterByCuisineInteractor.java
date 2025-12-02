package logic.generate_recipe.filter_by_cuisine;

import java.util.List;

public class FilterByCuisineInteractor implements FilterByCuisineInputBoundary {

    private final FilterByCuisineDataAccessInterface dao;
    private final FilterByCuisineOutputBoundary presenter;

    public FilterByCuisineInteractor(FilterByCuisineDataAccessInterface dao,
                                     FilterByCuisineOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(String cuisine) {
        if (cuisine == null || cuisine.isBlank() || "Any".equalsIgnoreCase(cuisine)) {
            presenter.present(new FilterByCuisineOutputData("Any", List.of()));
            return;
        }

        List<String> titlesForCuisine = dao.getRecipeTitlesByCuisine(cuisine);
        presenter.present(new FilterByCuisineOutputData(cuisine, titlesForCuisine));
    }
}