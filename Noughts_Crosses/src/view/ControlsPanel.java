package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlsPanel extends JPanel {
    private JComboBox<String> modeCombo;
    private JComboBox<String> sizeCombo;
    private JButton resetButton;
    private JButton refreshButton;

    public ControlsPanel(ActionListener sizeListener, ActionListener resetListener, ActionListener refreshListener) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 50));

        modeCombo = new JComboBox<>(new String[]{"Choi voi nguoi", "Choi voi may"});
        add(modeCombo);

        sizeCombo = new JComboBox<>(new String[]{"3x3", "15x15"});
        sizeCombo.addActionListener(sizeListener);
        add(sizeCombo);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(resetListener);
        add(resetButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(refreshListener);
        add(refreshButton);
    }
    public int getSelectedSize(){
        return sizeCombo.getSelectedIndex() == 0 ? 3 : 15;
    }
}
