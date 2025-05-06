package controller;

import model.Player;
import model.XOBoard;
import model.logic.gameLogic;

import static model.XOBoard.playerAmount;

import java.util.ArrayList;

public class BoardController {
    private  XOBoard board;
    private ArrayList<Player> players;
    private Player actualPlayer;

    public BoardController(XOBoard board) {
        this.board = board;
        generatePlayer();
    }

    private void generatePlayer(){
        players = new ArrayList<>();
        for(int i = 1; i <= playerAmount;i++){
            Player player = new Player(i);
            this.players.add(player);
        }
        actualPlayer = players.get(0);
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

    private void setMove(int row, int col) {
        board.getBoard().get(row).set( col , actualPlayer.getCurrentSymbol());
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
            actualPlayer.setScore(actualPlayer.getScore() + 1);
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

    public void switchPlayer() {
        actualPlayer = actualPlayer == players.get(0) ? players.get(1) : players.get(0);
    }

    public void reset() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.getBoard().get(i).set(j,' ');
            }
        }
    }
}
