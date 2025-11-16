package interface_adapter.view_favorite;

import interface_adapter.ViewModel;
import entity.Recipe;
import java.util.List;

/**
 * ViewModel for the View Favorites use case.
 * Follows the same pattern as ViewRecipesViewModel.
 */
public class ViewFavoriteViewModel extends ViewModel<List<Recipe>> {

    public static final String FAV_LOADED = "favorites_loaded";

    public ViewFavoriteViewModel() {
        super("view_favorites");
    }
}