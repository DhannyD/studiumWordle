package es.studium.WordleSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JuegoWordle extends JFrame implements ActionListener, KeyListener {
    private static final int MAX_INTENTOS = 6; // Intentos fijos
    int longitudPalabra = 5; // Longitud dinámica según dificultad
    private JTextField[][] camposIntentos;
    private JButton botonEnviar;
    private final String palabraObjetivo = "CIELO"; // Palabra a adivinar, se podría obtener también dinámicamente
    private int intentoActual = 0;
    

    public JuegoWordle() {
        super("Juego Wordle");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setFocusable(true);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel para los intentos de adivinar la palabra
        JPanel panelIntentos = new JPanel(new GridLayout(MAX_INTENTOS, 1, 5, 5));
        camposIntentos = new JTextField[MAX_INTENTOS][longitudPalabra];
        Dimension dim = new Dimension(50, 50); // Tamaño preferido para que sean cuadrados
        for (int i = 0; i < MAX_INTENTOS; i++) {
            JPanel fila = new JPanel(new GridLayout(1, longitudPalabra, 5, 5));
            for (int j = 0; j < longitudPalabra; j++) {
                camposIntentos[i][j] = new JTextField();
                camposIntentos[i][j].setHorizontalAlignment(JTextField.CENTER);
                camposIntentos[i][j].setFont(new Font("SansSerif", Font.BOLD, 24));
                camposIntentos[i][j].setEditable(false);
                camposIntentos[i][j].setPreferredSize(dim); // Establecer tamaño preferido
                fila.add(camposIntentos[i][j]);
            }
            panelIntentos.add(fila);
        }

        // Centrar el panel de intentos
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contenedorCentral.add(panelIntentos, gbc);

        add(contenedorCentral, BorderLayout.CENTER);

        // Panel para el teclado
        JPanel panelTeclado = new JPanel();
        panelTeclado.setLayout(new GridLayout(3, 1)); // Tres filas para las letras

        String[] fila1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        String[] fila2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"};
        String[] fila3 = {"Z", "X", "C", "V", "B", "N", "M"};

        // Crear las filas de botones
        JPanel fila1Panel = new JPanel(new GridLayout(1, 10));
        JPanel fila2Panel = new JPanel(new GridLayout(1, 10));
        JPanel fila3Panel = new JPanel(new GridLayout(1, 9)); // Modificar a 9 para incluir el botón de enviar

        // Añadir botones a cada fila
        agregarBotones(fila1Panel, fila1);
        agregarBotones(fila2Panel, fila2);
        agregarBotonesConBorradoYEnviar(fila3Panel, fila3); // Método modificado para incluir el botón de borrar y enviar

        // Añadir filas al panel principal del teclado
        panelTeclado.add(fila1Panel);
        panelTeclado.add(fila2Panel);
        panelTeclado.add(fila3Panel);

        // Contenedor para centrar el teclado
        JPanel contenedorTeclado = new JPanel();
        contenedorTeclado.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        contenedorTeclado.add(panelTeclado, gbc);

        add(contenedorTeclado, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void reiniciarJuego() {
        getContentPane().removeAll();
        intentoActual = 0;
        inicializarComponentes();
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton pressedButton = (JButton) e.getSource();
            String text = pressedButton.getText();
            procesarEntrada(text);
            requestFocusInWindow(); // Restaurar el foco a la ventana principal
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isLetter(keyChar)) {
            procesarEntrada(String.valueOf(keyChar).toUpperCase());
        } else if (keyChar == KeyEvent.VK_BACK_SPACE) {
            borrarUltimaLetra();
        } else if (keyChar == KeyEvent.VK_ENTER) {
            enviarIntento();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void procesarEntrada(String texto) {
        if ("←".equals(texto)) {
            borrarUltimaLetra();
        } else if ("ENVIAR".equals(texto)) {
            enviarIntento();
        } else {
            agregarLetra(texto);
        }
    }

    private void borrarUltimaLetra() {
        for (int i = longitudPalabra - 1; i >= 0; i--) {
            if (!camposIntentos[intentoActual][i].getText().isEmpty()) {
                camposIntentos[intentoActual][i].setText("");
                break;
            }
        }
        habilitarBotonEnviar();
    }

    private void agregarLetra(String letra) {
        if (intentoActual >= MAX_INTENTOS) return;
        for (int i = 0; i < longitudPalabra; i++) {
            if (camposIntentos[intentoActual][i].getText().isEmpty()) {
                camposIntentos[intentoActual][i].setText(letra);
                break;
            }
        }
        habilitarBotonEnviar();
    }

    private void enviarIntento() {
        if (esFilaCompleta(intentoActual)) {
            String intento = obtenerPalabra(intentoActual);
            proporcionarRetroalimentacion(intento);
            intentoActual++;
            botonEnviar.setEnabled(false); // Deshabilitar el botón de enviar hasta que la nueva fila esté completa
        } else {
            vibrarFila(intentoActual); // Vibrar si la fila no está completa
            System.out.println("La fila no está completa.");
        }
    }

    private void proporcionarRetroalimentacion(String intento) {
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

        if (intento.equals(palabraObjetivo)) {
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has adivinado la palabra.");
        } else if (intentoActual == MAX_INTENTOS - 1) {
            JOptionPane.showMessageDialog(this, "Lo siento, has alcanzado el número máximo de intentos. La palabra era: " + palabraObjetivo);
            reiniciarJuego();
        }
    }

    private boolean esFilaCompleta(int intento) {
        for (int i = 0; i < longitudPalabra; i++) {
            if (camposIntentos[intento][i].getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String obtenerPalabra(int intento) {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i < longitudPalabra; i++) {
            palabra.append(camposIntentos[intento][i].getText());
        }
        return palabra.toString();
    }

    private void habilitarBotonEnviar() {
        botonEnviar.setEnabled(esFilaCompleta(intentoActual));
    }

    private void vibrarFila(int intento) {
        final int[] initialX = new int[longitudPalabra];
        final int[] initialY = new int[longitudPalabra];

        for (int i = 0; i < longitudPalabra; i++) {
            initialX[i] = camposIntentos[intento][i].getLocation().x;
            initialY[i] = camposIntentos[intento][i].getLocation().y;
        }

        botonEnviar.setEnabled(false); // Deshabilitar el botón de enviar

        Timer timer = new Timer(10, new ActionListener() {
            int offset = 0;
            boolean direction = true;
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < longitudPalabra; i++) {
                    if (direction) {
                        camposIntentos[intento][i].setLocation(initialX[i] + 2, initialY[i]);
                    } else {
                        camposIntentos[intento][i].setLocation(initialX[i] - 2, initialY[i]);
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
                        camposIntentos[intento][i].setLocation(initialX[i], initialY[i]);
                    }
                    botonEnviar.setEnabled(true); // Volver a habilitar el botón de enviar
                    requestFocusInWindow(); // Restaurar el foco a la ventana principal
                }
            }
        });
        timer.start();
    }

    private void agregarBotones(JPanel panel, String[] letras) {
        for (String letra : letras) {
            JButton boton = new JButton(letra);
            boton.addActionListener(this);
            panel.add(boton);
        }
    }

    private void agregarBotonesConBorradoYEnviar(JPanel panel, String[] letras) {
        botonEnviar = new JButton("ENVIAR");
        botonEnviar.addActionListener(this);
        botonEnviar.setEnabled(false); // Inicialmente deshabilitado
        panel.add(botonEnviar); // Añadir el botón de enviar al principio

        agregarBotones(panel, letras); // Agregar todos los botones de letras

        JButton botonBorrar = new JButton("←"); // Crear botón de borrado
        botonBorrar.addActionListener(this);
        panel.add(botonBorrar); // Añadir el botón de borrado al final
    }

    public static void main(String[] args) {
        new JuegoWordle();
    }
}

