package model;

import controller.BoardController;
import model.behavior.AIPlayerBehavior;
import model.behavior.PlayerBehavior;

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