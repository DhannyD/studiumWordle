package es.studium.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VistaRanking extends JFrame {
    private JTable tablaRanking;
    private JButton btnVolver;
    private WordleBD wordleBD;

    public VistaRanking() {
        setTitle("Ranking");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        wordleBD = new WordleBD();

        // Panel para la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        // Obtener datos de la base de datos
        String[] columnas = {"Nombre", "Puntuación"};
        Object[][] datos = obtenerDatosRanking();

        tablaRanking = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tablaRanking);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Panel para el botón de volver
        JPanel panelBoton = new JPanel();
        btnVolver = new JButton("Volver");
        panelBoton.add(btnVolver);

        add(panelTabla, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    public void setControlador(ActionListener controlador) {
        btnVolver.addActionListener(controlador);
    }

    private Object[][] obtenerDatosRanking() {
        List<Object[]> datos = new ArrayList<>();
        String consulta = "SELECT nombreJugadorPuntuacion, puntuacionJugador FROM puntuacion ORDER BY puntuacionJugador DESC LIMIT 10";
        try {
            ResultSet rs = wordleBD.ejecutarConsulta(consulta);
            while (rs.next()) {
                String nombre = rs.getString("nombreJugadorPuntuacion");
                int puntuacion = rs.getInt("puntuacionJugador");
                datos.add(new Object[]{nombre, puntuacion});
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el ranking: " + e.getMessage());
            e.printStackTrace();
        }
        return datos.toArray(new Object[0][]);
    }

    public void mostrar() {
        setVisible(true);
    }

    public void ocultar() {
        setVisible(false);
    }
}
