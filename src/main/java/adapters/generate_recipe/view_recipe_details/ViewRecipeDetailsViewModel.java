package adapters.generate_recipe.view_recipe_details;

import adapters.ViewModel;

public class ViewRecipeDetailsViewModel extends ViewModel<ViewRecipeDetailsState> {

    public static final String DETAILS_PROPERTY = "recipe_details";

    public ViewRecipeDetailsViewModel() {
        super("recipe-details-view");
        setState(new ViewRecipeDetailsState());
    }
}
