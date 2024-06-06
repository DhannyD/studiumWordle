package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class VistaJuegoWordle extends JFrame {
    private JTextField[][] camposIntentos;
    private JButton botonEnviar;
    private JButton botonBorrar;
    private JButton[] botonesLetras;
    private int longitudPalabra;
    private int maxIntentos;
    private JLabel etiquetaPuntuacion;
    private int intentoActual;

    public VistaJuegoWordle() {
        botonEnviar = new JButton("ENVIAR");
        botonBorrar = new JButton("←");
        botonesLetras = new JButton[29]; // 27 letras + 2 botones
        etiquetaPuntuacion = new JLabel("Puntuación: 0");

        setTitle("Juego Wordle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setFocusable(true);
    }

    public void inicializarComponentes(int longitudPalabra, int maxIntentos) {
        this.longitudPalabra = longitudPalabra;
        this.maxIntentos = maxIntentos;
        this.intentoActual = 0;
        camposIntentos = new JTextField[maxIntentos][longitudPalabra];
        
        getContentPane().removeAll(); // Limpiar componentes anteriores

        JPanel panelIntentos = new JPanel(new GridLayout(maxIntentos, 1, 5, 5));
        Dimension dim = new Dimension(50, 50);
        for (int i = 0; i < maxIntentos; i++) {
            JPanel fila = new JPanel(new GridLayout(1, longitudPalabra, 5, 5));
            for (int j = 0; j < longitudPalabra; j++) {
                camposIntentos[i][j] = new JTextField();
                camposIntentos[i][j].setHorizontalAlignment(JTextField.CENTER);
                camposIntentos[i][j].setFont(new Font("SansSerif", Font.BOLD, 24));
                camposIntentos[i][j].setEditable(false);
                camposIntentos[i][j].setPreferredSize(dim);
                fila.add(camposIntentos[i][j]);
            }
            panelIntentos.add(fila);
        }

        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contenedorCentral.add(panelIntentos, gbc);
        add(contenedorCentral, BorderLayout.CENTER);

        JPanel panelTeclado = new JPanel();
        panelTeclado.setLayout(new GridLayout(3, 1));
        String[] fila1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        String[] fila2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"};
        String[] fila3 = {"Z", "X", "C", "V", "B", "N", "M"};

        agregarBotones(panelTeclado, fila1, 0);
        agregarBotones(panelTeclado, fila2, 10);
        agregarBotonesConBorradoYEnviar(panelTeclado, fila3, 20);

        JPanel contenedorTeclado = new JPanel();
        contenedorTeclado.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        contenedorTeclado.add(panelTeclado, gbc);
        add(contenedorTeclado, BorderLayout.SOUTH);

        JPanel panelPuntuacion = new JPanel();
        panelPuntuacion.add(etiquetaPuntuacion);
        add(panelPuntuacion, BorderLayout.NORTH);

        revalidate();
        repaint();
        requestFocusInWindow(); // Solicitar el foco para la ventana
    }

    private void agregarBotones(JPanel panel, String[] letras, int offset) {
        JPanel filaPanel = new JPanel(new GridLayout(1, letras.length));
        for (int i = 0; i < letras.length; i++) {
            JButton boton = new JButton(letras[i]);
            boton.setActionCommand(letras[i]);
            botonesLetras[offset + i] = boton;
            filaPanel.add(boton);
        }
        panel.add(filaPanel);
    }

    private void agregarBotonesConBorradoYEnviar(JPanel panel, String[] letras, int offset) {
        JPanel filaPanel = new JPanel(new GridLayout(1, letras.length + 2));
        botonEnviar.setActionCommand("ENVIAR");
        filaPanel.add(botonEnviar);
        for (int i = 0; i < letras.length; i++) {
            JButton boton = new JButton(letras[i]);
            boton.setActionCommand(letras[i]);
            filaPanel.add(boton);
        }
        filaPanel.add(botonBorrar);
        panel.add(filaPanel);
    }

    public void setControlador(ActionListener controlador) {
        botonEnviar.addActionListener(controlador);
        botonBorrar.addActionListener(controlador);
        for (JButton boton : botonesLetras) {
            if (boton != null) { // Verificar que el botón no sea nulo antes de agregar el ActionListener
                boton.addActionListener(controlador);
            }
        }
    }

    public void setKeyListener(KeyListener keyListener) {
        addKeyListener(keyListener);
        for (JButton boton : botonesLetras) {
            if (boton != null) {
                boton.addKeyListener(keyListener);
            }
        }
        botonEnviar.addKeyListener(keyListener);
        botonBorrar.addKeyListener(keyListener);
    }

    public void mostrar() {
        setVisible(true);
        requestFocusInWindow(); // Solicitar el foco para la ventana
    }

    public void actualizarPuntuacion(int puntuacion) {
        etiquetaPuntuacion.setText("Puntuación: " + puntuacion);
    }

    public void agregarLetra(int intentoActual, String letra) {
        for (int i = 0; i < longitudPalabra; i++) {
            if (camposIntentos[intentoActual][i].getText().isEmpty()) {
                camposIntentos[intentoActual][i].setText(letra);
                break;
            }
        }
    }

    public void borrarUltimaLetra(int intentoActual) {
        for (int i = longitudPalabra - 1; i >= 0; i--) {
            if (!camposIntentos[intentoActual][i].getText().isEmpty()) {
                camposIntentos[intentoActual][i].setText("");
                break;
            }
        }
    }

    public boolean esFilaCompleta(int intentoActual) {
        for (int i = 0; i < longitudPalabra; i++) {
            if (camposIntentos[intentoActual][i].getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public String obtenerPalabra(int intentoActual) {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i < longitudPalabra; i++) {
            palabra.append(camposIntentos[intentoActual][i].getText());
        }
        return palabra.toString();
    }

    public void proporcionarRetroalimentacion(int intentoActual, String intento, String palabraObjetivo) {
        for (int i = 0; i < longitudPalabra; i++) {
            char letraIntento = intento.charAt(i);
            char letraObjetivo = palabraObjetivo.charAt(i);

            if (letraIntento == letraObjetivo) {
                camposIntentos[intentoActual][i].setBackground(Color.GREEN);
            } else if (palabraObjetivo.contains(String.valueOf(letraIntento))) {
                camposIntentos[intentoActual][i].setBackground(Color.YELLOW);
            } else {
                camposIntentos[intentoActual][i].setBackground(Color.GRAY);
            }
        }
    }

    public void vibrarFila(int intentoActual) {
        final int[] initialX = new int[longitudPalabra];
        final int[] initialY = new int[longitudPalabra];

        for (int i = 0; i < longitudPalabra; i++) {
            initialX[i] = camposIntentos[intentoActual][i].getLocation().x;
            initialY[i] = camposIntentos[intentoActual][i].getLocation().y;
        }

        Timer timer = new Timer(10, new ActionListener() {
            int offset = 0;
            boolean direction = true;
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < longitudPalabra; i++) {
                    if (direction) {
                        camposIntentos[intentoActual][i].setLocation(initialX[i] + 2, initialY[i]);
                    } else {
                        camposIntentos[intentoActual][i].setLocation(initialX[i] - 2, initialY[i]);
                    }
                }
                offset += (direction ? 5 : -5);
                if (offset >= 10 || offset <= -10) {
                    direction = !direction;
                    count++;
                }
                if (count >= 6) {
                    ((Timer) e.getSource()).stop();
                    // Reset locations to the initial position
                    for (int i = 0; i < longitudPalabra; i++) {
                        camposIntentos[intentoActual][i].setLocation(initialX[i], initialY[i]);
                    }
                }
            }
        });
        timer.start();
    }
}
