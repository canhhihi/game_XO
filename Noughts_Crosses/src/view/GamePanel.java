
package view;

import controller.BoardController;
import controller.MoveHistory;
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
        controlsPanel = new ControlsPanel(
                e->modeButton(),
                e -> resizeButton(),
                e -> resetGame(),
                e -> undoButton(),
                e -> redoButton());
        add(controlsPanel, BorderLayout.SOUTH);

        //Game khoi tao
        createBoard();

        // Left: Player 1
        player1Panel = new PlayersPanel(boardController.getPlayers().get(0));
        add(player1Panel, BorderLayout.WEST);

        // Right: Player 2
        player2Panel = new PlayersPanel(boardController.getPlayers().get(1));
        add(player2Panel, BorderLayout.EAST);

        updateUndoButtonState();
    }

    //Tao bang
    private void createBoard(){
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

    }

    //modebutton, thay doi che do
    private void modeButton() {
        resetGame();
    }

    //Thay đổi kich thuoc 3x3, 15x15
    private void resizeButton() {
        Player player1 = boardController.getPlayers().get(0);
        Player player2 = boardController.getPlayers().get(1);

        createBoard();
        boardController.getPlayers().set(0, player1);
        boardController.getPlayers().set(1, player2);
        boardController.setActualPlayer(player1);

        player1Panel.setPlayer(player1);
        player2Panel.setPlayer(player2);
        player1Panel.updateScore();
        player2Panel.updateScore();

        updateUndoButtonState();
        revalidate();
        repaint();
    }

    //Lam moi sau khi game hoa or thang
    private void refreshButton(){
        boardPanel.refresh();
        boardController.switchPlayer();
        if(boardController.isAIMode()) if(boardController.getActualPlayer() instanceof AIPlayer) boardController.switchPlayer();
        displayPanel.setDisplayText(boardController.getActualPlayer());
        updateUndoButtonState();
        repaint();
    }

    //Làm mới toàn bộ board và điểm số
    private void resetGame() {
        createBoard();

        // Cập nhật tên và tỉ số
        player1Panel.setPlayer(boardController.getPlayers().get(0));
        player2Panel.setPlayer(boardController.getPlayers().get(1));

        player1Panel.updateScore();
        player2Panel.updateScore();
        player1Panel.updateName();
        player2Panel.updateName();

        updateUndoButtonState();

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

            updateUndoButtonState();
            if (boardController.checkWinCondition(row, col)) {
                boardController.updateScore();
                player1Panel.updateScore();
                player2Panel.updateScore();
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
        updateUndoButtonState();

        if (boardController.checkWinCondition(row, col)) {
            boardController.updateScore();
            player1Panel.updateScore();
            player2Panel.updateScore();
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
        refreshButton();
    }

    private void showDrawMessage() {
        JOptionPane.showMessageDialog(this, "Hòa!");
            refreshButton();
    }


    /**
     * Undoes the last move made on the board, updating the UI accordingly
     */
    private void undoButton() {
        if (!boardController.canUndo()) {
            return;
        }

        // If in AI mode and there are at least 2 moves, undo both human and AI moves
        if (boardController.isAIMode() && boardController.getMoveCount() >= 2) {
            // Undo AI move first
            MoveHistory aiMove = boardController.undoLastMove();
            if (aiMove != null) {
                boardPanel.updateButton(aiMove.getRow(), aiMove.getCol(), ' ');
                boardController.switchPlayer();
            }

            // Then undo human move
            MoveHistory humanMove = boardController.undoLastMove();
            if (humanMove != null) {
                boardPanel.updateButton(humanMove.getRow(), humanMove.getCol(), ' ');
            }
        } else {
            // Just undo a single move in PvP mode
            MoveHistory lastMove = boardController.undoLastMove();
            if (lastMove != null) {
                boardPanel.updateButton(lastMove.getRow(), lastMove.getCol(), ' ');
            }
        }

        // Update the display panel to show the current player
        displayPanel.setDisplayText(boardController.getActualPlayer());

        // Update undo button state
        updateUndoButtonState();
    }
    private void updateUndoButtonState() {
        controlsPanel.setUndoEnabled(boardController.canUndo());
        controlsPanel.setRedoEnabled(boardController.canRedo());
    }

    private void redoButton() {
        if (!boardController.canRedo()) {
            return;
        }

        // If in AI mode, need to redo both human and AI moves
        if (boardController.isAIMode()) {
            // Redo human move first
            MoveHistory humanMove = boardController.redoLastMove();
            if (humanMove != null) {
                boardPanel.updateButton(humanMove.getRow(), humanMove.getCol(), humanMove.getSymbol());

                // Then redo AI move if available
                if (boardController.canRedo()) {
                    MoveHistory aiMove = boardController.redoLastMove();
                    if (aiMove != null) {
                        boardPanel.updateButton(aiMove.getRow(), aiMove.getCol(), aiMove.getSymbol());
                    }
                }
            }
        } else {
            // Just redo a single move in PvP mode
            MoveHistory lastMove = boardController.redoLastMove();
            if (lastMove != null) {
                boardPanel.updateButton(lastMove.getRow(), lastMove.getCol(), lastMove.getSymbol());
            }
        }

        // Update the display panel to show the current player
        displayPanel.setDisplayText(boardController.getActualPlayer());

        // Update button states
        updateUndoButtonState();
    }


}




