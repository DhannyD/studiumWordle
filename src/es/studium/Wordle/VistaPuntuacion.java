package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPuntuacion extends JFrame {
    private JLabel etiquetaPuntuacion;
    private JTextField campoNombre;
    private JButton botonEnviar;
    private JButton botonSalir;

    public VistaPuntuacion() {
        setTitle("Puntuación Final");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        etiquetaPuntuacion = new JLabel("Puntuación: 0", SwingConstants.CENTER);
        etiquetaPuntuacion.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(etiquetaPuntuacion, BorderLayout.CENTER);

        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new FlowLayout());

        campoNombre = new JTextField(20);
        panelEntrada.add(new JLabel("Nombre:"));
        panelEntrada.add(campoNombre);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonEnviar = new JButton("Enviar");
        botonSalir = new JButton("Salir");

        panelBotones.add(botonEnviar);
        panelBotones.add(botonSalir);

        add(panelEntrada, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void setPuntuacion(int puntuacion) {
        etiquetaPuntuacion.setText("Puntuación: " + puntuacion);
    }

    public String getNombre() {
        return campoNombre.getText();
    }

    public void setControlador(ActionListener controlador) {
        botonEnviar.addActionListener(controlador);
        botonSalir.addActionListener(controlador);
    }

    public void desactivarBotonEnviar() {
        botonEnviar.setEnabled(false);
    }
    
    public void mostrar() {
        setVisible(true);
    }
}
