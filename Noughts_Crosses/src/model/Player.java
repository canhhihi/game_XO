package model;

import java.util.ArrayList;

public class Player {

    private String playerName;
    //private ArrayList< ArrayList<Integer> > yourMove;
    private char currentSymbol;
    private int isOrder;
    //private boolean isTurn;
    private int score;

    /*
        getter, setter and cntructor
     */
    public Player(int Order) {
        this.playerName = "Player " + Order;
        //this.yourMove = new ArrayList<ArrayList<Integer>>();
        this.currentSymbol = (Order %2 == 0) ? 'O' : 'X';
        this.isOrder = Order;
        this.score = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public char getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(char currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
