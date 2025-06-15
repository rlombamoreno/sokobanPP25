package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import es.upm.pproject.sokoban.controller.GameController;

public class MainMenuView extends JFrame {
    private GameController gameController;
    private Image titleImage;
    private int levelCount = 0;

    public MainMenuView(GameController gameController) {
        this.gameController = gameController;
        setTitle("Sokoban - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadTitleImage();

        // Panel de fondo con imagen
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (titleImage != null) {
                    g.drawImage(titleImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Panel de instrucciones
        JLabel instructions = new JLabel("<html><center><h2>Bienvenido a Sokoban</h2>"
                + "<p>Usa W, A, S, D, o las flechas para mover al personaje.</p>"
                + "<p>Empuja las cajas hasta los objetivos marcados por una X.</p>"
                + "<p>Presiona U para deshacer un movimiento.</p>"
                + "<p>Presiona R para reiniciar el nivel.</p>"
                + "<p>Presiona Q para salir.</p></center></html>");
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructions.setForeground(Color.BLACK);
        instructions.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(instructions, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Para que los botones no tapen la imagen
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Iniciar Juego");
        startButton.addActionListener(e -> startGame());

        JButton loadButton = new JButton("Cargar Partida");
        loadButton.addActionListener(e -> loadGame());

        JButton exitButton = new JButton("Salir del juego");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTitleImage() {
        try {
            titleImage = ImageIO.read(new File("src/main/resources/title.png"));
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen del título.");
            titleImage = null;
        }
    }

    private void startGame() {
    	if (!hasEnoughLevels()) {
            showErrorScreen();
            return;
        }
        dispose();
        GameView gameView = new GameView(gameController, levelCount);
        gameView.setVisible(true);
    }

    private void loadGame() {
        gameController.loadSavedGame("saved_game.txt");
        dispose();
        GameView gameView = new GameView(gameController, levelCount);
        gameView.setVisible(true);
    }
    
    private boolean hasEnoughLevels() {
        levelCount = 0;
        for (int i = 1; i <= 100; i++) { // Buscar hasta 100 niveles
            File levelFile = new File("level" + i + ".txt");
            if (levelFile.exists()) {
                levelCount++;
            }
        }
        return levelCount >= 3;
    }
    private void showErrorScreen() {
        dispose();
        ErrorView errorView = new ErrorView();
        errorView.setVisible(true);
    }
}
