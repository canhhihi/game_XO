package model.logic;

import model.XOBoard;

public class gameLogic implements victoryLogic {

    private int winCondition;
    //private char playerSymbol;
    private XOBoard board;


    public gameLogic(XOBoard board) {
        this.board = board;
        this.winCondition = board.getWinCondition();
        //this.playerSymbol =
    }

    @Override
    public boolean checkRow(int row, int col) {

        int count = 0;
        char playerSymbol = board.getBoard().get(row).get(col);
        for (int j = Math.max(0, col - winCondition + 1); j <= Math.min(board.getSize() - 1, col + winCondition - 1); j++) {
            if (board.getBoard().get(row).get(j) == playerSymbol) {
                count++;
                if (count >= winCondition) return true;
            } else {
                count = 0;
            }
        }
        return false;

    }

    @Override
    public boolean checkCol(int row, int col) {
        int count = 0;
        char playerSymbol = board.getBoard().get(row).get(col);
        for (int i = Math.max(0, row - winCondition + 1); i <= Math.min(board.getSize() - 1, row + winCondition - 1); i++) {
            if (board.getBoard().get(i).get(col) == playerSymbol) {
                count++;
                if (count >= winCondition) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Override
    public boolean checkDiagonalM(int row, int col) {

        int count = 0;
        char playerSymbol = board.getBoard().get(row).get(col);
        for (int i = -winCondition + 1; i <= winCondition - 1; i++) {
            int r = row + i, c = col + i;
            if (r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize()) {
                if (board.getBoard().get(r).get(c) == playerSymbol) {
                    count++;
                    if (count >= winCondition) return true;
                } else {
                    count = 0;
                }
            }
        }
        return false;

    }

    @Override
    public boolean checkDiagonalS(int row, int col) {
        int count = 0;
        char playerSymbol = board.getBoard().get(row).get(col);
        for (int i = -winCondition + 1; i <= winCondition - 1; i++) {
            int r = row + i, c = col - i;
            if (r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize()) {
                if (board.getBoard().get(r).get(c) == playerSymbol) {
                    count++;
                    if (count >= winCondition) return true;
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }
}
