package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaAyuda extends JFrame {
    private JTextArea areaAyuda;
    private JButton btnVolver;

    public VistaAyuda() {
        setTitle("Ayuda");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Área de texto para la ayuda
        areaAyuda = new JTextArea();
        areaAyuda.setText("Bienvenido a Wordle!\n\n" +
                "Instrucciones del juego:\n" +
                "1. El objetivo es adivinar una palabra secreta.\n" +
                "2. Tienes 6 intentos para adivinar la palabra.\n" +
                "3. Cada intento debe ser una palabra válida de la misma longitud que la palabra secreta.\n" +
                "4. Después de cada intento, el color de las letras cambiará para mostrar qué tan cerca estás de adivinar la palabra:\n" +
                "   - Verde: La letra está en la posición correcta.\n" +
                "   - Amarillo: La letra está en la palabra pero en la posición incorrecta.\n" +
                "   - Gris: La letra no está en la palabra.\n\n" +
                "Nota: Actualmente, el teclado en pantalla no funciona (lo siento Jorge, despues de 3 horas no he sido capaz de hacer que funcione). Usa el teclado físico para jugar.\n\n" +
                "¡Buena suerte!");
        areaAyuda.setEditable(false);
        areaAyuda.setWrapStyleWord(true);
        areaAyuda.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(areaAyuda);

        // Panel para el botón de volver
        JPanel panelBoton = new JPanel();
        btnVolver = new JButton("Volver");
        panelBoton.add(btnVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    public void setControlador(ActionListener controlador) {
        btnVolver.addActionListener(controlador);
    }

    public void mostrar() {
        setVisible(true);
    }

    public void ocultar() {
        setVisible(false);
    }
}
