package interface_adapter.add_diet_res;

import interface_adapter.ViewModel;

public class AddDietResViewModel extends ViewModel<AddDietResState> {
    public static final String RESTRICTION_ADDED = "restriction_added";
    public static final String RESTRICTION_ADD_FAILED = "restriction_add_failed";

    public AddDietResViewModel() {
        super("add_restriction");
        setState(new AddDietResState());
    }
}
