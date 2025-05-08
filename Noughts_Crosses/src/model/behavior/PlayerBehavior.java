package model.behavior;

import controller.BoardController;

public interface PlayerBehavior {
    int[] getNextMove(BoardController boardController);
}