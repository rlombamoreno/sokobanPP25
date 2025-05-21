package es.upm.pproject.sokoban.model;

public class LevelLoader {

    public static Level loadLevel(String levelData) {
        String[] lines = levelData.split("\n");
        String levelName = lines[0].trim();
        int height = lines.length - 1;
        int width = 0;
        for (int i = 1; i < lines.length; i++) {
            width = Math.max(width, lines[i].trim().length());
        }

        Board board = new Board(width, height);

        int boxCount = 0;
        int goalCount = 0;
        int warehouseManCount = 0;

        for (int i = 0; i < height; i++) {
            String line = lines[i + 1].trim();
            for (int j = 0; j < width; j++) {
                char cellChar = line.charAt(j);
                Cell cell = new Cell();
                switch (cellChar) {
                    case '+':
                        cell.setType(Cell.CellType.WALL);
                        break;
                    case '*':
                        board.setTarget(i, j);
                        goalCount++;
                        break;
                    case '#':
                        cell.setType(Cell.CellType.BOX);
                        boxCount++;
                        break;
                    case 'W':
                        cell.setType(Cell.CellType.PLAYER);
                        warehouseManCount++;
                        break;
                    default:
                        break;
                }
                board.setCell(i, j, cell);
            }
        }

        
        if (boxCount == 0 || goalCount == 0) {
            throw new IllegalArgumentException("The level must contain at least one box and one objectives.");
        }
        if (warehouseManCount != 1) {
            throw new IllegalArgumentException("There must be at least one wharehouseman in the level.");
        }
        if (boxCount != goalCount) {
            throw new IllegalArgumentException("The number of boxes and objectives must be the same.");
        }

        return new Level(levelName, board);
    }
}
