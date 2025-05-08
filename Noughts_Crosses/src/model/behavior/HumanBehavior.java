package model.behavior;

import controller.BoardController;

public class HumanBehavior implements PlayerBehavior {
    @Override

    //Là người nên không cần getMove
    public int[] getNextMove(BoardController xoController){
        return null;
    }
}
