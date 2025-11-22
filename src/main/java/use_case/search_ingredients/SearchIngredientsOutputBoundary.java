package use_case.search_ingredients;

import java.util.List;

public interface SearchIngredientsOutputBoundary {

    void prepareSuccessView(List<String> ingredients);

    void prepareFailView(String error);
}

