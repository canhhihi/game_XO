//package view;
//
//import controller.BoardController;
//import controller.XOController;
//import model.*;
//
//import java.util.Scanner;
//
//public class viewGame {
//    private BoardController boardC;
//
//    public viewGame(BoardController boardC){
//        this.boardC = boardC;
//    }
//
//    public void boardView(){
//        XOBoard board = boardC.getXOBoard();
//        int size = board.getSize();
//
//        for(int i = 0; i < size; i++){
//            for(int j = 0; j < size; j++){
//
//                if(board.getBoard()!= null && board.getBoard().get(i).get(j) != null){
//                    System.out.print(" " + board.getBoard().get(i).get(j)+" ");
//                }
//
//                else{
//                    System.out.print(" ");
//                }
//                if(j<size-1){
//                    System.out.print("|");
//                }
//            }
//            System.out.println();
//            if (i<size-1) System.out.println(" ---------- ");
//        }
//    }
//    public int[] playerInput(){
//        System.out.print("Enter row, col: ");
//        Scanner scanner = new Scanner(System.in);
//        int row = scanner.nextInt();
//        int col = scanner.nextInt();
//        return new int[]{row, col};
//    }
//}
//
//
