package view.user_recipe_view;

import interface_adapter.add_recipe.SwitchViewController;
import interface_adapter.view_recipes.UserRecipesViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesView extends JPanel implements PropertyChangeListener {
    private final String viewName;

    private final JScrollPane scrollPane;

    private final UserRecipesViewModel userRecipesViewModel;

    private final JButton addRecipeButton;

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        this.userRecipesViewModel = userRecipesViewModel;
        viewName = UserRecipesViewModel.VIEW_NAME;

        this.addRecipeButton = new JButton("Add Recipe");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JPanel recipes = new JPanel();
        recipes.setLayout(new BoxLayout(recipes, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(recipes);

        recipes.add(new UserRecipeVisual("MacCheese", "A Macaroni and Cheese, mmmmalskjfpawoijefpalksjpdfiajwsodfkjlawiojefpklawjsdfoiajwpkefljapsoidfjapwlkefjapwoidfjpaksjdfpiawjpofjiawfm"));
        recipes.add(new UserRecipeVisual("Lasagna", "Cool recipe"));
        recipes.add(new UserRecipeVisual("Pasta", "Hot Pasta"));
        recipes.add(new UserRecipeVisual("Pizza", "That's right the lasagna is cold"));
        recipes.add(new UserRecipeVisual("Stroganoff", "What a neat word"));

        this.add(scrollPane);

        JPanel addRecipesPanel = new JPanel();
        addRecipesPanel.setLayout(new BorderLayout());

        addRecipesPanel.add(new JLabel("Have 3 recipes"), BorderLayout.LINE_START);
        addRecipesPanel.add(addRecipeButton, BorderLayout.LINE_END);

        this.add(addRecipesPanel);
    }

	public void addViewCreatorUseCase(SwitchViewController switchViewController) {
		this.addRecipeButton.addActionListener(e -> {
			switchViewController.execute();
		});
	}

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
