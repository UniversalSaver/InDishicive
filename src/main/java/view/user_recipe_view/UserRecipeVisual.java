package view.user_recipe_view;

import javax.swing.*;
import java.awt.*;

public class UserRecipeVisual extends JPanel {
    private String title;
    private String description;

    private JPanel infoPanel = new JPanel();
    private JPanel infoPanelNames = new JPanel();
    private JPanel infoPanelData = new JPanel();


    private JPanel infoPanelButtons = new JPanel();
    private JButton deleteButton = new JButton("Delete");
    private JButton viewButton = new JButton("View");

    public UserRecipeVisual(String title, String description) {
        this.title = title;
        this.description = description;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(600, 100));

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setMaximumSize(new Dimension(550, 100));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        infoPanelNames.setLayout(new BoxLayout(infoPanelNames, BoxLayout.Y_AXIS));

        infoPanelNames.add(new JLabel("Title:"));
        infoPanelNames.add(new JLabel("Description:"));
        infoPanelNames.add(Box.createVerticalGlue());
        infoPanelNames.add(Box.createHorizontalGlue());
        infoPanel.add(infoPanelNames);



        infoPanelData.setLayout(new BoxLayout(infoPanelData, BoxLayout.Y_AXIS));
        infoPanelData.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanelData.add(titleLabel);

        JTextArea descriptionTextArea = new JTextArea(5, 25);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setText(this.description);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanelData.add(descriptionScrollPane);

        infoPanel.add(infoPanelData);
        this.add(infoPanel);



        infoPanelButtons.setLayout(new BoxLayout(infoPanelButtons, BoxLayout.Y_AXIS));

        this.deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanelButtons.add(this.deleteButton);
        infoPanelButtons.add(this.viewButton);

        this.add(infoPanelButtons);
    }
}
