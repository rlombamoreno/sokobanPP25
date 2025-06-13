package es.upm.pproject.sokoban.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import es.upm.pproject.sokoban.model.Position.Direction;
import es.upm.pproject.sokoban.view.GameView;

public class InputHandler extends KeyAdapter {
    private GameController gameController;
    private GameView gameView;

    public InputHandler(GameController gameController, GameView gameView) {
        this.gameController = gameController;
        this.gameView = gameView;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction = null;
        boolean actualizado = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: direction = Direction.UP; break;
            case KeyEvent.VK_S: direction = Direction.DOWN; break;
            case KeyEvent.VK_A: direction = Direction.LEFT; break;
            case KeyEvent.VK_D: direction = Direction.RIGHT; break;
            case KeyEvent.VK_UP: direction = Direction.UP; break;
            case KeyEvent.VK_DOWN: direction = Direction.DOWN; break;
            case KeyEvent.VK_LEFT: direction = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: direction = Direction.RIGHT; break;
        }

        if (direction != null) {
            boolean moved = gameController.movePlayer(direction);
            if (moved) {
                gameView.updateBoard(); // Actualizar la vista después del movimiento
            }
        }
    }
}
