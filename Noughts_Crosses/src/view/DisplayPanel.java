package view;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private JLabel displayLabel;

    public DisplayPanel(Player player) {
        this.setPreferredSize(new Dimension(100, 50));
        this.setLayout(new FlowLayout());
        displayLabel = new JLabel("Lượt của " + player.getPlayerName());
        displayLabel.setFont(new Font("Arial", Font.BOLD, 25));
        displayLabel.setForeground(Color.red);

        this.add(displayLabel);
    }

    public void setDisplayText(Player player) {
        displayLabel.setText("Lượt của " + player.getPlayerName());
    }
}
