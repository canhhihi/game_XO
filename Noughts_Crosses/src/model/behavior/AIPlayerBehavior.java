package model.behavior;

import controller.BoardController;
import model.behavior.PlayerBehavior;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerBehavior implements PlayerBehavior {
    @Override
    public int[] getNextMove(BoardController xoController) {
        int size = xoController.getXOBoard().getSize();
        char aiSymbol = xoController.getActualPlayer().getCurrentSymbol();
        char opponentSymbol = (aiSymbol == 'X') ? 'O' : 'X';

        // Lưu danh sách ô trống và điểm
        List<int[]> emptyCells = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (xoController.getXOBoard().getBoard().get(i).get(j) == ' ') {
                    int score = evaluateCell(i, j, xoController, aiSymbol, opponentSymbol);
                    emptyCells.add(new int[]{i, j});
                    scores.add(score);
                }
            }
        }

        // Chọn ô có điểm cao nhất
        int maxScore = Integer.MIN_VALUE;
        int[] bestMove = emptyCells.get(0);
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > maxScore) {
                maxScore = scores.get(i);
                bestMove = emptyCells.get(i);
            }
        }

        return bestMove;
    }

    private int evaluateCell(int row, int col, BoardController xoController, char aiSymbol, char opponentSymbol) {
        int size = xoController.getXOBoard().getSize();
        int score = 0;

        // 1. Kiểm tra nước thắng ngay
        xoController.getXOBoard().getBoard().get(row).set(col, aiSymbol);
        if (xoController.checkWinCondition(row, col)) {
            xoController.getXOBoard().getBoard().get(row).set(col, ' ');
            return 100000; // Ưu tiên tuyệt đối
        }
        xoController.getXOBoard().getBoard().get(row).set(col, ' ');

        // 2. Kiểm tra chặn đối thủ thắng
        xoController.getXOBoard().getBoard().get(row).set(col, opponentSymbol);
        if (xoController.checkWinCondition(row, col)) {
            xoController.getXOBoard().getBoard().get(row).set(col, ' ');
            return 10000; // Chặn thắng ngay
        }
        xoController.getXOBoard().getBoard().get(row).set(col, ' ');

        // 3. Đánh giá tiềm năng thắng/chặn (hàng, cột, đường chéo)
        int[] lineScores = new int[4];
        lineScores[0] = evaluateLine(row, col, 0, 1, xoController, aiSymbol, opponentSymbol); // Hàng
        lineScores[1] = evaluateLine(row, col, 1, 0, xoController, aiSymbol, opponentSymbol); // Cột
        lineScores[2] = evaluateLine(row, col, 1, 1, xoController, aiSymbol, opponentSymbol); // Đường chéo chính
        lineScores[3] = evaluateLine(row, col, 1, -1, xoController, aiSymbol, opponentSymbol); // Đường chéo phụ

        // 4. Kiểm tra tình huống buộc thắng (hai chuỗi tiềm năng giao nhau)
        int aiPotentialCount = 0;
        for (int s : lineScores) {
            if (s >= 4000 && s < 10000) { // Chuỗi 4 hoặc 3 của AI
                aiPotentialCount++;
            }
            score += s;
        }
        if (aiPotentialCount >= 2) {
            score += 20000; // Buộc thắng: hai chuỗi tiềm năng
        }

        // 5. Ưu tiên vị trí
        int center = size / 2;
        if (row == center && col == center) {
            score += 50; // Trung tâm
        } else if ((row == 0 || row == size - 1) && (col == 0 || col == size - 1)) {
            score += 30; // Góc
        } else if (row == center || col == center) {
            score += 20; // Cạnh gần trung tâm
        }

        return score;
    }

    private int evaluateLine(int row, int col, int dRow, int dCol, BoardController xoController, char aiSymbol, char opponentSymbol) {
        int size = xoController.getXOBoard().getSize();
        int aiCount = 0, oppCount = 0, emptyCount = 0;
        boolean openStart = false, openEnd = false;

        // Kiểm tra đoạn 7 ô (rộng hơn để phát hiện chuỗi mở)
        List<int[]> lineCells = new ArrayList<>();
        for (int i = -5; i <= 5; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < size && c >= 0 && c < size) {
                lineCells.add(new int[]{r, c});
            }
        }

        // Đếm ký tự trong đoạn 5 ô liên tiếp
        int maxScore = 0;
        for (int i = 0; i < lineCells.size() - 4; i++) {
            aiCount = 0;
            oppCount = 0;
            emptyCount = 0;
            List<int[]> window = lineCells.subList(i, Math.min(i + 5, lineCells.size()));
            if (window.size() < 5) continue;

            for (int[] cell : window) {
                char value = xoController.getXOBoard().getBoard().get(cell[0]).get(cell[1]);
                if (value == aiSymbol) aiCount++;
                else if (value == opponentSymbol) oppCount++;
                else emptyCount++;
            }

            // Kiểm tra tính mở của chuỗi
            openStart = false;
            openEnd = false;
            if (i > 0) {
                int[] prevCell = lineCells.get(i - 1);
                if (xoController.getXOBoard().getBoard().get(prevCell[0]).get(prevCell[1]) == ' ') {
                    openStart = true;
                }
            }
            if (i + 5 < lineCells.size()) {
                int[] nextCell = lineCells.get(i + 5);
                if (xoController.getXOBoard().getBoard().get(nextCell[0]).get(nextCell[1]) == ' ') {
                    openEnd = true;
                }
            }

            // Chấm điểm dựa trên số ký tự
            int score = 0;
            if (oppCount == 4 && emptyCount == 1) score = 10000; // Chặn đối thủ thắng
            else if (oppCount == 3 && emptyCount == 2) {
                score = (openStart && openEnd) ? 7000 : 6000; // Chặn chuỗi 3
            } else if (oppCount == 2 && emptyCount == 3) {
                score = (openStart && openEnd) ? 1500 : 1000; // Chặn chuỗi 2
            } else if (aiCount == 4 && emptyCount == 1) {
                score = (openStart && openEnd) ? 9000 : 8000; // Tạo chuỗi 4
            } else if (aiCount == 3 && emptyCount == 2) {
                score = (openStart && openEnd) ? 6000 : 5000; // Tạo chuỗi 3
            } else if (aiCount == 2 && emptyCount == 3) {
                score = (openStart && openEnd) ? 1000 : 800; // Tạo chuỗi 2
            }

            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }
}