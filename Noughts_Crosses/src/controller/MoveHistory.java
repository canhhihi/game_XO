package controller;

import model.Player;

public class MoveHistory {
    private int row;
    private int col;
    private char symbol;
    private Player player;

    public MoveHistory(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.symbol = player.getCurrentSymbol();
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getSymbol() {
        return symbol;
    }

    public Player getPlayer() {
        return player;
    }
}
