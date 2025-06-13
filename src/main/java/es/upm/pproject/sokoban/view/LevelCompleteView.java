package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.*;
import es.upm.pproject.sokoban.controller.GameController;

public class LevelCompleteView extends JDialog {
    private GameController gameController;
    private GameView gameView;

    public LevelCompleteView(GameController gameController, GameView gameView) {
        this.gameController = gameController;
        this.gameView = gameView;

        setTitle("¡Nivel Completado!");
        setSize(400, 200);
        setLocationRelativeTo(gameView);
        setLayout(new BorderLayout());

        JLabel message = new JLabel("<html><center><h2>¡Nivel Completado!</h2>"
                + "<p>¿Qué quieres hacer ahora?</p></center></html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));
        add(message, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton saveAndMenuButton = new JButton("Guardar y salir al menú");
        saveAndMenuButton.addActionListener(e -> saveAndExit());

        JButton nextLevelButton = new JButton("Ir al siguiente nivel");
        nextLevelButton.addActionListener(e -> goToNextLevel());

        JButton exitToMenuButton = new JButton("Salir al menú principal");
        exitToMenuButton.addActionListener(e -> exitToMenu());

        buttonPanel.add(saveAndMenuButton);
        buttonPanel.add(nextLevelButton);
        buttonPanel.add(exitToMenuButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setModal(true);
        setVisible(true);
    }

    private void saveAndExit() {
        gameController.saveGame("saved_game.txt");
        gameController.restartLevel();
        exitToMenu();
    }

    private void goToNextLevel() {
        gameController.loadSavedGame("level2.txt"); // Cargar siguiente nivel (ajustar lógica según niveles disponibles)
        gameView.updateBoard();
        dispose();
    }

    private void exitToMenu() {
        gameView.dispose();
        new MainMenuView(gameController).setVisible(true);
        dispose();
    }
}
