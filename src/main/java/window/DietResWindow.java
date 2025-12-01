package window;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import adapters.dietary_restriction.view_diet_res.DietResWindowModel;

/**
 * The window responsible for displaying the dietary restrictions interface.
 * It listens for property changes from the DietResWindowModel to control its own visibility.
 */
public class DietResWindow extends JFrame implements PropertyChangeListener {

    /**
     * Given cardPanel and dietResWindowModel, creates a new DietResWindow.
     * @param cardPanel The panel containing the views (managed by DietResViewManager)
     * @param dietResWindowModel The model deciding if this window is visible
     */
    public DietResWindow(JPanel cardPanel, DietResWindowModel dietResWindowModel) {
        super("Restrictions List");

        dietResWindowModel.addPropertyChangeListener(this);

        final int width = 300;
        final int height = 400;

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        if (cardPanel != null) {
            this.add(cardPanel, BorderLayout.CENTER);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (DietResWindowModel.SET_VISIBLE.equals(evt.getPropertyName())) {
            this.setVisible(true);
            this.toFront();
        }
    }
}
