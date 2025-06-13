package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.sokoban.model.*;
import es.upm.pproject.sokoban.model.Cell.CellType;
import es.upm.pproject.sokoban.model.Position.Direction;
import java.util.List;

public class MainTest {
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

    @Test
    void testConstructorValid() {
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        assertNotNull(board.getCells());
        assertEquals(height, board.getCells().length);
        assertEquals(width, board.getCells()[0].length);
        assertNotNull(board.getBoxes());
        assertTrue(board.getBoxes().isEmpty());
        assertNotNull(board.getWarehouseman());
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
    void testGetCellInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, width));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(height, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, -1));
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
    void testSetCellInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setCell(-1, 0, new Cell(CellType.EMPTY)));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setCell(0, width, new Cell(CellType.EMPTY)));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setCell(height, 0, new Cell(CellType.EMPTY)));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setCell(0, -1, new Cell(CellType.EMPTY)));
        assertThrows(IllegalArgumentException.class, () -> board.setCell(2, 2, null));
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
    void testSetTargetInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(0, width));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(height, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.setTarget(0, -1));
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

//--------------------------------------BOX---------------------------------
  //AQUI NO AGO UN BEFORE EACH PARA ASI CAMBIAR UN POCO, PERO LO PODRÍAMOS AÑADIR Y NO DECLARAR BOX EN TODOS LOS METODOS
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
    void testInvalidBoxCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Box(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Box(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Box(-5, -5));
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

    @Test
    void testConstructorWithNegativeCoordinatesThrows() {
        assertThrows(IllegalArgumentException.class, () -> new WarehouseMan(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> new WarehouseMan(2, -5));
        assertThrows(IllegalArgumentException.class, () -> new WarehouseMan(-1, -1));
    }
}