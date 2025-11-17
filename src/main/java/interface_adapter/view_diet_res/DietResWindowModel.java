package interface_adapter.view_diet_res;

import interface_adapter.ViewModel;

public class DietResWindowModel extends ViewModel<Boolean> {

    public static final String SET_VISIBLE = "set_visible";
    public static final String VIEW_NAME = "dietary_restrictions_window";

    public DietResWindowModel() {
        super(VIEW_NAME);
    }
}

