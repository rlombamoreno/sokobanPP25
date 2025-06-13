package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import es.upm.pproject.sokoban.model.Cell;

public class CellView extends JPanel {
    private Cell cell;
    private Image playerImage;

    public CellView(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(20, 20));
        loadPlayerImage();
        updateAppearance();
    }

    public void updateCell(Cell newCell) {
        this.cell = newCell;
        updateAppearance();
    }

    private void loadPlayerImage() {
        try {
            playerImage = ImageIO.read(new File("src/main/resources/player.png"));
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen del jugador.");
            playerImage = null;
        }
    }

    private void updateAppearance() {
        if ("Player".equals(cell.getContent())) {
            setBackground(null); // Eliminar el color de fondo
        } else {
            switch (cell.getContent()) {
                case "Wall":
                    setBackground(Color.DARK_GRAY);
                    break;
                case "Box":
                    setBackground(Color.ORANGE);
                    break;
                default:
                    setBackground(cell.isTarget() ? Color.GREEN : new Color(240, 240, 240));
                    break;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ("Player".equals(cell.getContent()) && playerImage != null) {
            g.drawImage(playerImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
