package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.event.KeyEvent;
import es.upm.pproject.sokoban.controller.GameController;

public class MenuView extends JMenuBar {
    private GameController gameController;
    private GameView gameView;

    public MenuView(GameController gameController, GameView gameView) {
        this.gameController = gameController;
        this.gameView = gameView;

        JMenu menu = new JMenu("Opciones");

        // Reiniciar nivel (R)
        JMenuItem restartItem = new JMenuItem("Reiniciar");
        restartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        restartItem.addActionListener(e -> {
            gameController.restartLevel();
            gameView.updateBoard();
        });

        // Deshacer movimiento (U)
        JMenuItem undoItem = new JMenuItem("Deshacer (U)");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
        undoItem.addActionListener(e -> {
            gameController.undoLastMove();
            gameView.updateBoard();
        });

        // Guardar partida (G)
        JMenuItem saveItem = new JMenuItem("Guardar partida");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
        saveItem.addActionListener(e -> gameController.saveGame("saved_game.txt"));

        // Cargar partida (L)
        JMenuItem loadItem = new JMenuItem("Cargar partida");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
        loadItem.addActionListener(e -> {
            gameController.loadSavedGame("saved_game.txt");
            gameView.updateBoard();
        });

        // Volver al menú principal (Q)
        JMenuItem quitItem = new JMenuItem("Volver al Menú Principal");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        quitItem.addActionListener(e -> returnToMainMenu());

        // Agregar elementos al menú
        menu.add(restartItem);
        menu.add(undoItem);
        menu.addSeparator();
        menu.add(saveItem);
        menu.add(loadItem);
        menu.addSeparator();
        menu.add(quitItem);

        // Agregar menú a la barra
        add(menu);
    }

    private void returnToMainMenu() {
        gameView.dispose(); // Cierra la ventana del juego
        MainMenuView mainMenu = new MainMenuView(gameController);
        mainMenu.setVisible(true); // Abre el menú principal
    }
}
