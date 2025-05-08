package model;

import controller.BoardController;

public class AIPlayer extends Player {
    private PlayerBehavior behavior;

    public AIPlayer(int order) {
        super(order);
        this.setPlayerName("AI Player");
        this.behavior = new AIPlayerBehavior();
    }

    public int[] getNextMove(BoardController xoController) {
        return behavior.getNextMove(xoController);
    }
}