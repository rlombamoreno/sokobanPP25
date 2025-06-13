package es.upm.pproject.sokoban.view;

import javax.swing.*;
import java.awt.*;

public class ErrorView extends JFrame {
    public ErrorView() {
        setTitle("Error - Faltan niveles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel errorMessage = new JLabel("<html><center><h2>Error</h2>"
                + "<p>Faltan niveles para cargar.</p>"
                + "<p>Debe haber al menos 3 archivos de niveles.</p></center></html>");
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.setFont(new Font("Arial", Font.BOLD, 16));
        add(errorMessage, BorderLayout.CENTER);

        JButton exitButton = new JButton("Cerrar");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, BorderLayout.SOUTH);
    }
}
