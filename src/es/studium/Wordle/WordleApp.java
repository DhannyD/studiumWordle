package es.studium.Wordle;

import javax.sound.sampled.Clip;

public class WordleApp {
    public static void main(String[] args) {
        // Iniciar música de fondo
        MusicaFondo musica = new MusicaFondo("./Musica/musica.wav");
        musica.reproducir();
        Clip clip = musica.getClip();

        ModeloWordle modelo = new ModeloWordle("CIELO", 5, 6); // Inicialización ejemplo
        VistaMenuPrincipal menuPrincipalVista = new VistaMenuPrincipal(clip);
        VistaSeleccionarDificultad seleccionarDificultadVista = new VistaSeleccionarDificultad();
        VistaJuegoWordle juegoWordleVista = new VistaJuegoWordle();
        VistaPuntuacion vistaPuntuacion = new VistaPuntuacion();
        VistaRanking vistaRanking = new VistaRanking();
        VistaAyuda vistaAyuda = new VistaAyuda();

        seleccionarDificultadVista.setVisible(false);
        juegoWordleVista.setVisible(false);
        vistaPuntuacion.setVisible(false);
        vistaRanking.setVisible(false);
        vistaAyuda.setVisible(false);

        new Controlador(modelo, menuPrincipalVista, seleccionarDificultadVista, juegoWordleVista, vistaPuntuacion, vistaRanking, vistaAyuda);
    }
}
