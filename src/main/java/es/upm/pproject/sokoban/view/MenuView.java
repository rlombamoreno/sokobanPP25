package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import es.upm.pproject.sokoban.controller.GameController;

public class MenuView extends JMenuBar {
    public MenuView(GameController gameController, GameView gameView) {
        JMenu menu = new JMenu("Opciones");

        // Reiniciar nivel (Ctrl+R)
        JMenuItem restartItem = new JMenuItem("Reiniciar");
        restartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        restartItem.addActionListener(e ->{ gameController.restartLevel();
			gameView.updateBoard(); 
		});

        // Deshacer movimiento (Ctrl+Z)
        JMenuItem undoItem = new JMenuItem("Deshacer");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
        undoItem.addActionListener(e -> {gameController.undoLastMove();gameView.updateBoard();});

        // Guardar partida (Ctrl+G)
        JMenuItem saveItem = new JMenuItem("Guardar partida");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
        saveItem.addActionListener(e -> gameController.saveGame("saved_game.txt"));

        // Cargar partida (Ctrl+L)
        JMenuItem loadItem = new JMenuItem("Cargar partida");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
        loadItem.addActionListener(e ->{ gameController.loadSavedGame("saved_game.txt"); gameView.updateBoard(); });

        // Salir del juego (Ctrl+Q)
        JMenuItem quitItem = new JMenuItem("Salir");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        quitItem.addActionListener(e -> System.exit(0));

        // Agregar elementos al menú
        menu.add(restartItem);
        menu.add(undoItem);
        menu.addSeparator(); // Línea separadora
        menu.add(saveItem);
        menu.add(loadItem);
        menu.addSeparator();
        menu.add(quitItem);

        // Agregar menú a la barra
        add(menu);
    }
}
