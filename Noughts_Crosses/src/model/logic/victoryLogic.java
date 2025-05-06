package model.logic;

public interface victoryLogic {

    public boolean checkRow(int row,int col);
    public boolean checkCol(int col,int row);
    public boolean checkDiagonalM(int row,int col);
    public boolean checkDiagonalS(int row,int col);

}
