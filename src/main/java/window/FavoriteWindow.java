package window;
import interface_adapter.view_favorite.ViewFavoriteViewModel;
import view.FavoriteView;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class FavoriteWindow extends JFrame implements PropertyChangeListener {

    public static final String SET_VISIBLE = "setVisible";

    public FavoriteWindow(FavoriteView favoritesView, ViewFavoriteViewModel viewModel) {
        super("My Favorites");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        add(favoritesView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SET_VISIBLE.equals(evt.getPropertyName())) {
            setVisible(true);
        }
    }
}