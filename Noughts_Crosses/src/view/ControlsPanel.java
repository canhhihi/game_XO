package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ControlsPanel extends JPanel {
    private JComboBox<String> modeCombo;
    private JComboBox<String> sizeCombo;
    private JButton resetButton;

    private JButton undoButton;
    private JButton redoButton;

    public ControlsPanel(ActionListener modeListener,ActionListener sizeListener, ActionListener resetListener, ActionListener undoListener, ActionListener redoListener) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 50));

        modeCombo = new JComboBox<>(new String[]{"Choi voi nguoi", "Choi voi may"});
        modeCombo.addActionListener(modeListener);
        add(modeCombo);

        sizeCombo = new JComboBox<>(new String[]{"3x3", "15x15"});
        sizeCombo.addActionListener(sizeListener);
        add(sizeCombo);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(resetListener);
        add(resetButton);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(undoListener);
        undoButton.setToolTipText("Undo last move (Ctrl+Z)");
        //undoButton.setMnemonic(KeyEvent.VK_Z); // Alt+Z shortcut
       undoButton.setEnabled(false);
        add(undoButton);

        // Redo button with icon
        redoButton = new JButton("Redo");
        redoButton.addActionListener(redoListener);
        redoButton.setToolTipText("Redo last undone move (Ctrl+Y)");
        //redoButton.setMnemonic(KeyEvent.VK_Y); // Alt+Y shortcut
        redoButton.setEnabled(false);
        add(redoButton);

    }
    public int getSelectedSize(){
        return sizeCombo.getSelectedIndex() == 0 ? 3 : 15;
    }
    public boolean getSelectedMode(){
        return modeCombo.getSelectedIndex() == 0 ? false : true;}


    public void setUndoEnabled(boolean enabled) {
        undoButton.setEnabled(enabled);
    }

    public void setRedoEnabled(boolean enabled) {
        redoButton.setEnabled(enabled);
    }

}
