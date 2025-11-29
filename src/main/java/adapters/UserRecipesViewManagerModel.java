package adapters;

/**
 * A manager for the User Recipe Window. Used to switch between views.
 */
public class UserRecipesViewManagerModel extends ViewModel<String> {

    public static final String CHANGE_VIEW = "change_view";

    public UserRecipesViewManagerModel() {
        super("manager model");
    }
}
