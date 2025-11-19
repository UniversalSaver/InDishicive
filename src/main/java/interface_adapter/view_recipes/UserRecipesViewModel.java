package interface_adapter.view_recipes;

import interface_adapter.ViewModel;

public class UserRecipesViewModel extends ViewModel<ViewRecipesState> {
    public static final String VIEW_NAME = "user_recipes";
    public static final String SET_SUMMARIES = "set_summaries";

    public UserRecipesViewModel() {
        super(VIEW_NAME);
    }
}
