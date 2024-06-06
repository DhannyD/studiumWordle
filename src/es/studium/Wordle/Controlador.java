package es.studium.Wordle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class Controlador implements ActionListener, KeyListener {
    private ModeloWordle modelo;
    private final VistaMenuPrincipal menuPrincipalVista;
    private final VistaSeleccionarDificultad seleccionarDificultadVista;
    private final VistaJuegoWordle juegoWordleVista;
    private final VistaPuntuacion vistaPuntuacion;
    private final VistaRanking vistaRanking;
    private final VistaAyuda vistaAyuda;
    private int intentoActual;
    private WordleBD wordleBD;

    public Controlador(ModeloWordle modelo, VistaMenuPrincipal menuPrincipalVista, VistaSeleccionarDificultad seleccionarDificultadVista, VistaJuegoWordle juegoWordleVista, VistaPuntuacion vistaPuntuacion, VistaRanking vistaRanking, VistaAyuda vistaAyuda) {
    	this.modelo = modelo;
        this.menuPrincipalVista = menuPrincipalVista;
        this.seleccionarDificultadVista = seleccionarDificultadVista;
        this.juegoWordleVista = juegoWordleVista;
        this.vistaPuntuacion = vistaPuntuacion;
        this.vistaRanking = vistaRanking;
        this.vistaAyuda = vistaAyuda;
        this.intentoActual = 0;
        this.wordleBD = new WordleBD();

        this.menuPrincipalVista.setControlador(this);
        this.seleccionarDificultadVista.setControlador(this);
        this.juegoWordleVista.setControlador(this);
        this.juegoWordleVista.setKeyListener(this);
        this.vistaPuntuacion.setControlador(this);
        this.vistaRanking.setControlador(this);
        this.vistaAyuda.setControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println("Acción: " + comando);  // Mensaje de depuración

        if (comando.equals("Nueva Partida")) {
            seleccionarDificultadVista.mostrar();
            menuPrincipalVista.setVisible(false);
            vistaPuntuacion.setVisible(false);
            System.out.println("Cambiando a Seleccionar Dificultad");
        } else if (comando.equals("Ranking")) {
            vistaRanking.mostrar();
            menuPrincipalVista.setVisible(false);
        } else if (comando.equals("Ayuda")) {
            vistaAyuda.mostrar();
            menuPrincipalVista.setVisible(false);
        } else if (comando.equals("Volver")) {
            menuPrincipalVista.setVisible(true);
            vistaRanking.ocultar();
            vistaAyuda.ocultar();
            seleccionarDificultadVista.ocultar();
        } else if (comando.equals("Menú Principal")) {
            menuPrincipalVista.setVisible(true);
            seleccionarDificultadVista.setVisible(false);
            juegoWordleVista.setVisible(false);
            vistaPuntuacion.setVisible(false);
        } else if (comando.equals("4 Letras")) {
        	String palabra = modelo.seleccionarPalabraAleatoria(4);
            int longitudPalabra = 4;
            modelo = new ModeloWordle(palabra, longitudPalabra, 6); // Selección aleatoria
            juegoWordleVista.inicializarComponentes(longitudPalabra, 6);
            juegoWordleVista.mostrar();
            seleccionarDificultadVista.setVisible(false);
            System.out.println("Cambiando a Juego Wordle con longitud: " + longitudPalabra);
        } else if(comando.equals("5 Letras") ) {
        	String palabra = modelo.seleccionarPalabraAleatoria(5);
            int longitudPalabra = 5;
            modelo = new ModeloWordle(palabra, longitudPalabra, 6);
            juegoWordleVista.inicializarComponentes(longitudPalabra, 6);
            juegoWordleVista.mostrar();
            seleccionarDificultadVista.setVisible(false);
            System.out.println("Cambiando a Juego Wordle con longitud: " + longitudPalabra);
        } else if(comando.equals("6 Letras")) {
          	String palabra = modelo.seleccionarPalabraAleatoria(6);
            int longitudPalabra = 6;
            modelo = new ModeloWordle(palabra, longitudPalabra, 6);
            juegoWordleVista.inicializarComponentes(longitudPalabra, 6);
            juegoWordleVista.mostrar();
            seleccionarDificultadVista.setVisible(false);
            System.out.println("Cambiando a Juego Wordle con longitud: " + longitudPalabra);
        } else if(comando.equals("7 Letras")){
          	String palabra = modelo.seleccionarPalabraAleatoria(7);
            int longitudPalabra = 7;
            modelo = new ModeloWordle(palabra, longitudPalabra, 6);
            juegoWordleVista.inicializarComponentes(longitudPalabra, 6);
            juegoWordleVista.mostrar();
            seleccionarDificultadVista.setVisible(false);
            System.out.println("Cambiando a Juego Wordle con longitud: " + longitudPalabra);
        } else if (comando.equals("ENVIAR")) {
            manejarEnvioIntento();
        } else if (comando.equals("←")) {
            manejarBorradoUltimaLetra();
        } else if (comando.length() == 1 && Character.isLetter(comando.charAt(0))) {
            manejarIngresoLetra(comando);
        } else if (comando.equals("Salir")) {
            System.exit(0);
        } else if (comando.equals("Enviar")) {
            enviarPuntuacion();
        }

        // Devolver el foco a la ventana principal después de cada acción
        juegoWordleVista.requestFocusInWindow();
    }

    private void manejarEnvioIntento() {
        if (juegoWordleVista.esFilaCompleta(intentoActual)) {
            String intento = juegoWordleVista.obtenerPalabra(intentoActual);
            juegoWordleVista.proporcionarRetroalimentacion(intentoActual, intento, modelo.getPalabraObjetivo());
            modelo.incrementarIntentos();
            intentoActual++;
            int puntuacion = modelo.calcularPuntuacionPorPrecision(intento);
            juegoWordleVista.actualizarPuntuacion(puntuacion);
            if (intento.equals(modelo.getPalabraObjetivo())) {
                JOptionPane.showMessageDialog(juegoWordleVista, "¡Felicidades! Has adivinado la palabra.");
                mostrarVistaPuntuacion(puntuacion);
            } else if (intentoActual == modelo.getMaxIntentos()) {
                JOptionPane.showMessageDialog(juegoWordleVista, "Lo siento, has alcanzado el número máximo de intentos. La palabra era: " + modelo.getPalabraObjetivo());
                mostrarVistaPuntuacion(puntuacion);
            }
        } else {
            juegoWordleVista.vibrarFila(intentoActual);
            System.out.println("La fila no está completa.");
        }
    }

    private void mostrarVistaPuntuacion(int puntuacion) {
        juegoWordleVista.setVisible(false);
        vistaPuntuacion.setPuntuacion(puntuacion);
        vistaPuntuacion.mostrar();
    }

    private void manejarBorradoUltimaLetra() {
        juegoWordleVista.borrarUltimaLetra(intentoActual);
    }

    private void manejarIngresoLetra(String letra) {
        juegoWordleVista.agregarLetra(intentoActual, letra);
    }

    private void enviarPuntuacion() {
        String nombre = vistaPuntuacion.getNombre();
        String intento = juegoWordleVista.obtenerPalabra(intentoActual - 1);
        int puntuacion = modelo.calcularPuntuacionPorPrecision(intento);
        wordleBD.insertarPuntuacion(nombre, puntuacion);
        vistaPuntuacion.desactivarBotonEnviar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isLetter(keyChar)) {
            manejarIngresoLetra(String.valueOf(keyChar).toUpperCase());
        } else if (keyChar == KeyEvent.VK_BACK_SPACE) {
            manejarBorradoUltimaLetra();
        } else if (keyChar == KeyEvent.VK_ENTER) {
            manejarEnvioIntento();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
