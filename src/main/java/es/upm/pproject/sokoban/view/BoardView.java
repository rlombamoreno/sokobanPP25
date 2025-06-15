package es.upm.pproject.sokoban.view;
import javax.swing.*;
import java.awt.*;
import es.upm.pproject.sokoban.model.Board;

public class BoardView extends JPanel {
    private transient Board board;

    public BoardView(Board board) {
        this.board = board;
        setLayout(new GridLayout(board.getHeight(),board.getWidth()));
        renderBoard();
    }

    public void updateBoard(Board newBoard) {
        this.board = newBoard;
        removeAll(); // Eliminar todas las celdas antes de actualizar

        renderBoard(); // Volver a pintar el tablero

        revalidate();
        repaint();
    }

    private void renderBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                add(new CellView(board.getCell(i, j))); // Crear nuevas celdas
            }
        }
    }
}