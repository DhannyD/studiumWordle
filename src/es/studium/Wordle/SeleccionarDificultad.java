package es.studium.Wordle;

import java.awt.*;
import java.awt.event.*;

public class SeleccionarDificultad extends Frame {

    public SeleccionarDificultad() {
        super("Seleccionar Dificultad"); // Título del Frame
        setSize(400, 300); // Tamaño del Frame
        setLayout(new GridBagLayout()); // Usar GridBagLayout para centrar los componentes
        setLocationRelativeTo(null); // Centrar ventana en la pantalla
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Configuración de GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre botones

        // Crear y añadir botones para las opciones de dificultad
        String[] labels = {"4 Letras", "5 Letras", "6 Letras", "7 Letras"};
        int num = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                Button button = new Button(labels[num++]);
                gbc.gridx = col; // Columna del botón
                gbc.gridy = row; // Fila del botón
                add(button, gbc);
            }
        }

        setVisible(true); // Hacer visible el Frame
    }
}
