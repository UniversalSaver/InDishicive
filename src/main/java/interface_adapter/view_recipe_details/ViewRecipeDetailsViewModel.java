package interface_adapter.view_recipe_details;

import interface_adapter.ViewModel;

public class ViewRecipeDetailsViewModel extends ViewModel<ViewRecipeDetailsState> {

    public static final String DETAILS_PROPERTY = "recipe_details";

    public ViewRecipeDetailsViewModel() {
        super("recipe-details-view");
        setState(new ViewRecipeDetailsState());
    }
}