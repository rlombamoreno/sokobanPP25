package es.upm.pproject.sokoban.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.controller.GameController;

public class GameFinishView extends JDialog{
		private GameController gameController;
		private GameView gameView;

	public GameFinishView(GameController gameController, GameView gameView) {
		this.gameController = gameController;
		this.gameView = gameView;
		
		setTitle("¡Juego Terminado!");
        setSize(400, 200);
        setLocationRelativeTo(gameView);
        setLayout(new BorderLayout());

        JLabel message = new JLabel("<html><center><h2>¡Enhorabuena has completado todos los niveles!</h2>"
                + "</center></html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));
        add(message, BorderLayout.CENTER);
        
     // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());        
        JButton exitToMenuButton = new JButton("Salir al menú principal");
        exitToMenuButton.addActionListener(e -> exitToMenu());       
        buttonPanel.add(exitToMenuButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setModal(true);
        setVisible(true);	
	}
	
	private void exitToMenu() {
        gameView.dispose();
        gameController.getGame().setCurrentLevelNumber(1);
    	gameController.getGame().loadLevel(1);
    	gameController.updateBoard();
    	gameController.getGame().restartLevelScore();
        new MainMenuView(gameController).setVisible(true);
        dispose();
    }

}
