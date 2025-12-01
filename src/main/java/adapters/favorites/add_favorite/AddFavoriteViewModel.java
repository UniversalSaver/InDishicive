package adapters.favorites.add_favorite;

import adapters.ViewModel;

/**
 * View model for the add favorite use case.
 * Manages the state and property change notifications for adding favorites.
 */
public class AddFavoriteViewModel extends ViewModel<AddFavoriteState> {

    /** Property name fired when a favorite is successfully added. */
    public static final String FAVORITE_ADDED = "favorite_added";

    /** Property name fired when adding a favorite fails. */
    public static final String FAVORITE_FAILED = "favorite_failed";

    /**
     * Constructs an AddFavoriteViewModel with an initial empty state.
     */
    public AddFavoriteViewModel() {
        super("add_favorite");
        setState(new AddFavoriteState());
    }
}
