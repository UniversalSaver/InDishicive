package view;

import javax.swing.*;

public class MainView extends JPanel {
    private final JTabbedPane tabbedPane;

    public MainView() {
        tabbedPane = new JTabbedPane();

        tabbedPane.add(new JTextArea("hihello"));

        this.add(tabbedPane);

    }
}
