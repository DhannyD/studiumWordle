package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;

public class VistaMenuPrincipal extends JFrame {
    JButton btnNuevaPartida = new JButton("Nueva Partida");
    JButton btnRanking = new JButton("Ranking");
    JButton btnAyuda = new JButton("Ayuda");
    JSlider volumeSlider;
    Clip clip;

    public VistaMenuPrincipal(Clip clip) {
        this.clip = clip;
        setTitle("Menu Principal");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(150, 120));

        btnNuevaPartida.setPreferredSize(new Dimension(120, 30));
        btnRanking.setPreferredSize(new Dimension(120, 30));
        btnAyuda.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(btnNuevaPartida);
        buttonPanel.add(btnRanking);
        buttonPanel.add(btnAyuda);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Crear el control de volumen
        volumeSlider = new JSlider(0, 100, 50); // Rango de volumen de 0 a 100 con valor inicial 50
        volumeSlider.setPreferredSize(new Dimension(100, 20)); // Hacer más pequeño el control de volumen
        volumeSlider.addChangeListener(e -> {
            float volume = volumeSlider.getValue() / 100f;
            setVolume(volume);
        });

        JPanel volumePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        volumePanel.add(new JLabel("Volumen:"));
        volumePanel.add(volumeSlider);

        gbc.gridx = 0; // Colocar en la esquina inferior izquierda
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(volumePanel, gbc);

        setVisible(true);
    }

    public void setControlador(ActionListener controlador) {
        btnNuevaPartida.addActionListener(controlador);
        btnRanking.addActionListener(controlador);
        btnAyuda.addActionListener(controlador);
    }

    public void setVolume(float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}
