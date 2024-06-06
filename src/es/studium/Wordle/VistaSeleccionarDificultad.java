package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaSeleccionarDificultad extends JFrame {
    JButton[] botonesDificultad;
    JButton btnVolver;

    public VistaSeleccionarDificultad() {
        setTitle("Seleccionar Dificultad");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        String[] labels = {"4 Letras", "5 Letras", "6 Letras", "7 Letras"};
        botonesDificultad = new JButton[labels.length];
        int num = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                botonesDificultad[num] = new JButton(labels[num]);
                gbc.gridx = col;
                gbc.gridy = row;
                add(botonesDificultad[num++], gbc);
            }
        }

        // Añadir el botón de "Volver"
        btnVolver = new JButton("Volver");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnVolver, gbc);
    }

    public void setControlador(ActionListener controlador) {
        for (JButton boton : botonesDificultad) {
            boton.addActionListener(controlador);
        }
        btnVolver.addActionListener(controlador);
    }

    public void mostrar() {
        setVisible(true);
    }

    public void ocultar() {
        setVisible(false);
    }
}
