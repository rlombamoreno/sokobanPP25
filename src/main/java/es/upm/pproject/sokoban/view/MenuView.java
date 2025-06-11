package es.upm.pproject.sokoban.view;

import javax.swing.*;
import es.upm.pproject.sokoban.controller.GameController;

public class MenuView extends JPanel {
    public MenuView(GameController gameController) {
        JButton restartButton = new JButton("Reiniciar");
        JButton undoButton = new JButton("Deshacer");
        JButton quitButton = new JButton("Salir");

        restartButton.addActionListener(e -> {
            gameController.restartLevel();
        });

        undoButton.addActionListener(e -> {
            gameController.undoLastMove();
        });

        quitButton.addActionListener(e -> System.exit(0));

        add(restartButton);
        add(undoButton);
        add(quitButton);
    }
}