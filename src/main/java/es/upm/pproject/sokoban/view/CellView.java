package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import es.upm.pproject.sokoban.model.Cell;

public class CellView extends JPanel {
    private Cell cell;
    private static Image playerImage;
    private static Image boxImage;
    private static Image equisImage; 

    static {
        try {
            playerImage = ImageIO.read(new File("src/main/resources/player.png"));
            boxImage = ImageIO.read(new File("src/main/resources/box.png"));
            equisImage = ImageIO.read(new File("src/main/resources/equis.png"));
        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes.");
            playerImage = null;
            boxImage = null;
        }
    }

    public CellView(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(20, 20));
        updateAppearance();
    }

    public void updateCell(Cell newCell) {
        if (!newCell.equals(this.cell)) { // Solo actualiza si hay cambios
            this.cell = newCell;
            updateAppearance();
            repaint();
        }
    }

    private void updateAppearance() {
        if ("Player".equals(cell.getContent()) || "Box".equals(cell.getContent())) {
            setBackground(null); // Eliminar el color de fondo para imágenes
        } else {
            switch (cell.getContent()) {
                case "Wall":
                    setBackground(Color.DARK_GRAY);
                    break;
                default:
                    setBackground(cell.isTarget() ? null : new Color(240, 240, 240));
                    break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ("Player".equals(cell.getContent()) && playerImage != null) {
            g.drawImage(playerImage, 0, 0, getWidth(), getHeight(), this);
        } else if ("Box".equals(cell.getContent()) && boxImage != null) {
            g.drawImage(boxImage, 0, 0, getWidth(), getHeight(), this);
        } else if (cell.isTarget() && equisImage != null) {
            g.drawImage(equisImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}