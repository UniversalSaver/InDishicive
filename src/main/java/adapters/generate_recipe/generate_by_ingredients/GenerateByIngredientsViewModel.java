package adapters.generate_recipe.generate_by_ingredients;

import java.util.ArrayList;
import java.util.List;

import adapters.ViewModel;

public class GenerateByIngredientsViewModel extends ViewModel<List<String>> {

    public static final String VIEW_NAME = "generate_by_ingredients";
    public static final String SET_TITLES = "set_titles";

    public GenerateByIngredientsViewModel() {
        super(VIEW_NAME);
        setState(new ArrayList<>());
    }
}
