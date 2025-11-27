package adapters.favorites.add_favorite;

import adapters.ViewModel;

/**
 * ViewModel for the Add Favorite use case.
 */
public class AddFavoriteViewModel extends ViewModel<AddFavoriteState> {

    public static final String FAVORITE_ADDED = "favorite_added";
    public static final String FAVORITE_FAILED = "favorite_failed";

    public AddFavoriteViewModel() {
        super("add_favorite");
        setState(new AddFavoriteState());
    }
}