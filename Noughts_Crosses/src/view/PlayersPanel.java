package view;

import model.Player;

import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class PlayersPanel extends JPanel {
    private JLabel nameField;
    private JLabel playersImage;
    private JLabel scoreLabel;

    public PlayersPanel(Player player){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(150, 50));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        //Avt
        ImageIcon icon = new ImageIcon("user.png");
        icon.setImage(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        playersImage = new JLabel(icon);
        c.gridx = 0;
        c.gridy = 0;
        add(playersImage, c);

        //Ten nguoi choi
        nameField = new JLabel(player.getPlayerName() + "(" + player.getCurrentSymbol() + ")",JLabel.CENTER);
        //c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        add(nameField, c);

        //Diem so
        scoreLabel = new JLabel(""+player.getScore());
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        add(scoreLabel, c);

    }

    void updateScore(Player player){
        scoreLabel.setText(""+player.getScore());
        //System.out.println(player.getScore());
    }
}
