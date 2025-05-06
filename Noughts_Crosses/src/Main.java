import view.GamePanel;

import javax.swing.*;
import java.awt.*;

//
//import mdlaf.MaterialLookAndFeel;
//import javax.swing.UIManager;
public class Main {
    public static void main(String[] args) {
//        XOBoard board = new XOBoard(3,3);
//        BoardController boardController = new BoardController(board);
//        XOController xoController = new XOController(boardController);
//        xoController.play();
//            SwingUtilities.invokeLater(() -> {
//                JFrame frame = new JFrame("Cờ Caro");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(600, 600);
//                frame.add(new gamePanel());
//                frame.setVisible(true);
//            });


//            try {
//                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        try {
//            UIManager.setLookAndFeel(new MaterialLookAndFeel());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
            JFrame frame = new JFrame("Tic-Tac-Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1080, 920);
            frame.setBackground(Color.BLACK);
            frame.add(new GamePanel());
            frame.setVisible(true);



    }
}