//package controller;
//
//import model.XOBoard;
//import view.GamePanel;
////import view.viewGame;
//
//
//public class XOController {
//    private BoardController boardcontroller;
//    private GamePanel gamePanel;
//    //private viewGame viewgame;
//
//    public XOController(GamePanel gamePanel,int size, int winCondition) {
//        XOBoard board = new XOBoard(size, winCondition);
//        boardcontroller = new BoardController(board);
//        this.gamePanel = gamePanel;
//    }
//
//    public BoardController getXOcontroller() {
//        return boardcontroller;
//    }
//
//    public void setBoardcontroller(BoardController boardcontroller) {
//        this.boardcontroller = boardcontroller;
//    }
//
//
//
//
//
//
//
//
////    public void play() {
////        System.out.println("XO Game play");
////        viewgame.boardView();
////        while (!boardcontroller.isFull()) {
////            System.out.println(boardcontroller.getActualPlayer().getPlayerName());
////            int input[]=viewgame.playerInput();
////            if (!boardcontroller.checkMove(input[0],input[1])) { continue;}
////            if (boardcontroller.checkWinCondition(input[0],input[1])) {
////                viewgame.boardView();
////                System.out.println("player win is "+boardcontroller.getActualPlayer().getPlayerName());
////                break;
////            }
////            viewgame.boardView();
////            boardcontroller.switchPlayer();
////        }
////    }
//
//}
