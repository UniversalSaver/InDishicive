package interface_adapter.view_favorite;

import java.util.ArrayList;
import java.util.List;
import entity.Recipe;
import interface_adapter.ViewModel;


public class ViewFavoriteViewModel extends ViewModel<List<Recipe>> {

    public static final String FAVORITE_LOADED = "favorites_loaded";
    public static final String SET_VISIBLE = "setVisible";

    public ViewFavoriteViewModel() {
        super("view_favorites");
        setState(new ArrayList<>());
    }
}