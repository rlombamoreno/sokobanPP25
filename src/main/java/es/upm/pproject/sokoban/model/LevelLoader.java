package es.upm.pproject.sokoban.model;

public class LevelLoader {

    private LevelLoader() {
    	// constructor innecesario
    }

    public static Level loadLevel(String levelData) {
        String[] lines = levelData.split("\n");

        // Validar que el nivel tiene contenido
        if (lines.length < 2) {
            throw new IllegalArgumentException("Invalid level format: missing rows or data.");
        }
        
        String levelName = lines[0].trim();
        String[] dimensions = lines[1].trim().split("\\s+"); // Separar valores por espacios
        int height = Integer.parseInt(dimensions[0]); // Número de filas
        int width = Integer.parseInt(dimensions[1]); 

        Board board = new Board(width, height);

        int boxCount = 0;
        int goalCount = 0;
        int warehouseManCount = 0;

        for (int i = 0; i < height; i++) {
            String line = lines[i + 2].trim();
            for (int j = 0; j < width; j++) {
            	char cellChar = 'V';
            	if(j<line.length()) {
            		cellChar = line.charAt(j);
            	}
                Cell cell = new Cell();
                switch (cellChar) {
                    case '+':
                        cell.setType(Cell.CellType.WALL);
                        break;
                    case '*':
                    	cell.setIsTarget(true);
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

        // Validaciones de nivel
        if (boxCount == 0 || goalCount == 0) {
            throw new IllegalArgumentException("The level must contain at least one box and one objective.");
        }
        if (warehouseManCount != 1) {
            throw new IllegalArgumentException("There must be exactly one warehouseman in the level.");
        }
        if (boxCount != goalCount) {
            throw new IllegalArgumentException("The number of boxes and objectives must be the same.");
        }
        return new Level(levelName, board);
    }
}
