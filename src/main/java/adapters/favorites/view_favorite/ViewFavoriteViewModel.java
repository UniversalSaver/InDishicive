package adapters.favorites.view_favorite;

import java.util.ArrayList;
import java.util.List;

import adapters.ViewModel;
import entity.Recipe;

/**
 * View model for the view favorite use case.
 * Manages the state of the favorites list and property change notifications.
 */
public class ViewFavoriteViewModel extends ViewModel<List<Recipe>> {

    /** Property name fired when favorites are loaded. */
    public static final String FAVORITE_LOADED = "favorites_loaded";

    /** Property name fired when the favorites window should be made visible. */
    public static final String SET_VISIBLE = "setVisible";

    /**
     * Constructs a ViewFavoriteViewModel with an empty initial list.
     */
    public ViewFavoriteViewModel() {
        super("view_favorites");
        setState(new ArrayList<>());
    }
}
