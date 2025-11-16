package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entity.Recipe;
import interface_adapter.view_favorite.ViewFavoriteViewModel;


public class FavoriteView extends JPanel implements PropertyChangeListener{
    private final ViewFavoriteViewModel viewModel;
    private final JPanel cardsPanel;

    public FavoriteView(ViewFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("My Favorite Recipes", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Recipe> favoriteRecipes = viewModel.getState();

        if (favoriteRecipes == null || favoriteRecipes.isEmpty()){
            displayEmptyMessage("No favorite recipes have been added");
        } else{
            displayMessage(favoriteRecipes);
        }
    }

    private void displayEmptyMessage(String message){
        cardsPanel.removeAll();
        JLabel emptyLabel = new JLabel(message, SwingConstants.CENTER);
        cardsPanel.add(emptyLabel);
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private  void displayMessage(List<Recipe> recipes){
        cardsPanel.removeAll();

        for(Recipe recipe : recipes){
            JPanel card = createRecipeCard(recipe);
            cardsPanel.add(card);
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createRecipeCard(Recipe recipe){
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        JLabel nameLabel = new JLabel(recipe.getTitle());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        card.add(nameLabel, gbc);

        JButton showButton = new JButton("Show");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        //showButton.addActionListener(e -> ()); WILL IMPLEMENT THIS ONCE THE VIEWING RECIPE USE CASE IS ADDED
        card.add(showButton, gbc);


        JLabel categoryLabel = new JLabel(recipe.getCategory());
        categoryLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        card.add(categoryLabel, gbc);

        return card;
    }
}
