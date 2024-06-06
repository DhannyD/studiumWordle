package es.studium.Wordle;

import java.util.Random;

public class ModeloWordle {
	private String palabraObjetivo;
	private int longitudPalabra;
	private int maxIntentos;
	private int intentosRealizados;			
	private WordleBD wordleBD;

	private static final String[] PALABRAS_4_LETRAS = { "GATO", "LUNA", "RANA", "ROSA", "SOL", "NIÑO", "MESA", "VINO",
			"CAMA", "PATO", "LAGO", "FLOR", "LAMA", "RIOS", "ALMA", "LADR" };

	private static final String[] PALABRAS_5_LETRAS = { "CIELO", "NIÑOS", "RATON", "FOTOS", "PERRO", "CASAS", "PLUMA",
			"PIANO", "GATOS", "ARBOL", "CANTO", "PESCA", "LLAVE", "PIEZA", "FUEGO", "FRUTA" };
	private static final String[] PALABRAS_6_LETRAS = { "COCHEZ", "FELINO", "PLANTA", "FRUTAS", "HOMBRE", "MUJER",
			"LIBROS", "CASITA", "NARIZO", "CUERPO", "BEBIDO", "MUEBLO", "CAMION", "BOSQUE", "RUIDOS", "HUEVOS" };
	private static final String[] PALABRAS_7_LETRAS = { "HORMIGA", "LIBRERO", "MIRADOR", "RODILLA", "ANIMALO",
			"ESPEJOS", "CAMINOS", "FUEGOSO", "PALMERA", "PULSERA", "VENTANA", "TECLADO", "VERDURA", "FRUTERO",
			"BOTELLA", "GUITARR" };

	public ModeloWordle(String palabraObjetivo, int longitudPalabra, int maxIntentos) {
		this.palabraObjetivo = palabraObjetivo;
		this.longitudPalabra = longitudPalabra;
		this.maxIntentos = maxIntentos;
		this.intentosRealizados = 0;
		this.wordleBD = new WordleBD();
	}

	public String getPalabraObjetivo() {
		return palabraObjetivo;
	}

	public int getLongitudPalabra() {
		return longitudPalabra;
	}

	public int getMaxIntentos() {
		return maxIntentos;
	}

	public void incrementarIntentos() {
		intentosRealizados++;
	}

	public int getIntentosRealizados() {
		return intentosRealizados;
	}

	public int calcularPuntuacionPorPrecision(String intento) {
		int puntuacion = 0;
		for (int i = 0; i < intento.length(); i++) {
			if (intento.charAt(i) == palabraObjetivo.charAt(i)) {
				puntuacion += 75; // Puntos por cada letra correcta
			}
		}
		return puntuacion - (intentosRealizados * 10); // Penalización por intentos
	}

	public String seleccionarPalabraAleatoria(int longitudPalabra) {
		Random random = new Random();
		switch (longitudPalabra) {
		case 4:
			return PALABRAS_4_LETRAS[random.nextInt(PALABRAS_4_LETRAS.length)];
		case 5:
			return PALABRAS_5_LETRAS[random.nextInt(PALABRAS_5_LETRAS.length)];
		case 6:
			return PALABRAS_6_LETRAS[random.nextInt(PALABRAS_6_LETRAS.length)];
		case 7:
			return PALABRAS_7_LETRAS[random.nextInt(PALABRAS_7_LETRAS.length)];
		default:
			throw new IllegalArgumentException("Longitud de palabra no soportada: " + longitudPalabra);
		}
	}
}
