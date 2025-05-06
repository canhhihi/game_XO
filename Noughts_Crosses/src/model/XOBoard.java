package model;

import java.util.ArrayList;

public class XOBoard {

    public static final int playerAmount = 2;

    private int size;
    private int winCondition;
    private ArrayList<ArrayList<Character>> board;

    /*

     */
    public XOBoard(int size, int winCondition) {
        this.size = size;
        this.winCondition = winCondition;
        board = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Character> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(' ');
            }
            board.add(row);
        }
    }

    public XOBoard(int size, int winCondition, ArrayList<ArrayList<Character>> board) {
        this.size = size;
        this.winCondition = winCondition;
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public ArrayList<ArrayList<Character>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<Character>> board) {
        this.board = board;
    }

}
