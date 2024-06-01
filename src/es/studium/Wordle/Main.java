package es.studium.Wordle;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		File file = new File("imagenes/fondo.jpg");
		System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
		    System.out.println("Archivo no encontrado.");
		}

	}

}
