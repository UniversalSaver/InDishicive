package app;

import javax.swing.*;

import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.add_recipe.SwitchViewController;
import interface_adapter.add_recipe.ViewCreatorPresenter;
import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.UserRecipeWindowModel;
import use_case.view_recipe_creator.ViewCreatorInteractor;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import view.user_recipe_view.AddRecipeView;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;
import window.*;

import java.awt.*;

/**
 * An object that will build the app given what windows to include
 */
public class AppBuilder {

    private MainWindow mainWindow;

    private MainView mainView;


    /*
    Start of the UserRecipe variables
     */

    private UserRecipesWindow userRecipesWindow;
    private UserRecipeWindowModel userRecipeWindowModel;


    private UserRecipesView userRecipesView;
    private UserRecipesViewModel userRecipesViewModel;

	private AddRecipeView addRecipeView;
	private AddRecipeViewModel addRecipeViewModel;


    private final UserRecipesViewManagerModel userRecipesViewManagerModel = new UserRecipesViewManagerModel();
    private UserRecipesViewManager userRecipesViewManager;

    private final JPanel userRecipeCardPanel = new JPanel();
    private final CardLayout userRecipeCardLayout = new CardLayout();

    /*
    End of UserRecipes Variables
     */


    public AppBuilder addMainView() {
        mainView = new MainView();
        this.mainWindow.addMainView(mainView);

        return this;
    }

    public AppBuilder addMainWindow() {
        mainWindow = new MainWindow("Indishisive");

        return this;
    }

    public AppBuilder addProfileMenu() {
        mainWindow.addProfileMenu();
        return this;
    }

    /*
    Start of UserRecipe Methods
     */

    public AppBuilder addUserRecipesWindow() {
        this.userRecipeCardPanel.setLayout(userRecipeCardLayout);

        this.userRecipesViewManager = new UserRecipesViewManager(this.userRecipeCardLayout,
                this.userRecipeCardPanel, this.userRecipesViewManagerModel);

        this.userRecipeWindowModel = new UserRecipeWindowModel();

        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel, userRecipeCardLayout,
                userRecipesViewManager, userRecipesViewManagerModel, userRecipeWindowModel);


        return this;
    }

    public AppBuilder addUserRecipesView() {
        this.userRecipesViewModel = new UserRecipesViewModel();

        userRecipesView = new UserRecipesView(userRecipesViewModel);

        this.userRecipeCardPanel.add(this.userRecipesView, userRecipesView.getViewName());
        this.userRecipeWindowModel.addPropertyChangeListener(this.userRecipesWindow);

        userRecipesWindow.addUserRecipesView(userRecipesView, userRecipesViewModel);
        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

	public AppBuilder addAddRecipeView() {
		this.addRecipeViewModel = new AddRecipeViewModel();

		this.addRecipeView = new AddRecipeView(addRecipeViewModel);

		this.userRecipeCardPanel.add(this.addRecipeView, AddRecipeViewModel.VIEW_NAME);

		return this;
	}

	public AppBuilder addViewCreatorUseCase() {
		ViewCreatorPresenter viewCreatorPresenter = new ViewCreatorPresenter(this.userRecipesViewManagerModel,
				this.addRecipeViewModel);

		ViewCreatorInteractor viewCreatorInteractor = new ViewCreatorInteractor(viewCreatorPresenter);
		SwitchViewController switchViewController = new SwitchViewController(viewCreatorInteractor);

		userRecipesView.addViewCreatorUseCase(switchViewController);

		return this;
	}

    /*
    End of UserRecipe methods
     */

    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
