package es.upm.pproject.sokoban.view;
import javax.swing.*;
import java.awt.*;
import es.upm.pproject.sokoban.model.Cell;

public class CellView extends JPanel {
    private Cell cell;

    public CellView(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(20, 20));
        updateAppearance();
    }

    public void updateCell(Cell newCell) {
        this.cell = newCell;
        updateAppearance();
    }

    private void updateAppearance() {
        switch (cell.getContent()) {
            case "Wall":
                setBackground(Color.DARK_GRAY);
                break;
            case "Player":
                setBackground(Color.BLUE);
                break;
            case "Box":
                setBackground(Color.ORANGE);
                break;
            default:
              if(cell.isTarget()) {
          setBackground(Color.GREEN);
        } else {
          setBackground(new Color(240, 240, 240));
        }
                break;
        }
        repaint();
    }
}