package view.diet_res_view;

import javax.swing.*;
import java.awt.*;

public class DietResVisual extends JPanel {
    private String title;

    private JPanel infoPanel = new JPanel();
    private JPanel infoPanelNames = new JPanel();
    private JPanel infoPanelData = new JPanel();


    private JPanel infoPanelButtons = new JPanel();
    private JButton deleteButton = new JButton("Delete");
    private JButton addButton = new JButton("Add");

    public DietResVisual(String title) {
        this.title = title;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(600, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.black));


        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setMaximumSize(new Dimension(550, 100));

        infoPanelNames.setLayout(new BoxLayout(infoPanelNames, BoxLayout.Y_AXIS));

        infoPanelNames.add(new JLabel("Ingredient Name:"));
        infoPanelNames.add(Box.createVerticalGlue());
        infoPanelNames.add(Box.createHorizontalGlue());
        infoPanel.add(infoPanelNames);

        infoPanelData.setLayout(new BoxLayout(infoPanelData, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanelData.add(titleLabel);


        infoPanel.add(infoPanelData);
        this.add(infoPanel);



        infoPanelButtons.setLayout(new BoxLayout(infoPanelButtons, BoxLayout.Y_AXIS));

        this.deleteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanelButtons.add(this.deleteButton);

        deleteButton.addActionListener(e -> {
            Container parent = this.getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();

        });


        this.add(infoPanelButtons);
    }

}