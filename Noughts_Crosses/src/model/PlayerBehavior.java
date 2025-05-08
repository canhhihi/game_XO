package model;

import controller.BoardController;

public interface PlayerBehavior {
    int[] getNextMove(BoardController boardController);
}