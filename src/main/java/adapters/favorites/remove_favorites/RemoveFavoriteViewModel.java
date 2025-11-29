package adapters.favorites.remove_favorites;

import adapters.ViewModel;

public class RemoveFavoriteViewModel extends ViewModel<RemoveFavoriteState> {
    public static final String FAVORITE_REMOVED = "favorite_removed";
    public static final String FAVORITE_REMOVED_FAILED = "favorite_removed_failed";

    public RemoveFavoriteViewModel() {
        super("remove_favorite");
        setState(new RemoveFavoriteState());
    }
}
