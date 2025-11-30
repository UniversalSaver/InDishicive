package adapters.favorites.remove_favorites;

import adapters.ViewModel;

/**
 * View model for the remove favorite use case.
 * Manages the state and property change notifications for removing favorites.
 */
public class RemoveFavoriteViewModel extends ViewModel<RemoveFavoriteState> {
    /** Property name fired when a favorite is successfully removed. */
    public static final String FAVORITE_REMOVED = "favorite_removed";

    /** Property name fired when removing a favorite fails. */
    public static final String FAVORITE_REMOVED_FAILED = "favorite_removed_failed";

    /**
     * Constructs a RemoveFavoriteViewModel with an initial empty state.
     */
    public RemoveFavoriteViewModel() {
        super("remove_favorite");
        setState(new RemoveFavoriteState());
    }
}
