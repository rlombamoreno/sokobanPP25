package es.upm.pproject.sokoban;

import es.upm.pproject.sokoban.controller.GameController;
import es.upm.pproject.sokoban.controller.InputHandler;

public class Sokoban {
	public static void main(String[] args) {
        GameController gameController = new GameController();
        InputHandler inputHandler = gameController.getInputHandler();
        inputHandler.listenForInput();
     }
}