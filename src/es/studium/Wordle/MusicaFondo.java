package es.studium.Wordle;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicaFondo {
    private Clip clip;

    public MusicaFondo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Para repetir la música en bucle
        }
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void cerrar() {
        if (clip != null) {
            clip.close();
        }
    }

    public Clip getClip() {
        return clip;
    }
}
