package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlsPanel extends JPanel {
    private JComboBox<String> modeCombo;
    private JComboBox<String> sizeCombo;
    private JButton resetButton;

    public ControlsPanel(ActionListener modeListener,ActionListener sizeListener, ActionListener resetListener) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 50));

        modeCombo = new JComboBox<>(new String[]{"WITH PEOPLE", "WITH COMPUTER"});
        modeCombo.addActionListener(modeListener);
        add(modeCombo);

        sizeCombo = new JComboBox<>(new String[]{"3x3", "15x15"});
        sizeCombo.addActionListener(sizeListener);
        add(sizeCombo);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(resetListener);
        add(resetButton);

    }
    public int getSelectedSize(){
        return sizeCombo.getSelectedIndex() == 0 ? 3 : 15;
    }
    public boolean getSelectedMode(){
        return modeCombo.getSelectedIndex() != 0;
    }
}
