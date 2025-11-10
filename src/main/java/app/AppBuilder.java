package app;

import javax.swing.*;

import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import window.*;

/**
 * An object that will build the app given what windows to include
 */
public class AppBuilder {

    private MainWindow mainWindow;
    private UserRecipesWindow userRecipesWindow;

    private MainView mainView;
    private UserRecipesView userRecipesView;
    private ViewRecipesViewModel viewRecipesViewModel;

    public AppBuilder addUserRecipesView() {
        userRecipesView = new UserRecipesView();
        viewRecipesViewModel = new ViewRecipesViewModel("view recipes");
        userRecipesWindow = new UserRecipesWindow(this.viewRecipesViewModel);

        userRecipesWindow.addUserRecipesView(userRecipesView);

        return this;
    }

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
        mainWindow.addProfileMenu(userRecipesView);
        return this;
    }

    public AppBuilder addUserRecipesWindow() {
        mainWindow.addUserRecipesWindow(userRecipesWindow);

        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipesWindow, this.viewRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter);

        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
