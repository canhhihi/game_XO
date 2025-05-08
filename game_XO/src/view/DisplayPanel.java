package view;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private JLabel displayLabel;

    public DisplayPanel(Player player) {
        this.setPreferredSize(new Dimension(100, 50));
        this.setLayout(new FlowLayout());
        displayLabel = new JLabel(player.getPlayerName() + "'s turn");
        displayLabel.setFont(new Font("Arial", Font.BOLD, 25));
        this.add(displayLabel);
    }

    public void setDisplayText(Player player) {
        displayLabel.setText(player.getPlayerName() + "'s turn");
        displayLabel.setForeground( (player.getCurrentSymbol() == 'X') ? Color.decode("#27AE60") : Color.decode("#F39C12"));
    }

}
