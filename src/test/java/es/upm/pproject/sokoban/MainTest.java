package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.sokoban.model.*;
import es.upm.pproject.sokoban.model.Cell.CellType;
import es.upm.pproject.sokoban.model.Position.Direction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;

class MainTest {
    private Board board;
    //private Box box;
    private final int width = 5;
    private final int height = 5;
    //private final int x = 2;
    //private final int y = 3;
    

    @BeforeEach
    void setUp() {
        board = new Board(width, height);
    }

    
  //--------------------------------------BOARD---------------------------------

    @Test
    void testConstructorValid() {
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        assertNotNull(board.getCells());
        assertEquals(height, board.getCells().length);
        assertEquals(width, board.getCells()[0].length);
        assertNotNull(board.getBoxes());
        assertTrue(board.getBoxes().isEmpty());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = board.getCell(i, j);
                assertFalse(cell.isWall());
                assertFalse(cell.isBox());
                assertFalse(cell.isPlayer());
            }
        }
    }

    @Test
    void testGetSetWidth() {
        board.setWidth(10);
        assertEquals(10, board.getWidth());
    }

    @Test
    void testGetSetHeight() {
        board.setHeight(8);
        assertEquals(8, board.getHeight());
    }

    @Test
    void testGetCellValid() {
        Cell cell = board.getCell(2, 3);
        assertNotNull(cell);
        assertFalse(cell.isWall());
        assertFalse(cell.isBox());
        assertFalse(cell.isPlayer());
    }
    
    @Test
    void testGetCellInvalid1() {
        Board board = new Board(3, 3); 
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getCell(-1, 0);
        });

    }
    @Test
    void testGetCellInvalid2() {
        Board board = new Board(3, 3); 

        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getCell(0, 3);
        });

        
    }
    
    @Test
    void testGetCellInvalid3() {
        Board board = new Board(3, 3); 
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getCell(5, 5);
        });
    }
    
    @Test
    void testGetCellInvalid4() {
        Board board = new Board(3, 3); 
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getCell(2, -1);
        });
    }
    
    @Test
    void testSetCellsReplacesBoardCells() {
        Board board = new Board(2, 2); // crea una board inicial

        Cell[][] newCells = {
            { new Cell(Cell.CellType.EMPTY), new Cell(Cell.CellType.WALL) },
            { new Cell(Cell.CellType.BOX), new Cell(Cell.CellType.PLAYER) }
        };

        board.setCells(newCells);

        assertEquals(" ", board.getCell(0, 0).getContent());
        assertEquals("Wall", board.getCell(0, 1).getContent());
        assertEquals("Box", board.getCell(1, 0).getContent());
        assertEquals("Player", board.getCell(1, 1).getContent());
    }

    @Test
    void testSetCellValidPlayer() {
        Cell playerCell = new Cell(CellType.PLAYER);
        board.setCell(2, 2, playerCell);
        assertTrue(board.getCell(2, 2).isPlayer());
        assertNotNull(board.getWarehouseman());
        assertEquals(2, board.getWarehouseman().getPosition().getX());
        assertEquals(2, board.getWarehouseman().getPosition().getY());
    }

    @Test
    void testSetCellValidBox() {
        Cell boxCell = new Cell(CellType.BOX);
        board.setCell(1, 1, boxCell);
        assertTrue(board.getCell(1, 1).isBox());
        List<Box> boxes = board.getBoxes();
        assertEquals(1, boxes.size());
        assertEquals(1, boxes.get(0).getX());
        assertEquals(1, boxes.get(0).getY());
    }
    
    @Test
    void testSetCellInvalid1() {
        Board board = new Board(3, 3); 
        Cell emptyCell = new Cell(CellType.EMPTY);
        assertThrows(IndexOutOfBoundsException.class, () -> {
        	board.setCell(-1, 1, emptyCell);
        });

    }
    @Test
    void testSetCellInvalid2() {
    	Board board = new Board(3, 3); 
        Cell emptyCell = new Cell(CellType.EMPTY);
        assertThrows(IndexOutOfBoundsException.class, () -> {
        	board.setCell(1, -1, emptyCell);
        });

        
    }
    
    @Test
    void testSetCellInvalid3() {
    	Board board = new Board(3, 3); 
        Cell emptyCell = new Cell(CellType.EMPTY);
        assertThrows(IndexOutOfBoundsException.class, () -> {
        	board.setCell(4, 1, emptyCell);
        });
    }
    
    @Test
    void testSetCellInvalid4() {
    	Board board = new Board(3, 3); 
        Cell emptyCell = new Cell(CellType.EMPTY);
        assertThrows(IndexOutOfBoundsException.class, () -> {
        	board.setCell(1, 4, emptyCell);
        });
    }


    @Test
    void testSetTargetValid() {
        board.setTarget(3, 4);
        // Verify via toString since getTargets is not available
        board.setCell(3, 4, new Cell(CellType.EMPTY));
        String boardStr = board.toString();
        assertTrue(boardStr.contains("*")); // Target is represented by '*'
    }
    
    @Test
    void testSetTargetInValid1() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(-1, 2));
    }
    @Test
    void testSetTargetInValid2() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(2, -1));

    }
    @Test
    void testSetTargetInValid3() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(5, 2)); // i >= height
    }
    
    @Test
    void testSetTargetInValid4() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(2, 5)); // j >= width
    }



    @Test
    void testMovePlayerValid() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertTrue(moved);
        assertFalse(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isPlayer());
        assertEquals(3, board.getWarehouseman().getPosition().getX());
        assertEquals(2, board.getWarehouseman().getPosition().getY());
    }

    @Test
    void testMovePlayerOutOfBounds() {
        board.setCell(0, 0, new Cell(CellType.PLAYER));
        boolean moved = board.movePlayer(Direction.LEFT);
        assertFalse(moved);
        assertTrue(board.getCell(0, 0).isPlayer());
    }
    
    @Test
    void testMovePlayerInvalid() {
    	board.setCell(4, 0, new Cell(CellType.PLAYER));
        boolean moved = board.movePlayer(Direction.LEFT);
        assertFalse(moved);
    }
    
    
    
    @Test
    void testMovePlayerInvalid3() {
    	board.setCell(0, 4, new Cell(CellType.PLAYER));
        boolean moved = board.movePlayer(Direction.UP);
        assertFalse(moved);
    }
    
    @Test
    void testMovePlayerInvalid4() {
    	board.setCell(0, 0, new Cell(CellType.PLAYER));
        boolean moved = board.movePlayer(Direction.LEFT);
        assertFalse(moved);
    }

    @Test
    void testMovePlayerIntoWall() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.setCell(2, 3, new Cell(CellType.WALL));
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertFalse(moved);
        assertTrue(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isWall());
    }

    @Test
    void testMovePlayerPushBoxValid() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.setCell(2, 3, new Cell(CellType.BOX));
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertTrue(moved);
        assertFalse(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isPlayer());
        assertTrue(board.getCell(2, 4).isBox());
        Box box = board.getBoxes().get(0);
        assertEquals(4, box.getX());
        assertEquals(2, box.getY());
    }
    
    @Test
    void testIsValidMoveFailsWhenBoxIsMissing() {
        Board board = new Board(3, 3);
        board.setCell(1, 1, new Cell(Cell.CellType.PLAYER));
        board.setCell(2, 1, new Cell(Cell.CellType.BOX)); 
        boolean result = board.movePlayer(Direction.DOWN);
        assertFalse(result);
    }

    @Test
    void testMovePlayerPushBoxToTarget() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.setCell(2, 3, new Cell(CellType.BOX));
        board.setTarget(2, 4);
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertTrue(moved);
        assertFalse(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isPlayer());
        assertTrue(board.getCell(2, 4).isBox());
        Box box = board.getBoxes().get(0);
        assertEquals(4, box.getX());
        assertEquals(2, box.getY());
        assertTrue(box.isOnTarget());
    }

    @Test
    void testMovePlayerPushBoxIntoWall() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.setCell(2, 3, new Cell(CellType.BOX));
        board.setCell(2, 4, new Cell(CellType.WALL));
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertFalse(moved);
        assertTrue(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isBox());
        assertTrue(board.getCell(2, 4).isWall());
    }
    

    @Test
    void testMovePlayerPushBoxIntoBox() {
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.setCell(2, 3, new Cell(CellType.BOX));
        board.setCell(2, 4, new Cell(CellType.BOX));
        boolean moved = board.movePlayer(Direction.RIGHT);
        assertFalse(moved);
        assertTrue(board.getCell(2, 2).isPlayer());
        assertTrue(board.getCell(2, 3).isBox());
        assertTrue(board.getCell(2, 4).isBox());
    }

    @Test
    void testToString() {
        board.setCell(0, 0, new Cell(CellType.PLAYER));
        board.setCell(1, 1, new Cell(CellType.BOX));
        board.setCell(2, 2, new Cell(CellType.WALL));
        board.setTarget(3, 3);
        String expected = "W    \n" +
                          " #   \n" +
                          "  +  \n" +
                          "   * \n" +
                          "     \n";
        assertEquals(expected, board.toString());
    }

    @Test
    void testFindBox() {
        board.setCell(1, 1, new Cell(CellType.BOX));
        Box box = board.getBoxes().get(0);
        assertNotNull(box);
        assertEquals(1, box.getX());
        assertEquals(1, box.getY());
    }
    
    @Test
    void testFindBoxes() {
        board.setCell(1, 1, new Cell(CellType.BOX));
        board.setCell(2, 2, new Cell(CellType.BOX));
        Box box = board.getBoxes().get(0);
        Box box1 = board.getBoxes().get(1);
        assertNotNull(box);
        assertEquals(1, box.getX());
        assertEquals(1, box.getY());
        assertNotNull(box1);
        assertEquals(2, box1.getX());
        assertEquals(2, box1.getY());
    }
       
    @Test
    void testLevelCompletedTrue() {
        Board board = new Board(5, 5);
        board.setCell(1, 1, new Cell(Cell.CellType.BOX));
        board.setTarget(1, 1);
        board.getBoxes().get(0).updateOnTarget(true);
        board.setCell(2, 2, new Cell(Cell.CellType.BOX));
        board.setTarget(2, 2);
        board.getBoxes().get(1).updateOnTarget(true);
        assertTrue(board.isLevelCompleted());
        board.getBoxes().get(1).updateOnTarget(false);
        assertFalse(board.isLevelCompleted());
    }
    
    @Test
    void testLevelCompletedFalse() {
        Board board = new Board(5, 5);
        board.setCell(1, 1, new Cell(Cell.CellType.BOX));
        board.setTarget(1, 1);
        board.getBoxes().get(0).updateOnTarget(true);
        board.setCell(2, 2, new Cell(Cell.CellType.BOX));
        board.setTarget(2, 3);
        board.getBoxes().get(1).updateOnTarget(false);
        assertFalse(board.isLevelCompleted());
    }
    
    @Test
    void testUndoLastMoveWithBox() {
        Board board = new Board(5, 5);
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        Box box = new Box(2, 1);
        board.getBoxes().add(box);
        board.getBoxHistory().push(box);
        board.setCell(1, 2, new Cell(CellType.EMPTY)); 
        board.setCell(2, 1, new Cell(CellType.BOX)); 
        boolean result = board.undoLastMove(Position.Direction.UP);
        assertTrue(result);
        assertTrue(board.getCell(1, 2).isPlayer());
        assertTrue(board.getCell(2, 2).isBox());
    }
    
    @Test
    void testUndoLastMoveWithoutBox() {
        Board board = new Board(5, 5);
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.getBoxHistory().push(new Box(-100, -100)); 
        board.setCell(1, 2, new Cell(CellType.EMPTY));
        boolean result = board.undoLastMove(Position.Direction.UP);
        assertTrue(result);
        assertTrue(board.getCell(1, 2).isPlayer());
        assertFalse( board.getCell(2, 2).isBox());
    }
    @Test
    void testUndoLastMoveWithoutBox2() {
        Board board = new Board(5, 5);
        board.setCell(2, 2, new Cell(CellType.PLAYER));
        board.getBoxHistory().push(new Box(0, -100)); 
        board.setCell(1, 2, new Cell(CellType.EMPTY));
        boolean result = board.undoLastMove(Position.Direction.UP);
        assertTrue(result);
        assertTrue(board.getCell(1, 2).isPlayer());
        assertFalse( board.getCell(2, 2).isBox());
    }
    
    @Test
    void testGetBox() {
        Board board = new Board(3, 3);
        board.setCell(1, 1, new Cell(CellType.BOX));
        Box box = board.getBoxAt(1, 1);
        assertNotNull(box);
        assertEquals(1, box.getX());
        assertEquals(1, box.getY());
    }
    
    @Test
    void testGetBoxNull() {
        Board board = new Board(3, 3);
        Box box = board.getBoxAt(0, 0);
        assertNull(box);
    }
    @Test
    void testGetBoxNull2() {
        Board board = new Board(3, 3);
        board.setCell(1, 1, new Cell(CellType.BOX));
        Box box = board.getBoxAt(1, 2);
        assertNull(box);
    }
    
    @Test
    void testGetBoxes() {
        Board board = new Board(5, 5);
        board.setCell(2, 2, new Cell(CellType.BOX));
        board.setCell(4, 4, new Cell(CellType.BOX));
        Box box1 = board.getBoxAt(2, 2);
        Box box2 = board.getBoxAt(4, 4);
        Box box3 = board.getBoxAt(0, 0);
        assertNotNull(box1);
        assertEquals(2, box1.getX());
        assertEquals(2, box1.getY());
        assertNotNull(box2);
        assertEquals(4, box2.getX());
        assertEquals(4, box2.getY());
        assertNull(box3, "No debe haber caja en (0,0)");
    }
    
    @Test
    void testSetBoxHistory() {
        Board board = new Board(3, 3);

        // Creamos una nueva historia con una caja de prueba
        Deque<Box> newHistory = new java.util.ArrayDeque<>();
        Box testBox = new Box(1, 1);
        newHistory.push(testBox);

        board.setBoxHistory(newHistory);

        Deque<Box> result = board.getBoxHistory();
        assertEquals(1, result.size());
        assertSame(testBox, result.peek());
    }
 

//--------------------------------------BOX---------------------------------
  //AQUI NO HAGO UN BEFORE EACH PARA ASI CAMBIAR UN POCO, PERO LO PODRÍAMOS AÑADIR Y NO DECLARAR BOX EN TODOS LOS METODOS
    @Test
    void testBoxConstructor() {
        Box box = new Box(2, 2);
        assertEquals(2, box.getX());
        assertEquals(2, box.getY());
        assertFalse(box.isOnTarget());
    }

    @Test
    void testGetX() {
        Box box = new Box(3, 6);
        assertEquals(3, box.getX());
    }

    @Test
    void testGetY() {
        Box box = new Box(3, 6);
        assertEquals(6, box.getY());
    }


    @Test
    void testBoxMove() {
        Box box = new Box(2, 3);
        box.move(1, 1);
        assertEquals(3, box.getX());
        assertEquals(4, box.getY());
    }

    @Test
    void testBoxUpdateOnTarget() {
        Box box = new Box(1, 1);
        box.updateOnTarget(true);
        assertTrue(box.isOnTarget());
    }
    
    @Test
    void testToStringBox() {
        board.setCell(0, 0, new Cell(CellType.PLAYER));
        board.setCell(1, 1, new Cell(CellType.BOX));
        board.setCell(2, 2, new Cell(CellType.WALL));
        board.setTarget(3, 3);
        String expected = "W    \n" +
                          " #   \n" +
                          "  +  \n" +
                          "   * \n" +
                          "     \n";
        assertEquals(expected, board.toString());
    }
    
//-----------------WAREHOUSEMAN--------------------------------
    
    @Test
    void testConstructor() {
        WarehouseMan wm = new WarehouseMan(2, 3);
        Position pos = wm.getPosition();
        assertEquals(2, pos.getX());
        assertEquals(3, pos.getY());
    }

    @Test
    void testMoveUp() {
        WarehouseMan wm = new WarehouseMan(2, 2);
        wm.move(Direction.UP);
        Position pos = wm.getPosition();
        assertEquals(2, pos.getX());
        assertEquals(1, pos.getY());
    }

    @Test
    void testMoveDown() {
        WarehouseMan wm = new WarehouseMan(2, 2);
        wm.move(Direction.DOWN);
        Position pos = wm.getPosition();
        assertEquals(2, pos.getX());
        assertEquals(3, pos.getY());
    }

    @Test
    void testMoveLeft() {
        WarehouseMan wm = new WarehouseMan(2, 2);
        wm.move(Direction.LEFT);
        Position pos = wm.getPosition();
        assertEquals(1, pos.getX());
        assertEquals(2, pos.getY());
    }

    @Test
    void testMoveRight() {
        WarehouseMan wm = new WarehouseMan(2, 2);
        wm.move(Direction.RIGHT);
        Position pos = wm.getPosition();
        assertEquals(3, pos.getX());
        assertEquals(2, pos.getY());
    }

    @Test
    void testMultipleMoves() {
        WarehouseMan wm = new WarehouseMan(0, 0);
        wm.move(Direction.RIGHT);
        wm.move(Direction.DOWN);
        wm.move(Direction.RIGHT);
        wm.move(Direction.UP);
        Position pos = wm.getPosition();
        assertEquals(2, pos.getX());
        assertEquals(0, pos.getY());
    }

    @Test
    void testMoveWithNullDirectionThrows() {
        WarehouseMan wm = new WarehouseMan(1, 1);
        assertThrows(NullPointerException.class, () -> wm.move(null));
    }

    
  //-----------------CELL--------------------------------
    
    
    @Test
    void testConstructorEmpty() {
        Cell cell = new Cell();
        cell.setType(CellType.EMPTY);
        assertFalse(cell.isWall());
        assertFalse(cell.isBox());
        assertFalse(cell.isPlayer());
        assertEquals(" ", cell.getContent());
        assertFalse(cell.isTarget());
    }
    
    @Test
    void testConstructorWall() {
        Cell cell = new Cell(CellType.WALL);
        assertTrue(cell.isWall());
        assertEquals("Wall", cell.getContent());
    }
    
    @Test
    void testConstructorBox() {
        Cell cell = new Cell(CellType.BOX);
        assertTrue(cell.isBox());
        assertEquals("Box", cell.getContent());
    }
    
    @Test
    void testConstructorPlayer() {
        Cell cell = new Cell(CellType.PLAYER);
        assertTrue(cell.isPlayer());
        assertEquals("Player", cell.getContent());
    }
    
    @Test
    void testSetTypeWALL() {
        Cell cell = new Cell(CellType.PLAYER);
        assertTrue(cell.isPlayer());
        assertEquals("Player", cell.getContent());
    }
    
    
    @Test
    void testIsTargetTrue() {
        Cell cell = new Cell();
        cell.setIsTarget(true);
        assertTrue(cell.isTarget());
    }
    
    @Test
    void testIsTargetFalse() {
        Cell cell = new Cell();
        cell.setIsTarget(true);
        cell.setIsTarget(false);
        assertFalse(cell.isTarget());
    }
   
//-------------------------GAME----------------------------
    @Test
    void testGameInitialization() {
        Game game = new Game();
        Level level = game.getCurrentLevel();
        assertNotNull(level);
        assertEquals(1, game.getCurrentLevelNumber());
    }

    @Test
    void testLoadLevel() {
        Game game = new Game();
        boolean loaded = game.loadLevel(1);
        assertTrue(loaded);
        assertEquals(1, game.getCurrentLevelNumber());
        assertNotNull(game.getCurrentLevel());
    }
    
    @Test
    void testIncreaseScore() {
        Game game = new Game();
        Game.increaseScore(game);
        assertEquals(1, game.getCurrentLevel().getLevelScore());
    }
    
    @Test
    void testDecreaseScore() {
        Game game = new Game();
        Game.increaseScore(game);
        Game.increaseScore(game);
        Game.decreaseScore(game);
        assertEquals(1, game.getCurrentLevel().getLevelScore());
    }
    
    @Test
    void testDecreaseScore2() {
        Game game = new Game();
        Game.decreaseScore(game);
        assertEquals(0, game.getCurrentLevel().getLevelScore());
    }
    
    @Test
    void testResetLevelScore() {
        Game game = new Game();
        Level level = game.getCurrentLevel();
        level.setLevelScore(5);  
        Game.setTotalScore(10);
        Game.resetLevelScore(game);
        assertEquals(5, Game.getTotalScore()); 
    }
    
    @Test
    void testRestartLevelScore() {
        Game.setTotalScore(15); 
        Game.restartLevelScore();
        assertEquals(0, Game.getTotalScore());
    }
    
    @Test
    void testSetCurrentLevelNumber() {
        Game game = new Game();
        game.setCurrentLevelNumber(5);
        assertEquals(5, game.getCurrentLevelNumber()); 
    }


    
/*
    
    
    @Test
    void testSaveGameSuccess() {
        Game game = new Game();
        String filename = "testSave.txt";
        assertDoesNotThrow(() -> game.saveGame(filename));
        File file = new File(filename);
        assertTrue(file.exists());
        file.delete();
    }
    
    @Test
    void testSaveGameIOException() {
        Game game = new Game();
        assertDoesNotThrow(() -> game.saveGame("/invalid-path/testSave.txt"));
    }
    
    
    
    @Test
    void testLoadSavedGameIOException() {
        Game game = new Game();
        boolean loaded = game.loadSavedGame("nonexistentfile.txt");
        assertFalse(loaded);
    }
    
    @Test
    void testLoadLevelIOException() {
        Game game = new Game();
        boolean loaded = game.loadLevel(99); 
        assertFalse(loaded);
    }
    
    @Test
    void testLoadLevelReturnsNull() throws IOException {
        // Crear archivo vacío
        String filename = "level_empty.txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Only Name"); // forzamos error de formato
        }

        Game game = new Game();
        boolean loaded = game.loadLevel("empty".hashCode()); // hashCode como número para evitar colisión

        assertFalse(loaded);
    }
    
    @Test
    void testSaveGameIOException00() {
        Game game = new Game();
        String invalidFilename = "/invalid_path/saved_game.txt";
        assertDoesNotThrow(() -> game.saveGame(invalidFilename));
    }*/
    
  //-------------------------POSITION----------------------------
    @Test
    void testDirectionGetOppositeUp() {
        assertEquals(Direction.DOWN, Direction.UP.getOpposite());
        
    }
    
    @Test
    void testDirectionGetOppositeDown() {
        assertEquals(Direction.UP, Direction.DOWN.getOpposite());
      
    }
    
    @Test
    void testDirectionGetOppositeLeft() {
        assertEquals(Direction.RIGHT, Direction.LEFT.getOpposite());
    }
    
    @Test
    void testDirectionGetOppositeRight() {
        assertEquals(Direction.LEFT, Direction.RIGHT.getOpposite());
    }
    
    @Test
    void testEqualsSameObject() {
        Position pos = new Position(2, 3);
        assertEquals(true, pos.equals(pos)); 
    }

    @Test
    void testEqualsDifferentType() {
        Position pos = new Position(2, 3);
        assertNotEquals(true, pos.equals("not a Position")); 
    }
    
    //-------------------------LEVEL----------------------------

    @Test
    void testConstructorWithLevelNameBoardAndNumber() {
        Board board = new Board(5, 5);
        Level level = new Level("Level 2", board);
        assertEquals("Level 2", level.getLevelName());
        assertEquals(board, level.getBoard());
    }
    
    @Test
    void testGetLevelScore() {
        Board board = new Board(5, 5);
        Level level = new Level("Level 2", board);
        assertEquals(0, level.getLevelScore());
    }
    
    @Test
    void testIncreaseLevelScore() {
        Board board = new Board(5, 5);
        Level level = new Level("Level 2", board);
        level.increaseScore();
        assertEquals(1, level.getLevelScore());
    }
    
    @Test
    void testDecreaseLevelScore() {
        Board board = new Board(5, 5);
        Level level = new Level("Level 2", board);
        level.increaseScore();
        level.increaseScore();
        level.increaseScore();
        level.decreaseScore();
        assertEquals(2, level.getLevelScore());
    }
    @Test
    void testDecreaseLevelScore0() {
        Board board = new Board(5, 5);
        Level level = new Level("Level 2", board);
        level.decreaseScore();
        assertEquals(0, level.getLevelScore());
    }

//----------------------------LOAD LEVEL------------------------------------
    
/*
    @Test
    void testValidLevelLoad() {
        String levelData =
            "Level 1\n" +
            "++++\n" +
            "+W*+\n" +
            "+#++\n" +
            "++++";

        Level level = LevelLoader.loadLevel(levelData);
        assertEquals("Level 1", level.getLevelName());
        assertNotNull(level.getBoard());
        assertEquals(1, level.getBoard().getBoxes().size());
        assertNotNull(level.getBoard().getWarehouseman());

        Cell targetCell = level.getBoard().getCell(1, 2);
        assertTrue(targetCell.isTarget());
    }
    
*/
    
/*
    @Test
    void testInvalidLevelNoBoxes() {
        String levelData =
            "No Boxes\n" +
            "++++\n" +
            "+W*+\n" +
            "++++\n" +
            "++++";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            LevelLoader.loadLevel(levelData)
        );
        assertEquals("The level must contain at least one box and one objective.", exception.getMessage());
    }
*/

    /*
    @Test
    void testInvalidLeveNoGoals() {
        String levelData =
            "No Goals\n" +
            "++++\n" +
            "+W#+\n" +
            "++++\n" +
            "++++";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            LevelLoader.loadLevel(levelData)
        );
        assertEquals("The level must contain at least one box and one objective.", exception.getMessage());
    }
*/
/*
    @Test
    void testInvalidLevelMultipleW() {
        String levelData =
            "Too Many Players\n" +
            "++++\n" +
            "+W#+\n" +
            "+W*+\n" +
            "++++";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            LevelLoader.loadLevel(levelData)
        );
        assertEquals("There must be exactly one warehouseman in the level.", exception.getMessage());
    }
*/
    
/*
    @Test
    void testInvalidLevelMismatched() {
        String levelData =
            "Box Goal Mismatch\n" +
            "++++\n" +
            "+W#+\n" +
            "+*++\n" +
            "+*++";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            LevelLoader.loadLevel(levelData)
        );
        assertEquals("The number of boxes and objectives must be the same.", exception.getMessage());
    }
*/
    /*
    @Test
    void testLevelNonRectangular() {
        String levelData =
            "Non Rectangular\n" +
            "++++\n" +
            "+W*+\n" +
            "+#\n" +
            "++++";

        Level level = LevelLoader.loadLevel(levelData);
        assertEquals(4, level.getBoard().getHeight());
        assertEquals(4, level.getBoard().getWidth());
    } 
    */

    @Test
    void testInvalidLevelFormat() {
        String levelData =
            "Only Name";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            LevelLoader.loadLevel(levelData)
        );
        assertEquals("Invalid level format: missing rows or data.", exception.getMessage());
    }
}
