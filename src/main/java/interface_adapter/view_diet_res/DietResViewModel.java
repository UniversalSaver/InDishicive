package interface_adapter.view_diet_res;

import interface_adapter.ViewModel;

public class DietResViewModel extends ViewModel<ViewRestrictionsState>{
    public static final String VIEW_NAME = "dietary_restrictions";

    public DietResViewModel() {
        super(VIEW_NAME);
    }
}
