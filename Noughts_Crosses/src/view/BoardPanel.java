package view;

import controller.BoardController;
//import controller.XOController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel {
    private BoardController boardcontroller;
    private RoundedButton[][] buttons;
    private int size;
    private int cellSize;

    public BoardPanel(int size, int cellSize, BoardController boardController, ActionListener buttonClickListener) {
        this.size = size;
        this.cellSize = cellSize;
        this.boardcontroller = boardController;
        setLayout(new GridLayout(size, size));
        setPreferredSize(new Dimension(size * cellSize, size * cellSize));
        initialButton(buttonClickListener);
    }
    private void initialButton(ActionListener buttonClickListener) {
        buttons = new RoundedButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int row = i;
                final int col = j;
                RoundedButton button = new RoundedButton(Color.decode("#34495E"), Color.lightGray);
                button.setMargin(new Insets(0, 0, 0, 0));

                button.setPreferredSize(new Dimension(cellSize, cellSize));
                button.setFont(new Font("Arial Black",Font.BOLD,size == 3 ? 150: 15));

                button.addActionListener(e->buttonClickListener.actionPerformed(
                        new ActionEvent(button, row * size + col, row + "," + col)));
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    public void updateButton(int row, int col, char symbol) {
        buttons[row][col].setText(String.valueOf(symbol));
        buttons[row][col].setForeground(symbol=='X' ? Color.decode("#27AE60") : Color.decode("#F39C12"));
    }

    public void refresh() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        boardcontroller.reset();
    }
}

