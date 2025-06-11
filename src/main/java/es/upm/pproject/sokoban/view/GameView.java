package es.upm.pproject.sokoban.view;
import java.awt.BorderLayout;

import javax.swing.*;

import es.upm.pproject.sokoban.controller.GameController;

public class GameView extends JFrame {
  private GameController gameController;
    private BoardView boardView;

    public GameView(GameController gameController) {
        this.gameController = gameController;
        setTitle("Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(new MenuView(gameController), BorderLayout.NORTH);

        boardView = new BoardView(gameController.getMovementController().getCurrentBoard());
        add(boardView, BorderLayout.CENTER);
    }

    public void updateBoard() {
        boardView.updateBoard(gameController.getMovementController().getCurrentBoard());
    }
}