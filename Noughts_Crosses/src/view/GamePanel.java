
package view;

import controller.BoardController;
import model.AIPlayer;
import model.Player;
import model.XOBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private ControlsPanel controlsPanel;
    private BoardPanel boardPanel;
    private PlayersPanel player1Panel;
    private PlayersPanel player2Panel;
    private DisplayPanel displayPanel;
    private JPanel centerPanel;

    BoardController boardController;

        public GamePanel() {
            setLayout(new BorderLayout(0,0));
            initializeComponents();
        }

        private void initializeComponents() {
            // Top: Control panel
            controlsPanel = new ControlsPanel(e->modeButton(),e -> resizeButton(), e -> resetGame());
            add(controlsPanel, BorderLayout.SOUTH);

            XOBoard board = new XOBoard(3, 3);
            boardController = new BoardController(board,false);

            // Left: Player 1
            player1Panel = new PlayersPanel(boardController.getPlayers().get(0));
            add(player1Panel, BorderLayout.WEST);

            // Right: Player 2
            player2Panel = new PlayersPanel(boardController.getPlayers().get(1));
            add(player2Panel, BorderLayout.EAST);

            // Center: Board panel
            centerPanel = new JPanel();
            resetGame();
        }
        //modebutton
        private void modeButton() {
            resetGame();
        }

        //Thay đổi chế đố 3x3, 15x15
        private void resizeButton() {
            Player player1 = boardController.getPlayers().get(0);
            Player player2 = boardController.getPlayers().get(1);
            resetGame();
            boardController.getPlayers().set(0, player1);
            boardController.getPlayers().set(1, player2);
            boardController.setActualPlayer(player1);
            player1Panel.updateScore(boardController.getPlayers().get(0));
            player2Panel.updateScore(boardController.getPlayers().get(1));
//            revalidate();
            repaint();
        }

        //Làm mới điểm số
        private void refreshButton(){
            boardPanel.refresh();
            //revalidate();
            repaint();
        }

        //Làm mới toàn bộ board và điểm số
        private void resetGame() {
            int size = controlsPanel.getSelectedSize();
            int winCondition = size == 3 ? 3 : 5;
            int cellSize = size == 3 ? 60 : 40;
            if (centerPanel != null) {
                remove(centerPanel);// xoá panel cũ
            }
            centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());

            //mode
            boolean mode = controlsPanel.getSelectedMode();

            XOBoard board = new XOBoard(size, winCondition);
            boardController = new BoardController(board,mode);
            displayPanel = new DisplayPanel(boardController.getActualPlayer());
            centerPanel.add(displayPanel, BorderLayout.NORTH);

            /* kiem tra handleClick*/
            boardPanel = new BoardPanel(size, cellSize, boardController,e -> handleButtonClick(e));
            centerPanel.add(boardPanel, BorderLayout.CENTER);
            add(centerPanel,BorderLayout.CENTER);

            // Cập nhật tên và tỉ số
            player1Panel.updateScore(boardController.getPlayers().get(0));
            player2Panel.updateScore(boardController.getPlayers().get(1));

            revalidate();
            repaint();
        }

        private void handleButtonClick(ActionEvent e) {
            String[] coords = e.getActionCommand().split(",");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);

            if (boardController.checkMove(row, col)) {
                char symbol = boardController.getActualPlayer().getCurrentSymbol();
                boardPanel.updateButton(row, col, symbol);
                if (boardController.checkWinCondition(row, col)) {
                    boardController.updateScore();
                    player1Panel.updateScore(boardController.getPlayers().get(0));
                    player2Panel.updateScore(boardController.getPlayers().get(1));
                    showWinMessage(boardController.getActualPlayer().getPlayerName());
                    return;
                }
                if (boardController.isFull()) {
                    showDrawMessage();
                    return;
                }
                boardController.switchPlayer();
                displayPanel.setDisplayText(boardController.getActualPlayer());
                //them
                if (boardController.getActualPlayer() instanceof AIPlayer) {
                    int[] aiMove = ((AIPlayer)boardController.getActualPlayer()).getNextMove(boardController);
                    performAIMove(aiMove[0], aiMove[1]);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ô [" + row + "," + col + "] không hợp lệ!");
            }
        }
    private void performAIMove(int row, int col) {
        if (boardController.checkMove(row, col)) {
            char symbol = boardController.getActualPlayer().getCurrentSymbol();
            boardPanel.updateButton(row, col, symbol);

            if (boardController.checkWinCondition(row, col)) {
                boardController.updateScore();
                player1Panel.updateScore(boardController.getPlayers().get(0));
                player2Panel.updateScore(boardController.getPlayers().get(1));
                showWinMessage(boardController.getActualPlayer().getPlayerName());
                return;
            }
            if (boardController.isFull()) {
                showDrawMessage();
                return;
            }
            boardController.switchPlayer();
            displayPanel.setDisplayText(boardController.getActualPlayer());
        }
    }

        private void showWinMessage(String player) {
            JOptionPane.showMessageDialog(this, "Người chơi " + player + " thắng!");
            boardController.switchPlayer();
            displayPanel.setDisplayText(boardController.getActualPlayer());
            if(boardController.isAIMode()) resizeButton();
            else
            refreshButton();
        }

        private void showDrawMessage() {
            JOptionPane.showMessageDialog(this, "Hòa!");
            boardController.switchPlayer();
            displayPanel.setDisplayText(boardController.getActualPlayer());
            if(boardController.isAIMode()) resizeButton();
            else
                refreshButton();
        }
    }




