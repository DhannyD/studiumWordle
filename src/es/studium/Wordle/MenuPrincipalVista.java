package es.studium.Wordle;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;


public class MenuPrincipalVista implements ActionListener, WindowListener {
	Frame ventana = new Frame("Menu Principal");
    Button btnNuevaPartida = new Button("Nueva Partida");
    Button btnRanking = new Button("Ranking");
    Button btnAyuda = new Button("Ayuda");
	
    public MenuPrincipalVista() {
         // Título del Frame
        ventana.setSize(400, 300); // Tamaño del Frame
        ventana.setLayout(new GridBagLayout()); // Usar GridBagLayout para centrar los componentes
        ventana.setLocationRelativeTo(null); // Centrar ventana en la pantalla

        // Panel para contener los botones
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(150, 120)); // Tamaño del panel de botones
        
        btnNuevaPartida.setPreferredSize(new Dimension(120, 30));     
        btnRanking.setPreferredSize(new Dimension(120, 30));        
        btnAyuda.setPreferredSize(new Dimension(120, 30));

        btnNuevaPartida.addActionListener(this);
        btnRanking.addActionListener(this);
        btnAyuda.addActionListener(this);
        
        // Añadir botones al panel
        buttonPanel.add(btnNuevaPartida);
        buttonPanel.add(btnRanking);
        buttonPanel.add(btnAyuda);
        
        // Añadir panel de botones al centro del Frame usando GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el panel dentro del layout
        ventana.add(buttonPanel, gbc);

        // Configurar el cierre del Frame
        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Hacer visible el Frame
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipalVista(); // Crear la instancia de la ventana
    }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	if(e.getSource().equals(btnNuevaPartida)){
		ventana.setVisible(false);
		new SeleccionarDificultad();
	}
	}
		
}

