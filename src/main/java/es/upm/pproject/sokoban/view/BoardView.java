package es.upm.pproject.sokoban.view;
import javax.swing.*;
import java.awt.*;
import es.upm.pproject.sokoban.model.Board;
import es.upm.pproject.sokoban.model.Cell;

public class BoardView extends JPanel {
    private Board board;

    public BoardView(Board board) {
        this.board = board;
        setLayout(new GridLayout(board.getHeight(),board.getWidth()));
        renderBoard();
    }

    public void updateBoard(Board newBoard) {
        this.board = newBoard;
        renderBoard();
    }

    private void renderBoard() {
        removeAll();
        for (int i = 0; i < board.getHeight(); i++) {
          for (int j = 0; j < board.getWidth(); j++) {
            Cell cell = board.getCell(i, j);
            add(new CellView(cell));
          }
        }
        revalidate();
        repaint();
    }
}