package es.upm.pproject.sokoban.controller;

import java.util.Scanner;
import es.upm.pproject.sokoban.model.Position.Direction;

public class InputHandler {
    private GameController gameController;

    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    // Método para capturar comandos desde consola
    public void listenForInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command (W: UP, S: DOWN, A: LEFT, D: RIGHT, U: Undo, R: Restart, Q: Quit, G: Save Game, L: Load Game):");
        gameController.displayGameStatus();
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            switch (input) {
                case "W": gameController.movePlayer(Direction.UP); break;
                case "S": gameController.movePlayer(Direction.DOWN); break;
                case "A": gameController.movePlayer(Direction.LEFT); break;
                case "D": gameController.movePlayer(Direction.RIGHT); break;
                case "U": gameController.undoLastMove(); break;
                case "R": gameController.restartLevel(); break;
                case "G": gameController.saveGame("saved_game.txt"); break;
                case "L": gameController.loadSavedGame("saved_game.txt"); break;
                case "Q": 
                    System.out.println("Exiting Sokoban...");
                    scanner.close();
                    return; // Salir del programa
                default:
                    System.out.println("Invalid input. Use W/S/A/D for movement, U to undo, R to restart, G to save, L to load, Q to quit.");
            }
            gameController.displayGameStatus();
            }
        }
}
