package es.upm.pproject.sokoban.view;

import java.awt.BorderLayout;
import javax.swing.*;

import es.upm.pproject.sokoban.controller.GameController;
import es.upm.pproject.sokoban.controller.InputHandler;

public class GameView extends JFrame {
    private GameController gameController;
    private BoardView boardView;
    private int levelCount;
    private JLabel scoreLabel;

    public GameView(GameController gameController, int levelCount) {
    	this.levelCount = levelCount;
        this.gameController = gameController;
        setTitle("NIVEL " + gameController.getGame().getCurrentLevelNumber() + " - Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new MenuView(gameController, this), BorderLayout.NORTH); // Menú
        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        topPanel.add(scoreLabel, BorderLayout.SOUTH); // Puntuación

        add(topPanel, BorderLayout.NORTH);
        
        boardView = new BoardView(gameController.getMovementController().getCurrentBoard());
        add(boardView, BorderLayout.CENTER);

        // Capturar eventos de teclado con InputHandler
        addKeyListener(new InputHandler(gameController, this,levelCount));

        setFocusable(true);
        requestFocusInWindow();
    }

    public void updateBoard() {
        boardView.updateBoard(gameController.getMovementController().getCurrentBoard());
        scoreLabel.setText(getScoreText());
    }

	public void setBoardView(BoardView boardView2) {
		this.boardView = boardView2;
		add(boardView, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	private String getScoreText() {
        int levelScore = gameController.getGame().getCurrentLevel().getLevelScore();
        int totalScore = gameController.getGame().getTotalScore();
        return "Puntuación Nivel: " + levelScore + " | Puntuación Total: " + totalScore;
    }
	}
