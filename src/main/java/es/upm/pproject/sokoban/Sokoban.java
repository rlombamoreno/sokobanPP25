package es.upm.pproject.sokoban;

import javax.swing.SwingUtilities;

import es.upm.pproject.sokoban.controller.GameController;
import es.upm.pproject.sokoban.view.MainMenuView;

public class Sokoban {
	public static void main(String[] args) {
        GameController gameController = new GameController();
        
        SwingUtilities.invokeLater(() -> {
        	MainMenuView mainMenu = new MainMenuView(gameController);
            mainMenu.setVisible(true);
        });
        }
}