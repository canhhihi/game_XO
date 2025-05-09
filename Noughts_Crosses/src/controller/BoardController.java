package controller;

import model.Player;
import model.XOBoard;
import model.logic.gameLogic;

import model.AIPlayer;

import java.util.*;

public class BoardController {
    private  XOBoard board;
    private ArrayList<Player> players;
    private Player actualPlayer;
    private boolean isAIMode;
    private Stack<MoveHistory> undoMove;
    private Deque<MoveHistory> redoMove;


public BoardController(XOBoard board, boolean isAiMode) {
    this.board = board;
    this.isAIMode = isAiMode;
    this.players = new ArrayList<>();
    this.undoMove = new Stack<>();
    this.redoMove = new LinkedList<>();
    generatePlayer();
}

private void generatePlayer(){
    players.add(new Player(1)); // Người chơi 1 luôn là người
    players.add(isAIMode ? new AIPlayer(2) : new Player(2)); // Người chơi 2 là AI nếu isAIMode
    actualPlayer = players.getFirst();
}

public XOBoard getXOBoard() {
    return board;
}

public void setXOBoard(XOBoard board) {
    this.board = board;
}

public ArrayList<Player> getPlayers() {
    return players;
}

public void setPlayers(ArrayList<Player> players) {
    this.players = players;
}

public Player getActualPlayer() {
    return actualPlayer;
}

public void setActualPlayer(Player actualPlayer) {
    this.actualPlayer = actualPlayer;
}

public boolean isAIMode() {
    return isAIMode;
}

private void setMove(int row, int col) {
    board.getBoard().get(row).set( col , actualPlayer.getCurrentSymbol());
    undoMove.push(new MoveHistory(row,col,actualPlayer));
    redoMove.clear();
}

public boolean checkMove(int row, int col) {
    int size = board.getSize();
    if (row < 0 || row >= size || col < 0 || col >= size || board.getBoard().get(row).get(col) != ' ') {
        return false;
    }
    setMove(row, col);
    return true;
}

public boolean checkWinCondition(int row, int col) {
    gameLogic checkWin = new gameLogic(this.board);
    if(checkWin.checkCol(row,col) || checkWin.checkRow(row,col) || checkWin.checkDiagonalM(row,col) || checkWin.checkDiagonalS(row,col)){
        return true;
    }
    return false;
}

public boolean isFull() {
    for (int i = 0; i < this.board.getSize(); i++) {
        for (int j = 0; j < this.board.getSize(); j++) {
            if (this.board.getBoard().get(i).get(j) == ' ') return false;
        }
    }
    return true;
}

public void updateScore(){
    actualPlayer.setScore(actualPlayer.getScore() + 1);
}

public void switchPlayer() {
    actualPlayer = actualPlayer == players.get(0) ? players.get(1) : players.get(0);
}

public void reset() {
    for (int i = 0; i < board.getSize(); i++) {
        for (int j = 0; j < board.getSize(); j++) {
            board.getBoard().get(i).set(j,' ');
        }
    }

    /**
     * check it
     */
    redoMove.clear();
    undoMove.clear();
}

    public MoveHistory undoLastMove() {
        if (undoMove.isEmpty()) {
            return null;
        }
        // Get last move from history
        MoveHistory lastMove = undoMove.pop();
        // Clear the cell
        board.getBoard().get(lastMove.getRow()).set(lastMove.getCol(), ' ');
        // Add to redo history
        redoMove.push(lastMove);
        // Set the current player to the one who made the undone move
        actualPlayer = lastMove.getPlayer();
        return lastMove;
    }
    public MoveHistory redoLastMove() {
        if (redoMove.isEmpty()) {
            return null;
        }
        // Get last undone move
        MoveHistory redoLastMove = redoMove.pop();
        // Execute the move again
        board.getBoard().get(redoLastMove.getRow()).set(redoLastMove.getCol(), redoLastMove.getSymbol());
        // Add back to move history
        undoMove.push(redoLastMove);
        // Switch to the next player (since we're redoing a completed move)
        Player movePlayer = redoLastMove.getPlayer();
        //actualPlayer = movePlayer;
        switchPlayer();
        return redoLastMove;
    }

    public boolean canUndo() {
        return !undoMove.isEmpty();
    }

    public boolean canRedo() {
        return !redoMove.isEmpty();
    }

    //tra ve so luot da di
    public int getMoveCount() {
        return undoMove.size();
    }

}
