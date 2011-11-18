package ar.com.AmberSoft.iEvenTask.shared;

/**
 * Agrupa constantes con expresiones regulares 
 */
public final class RegularExpresions {
	
	public static final String ALPHABET = "^[a-zA-Z_]+$";
	public static final String ALPHANUMERIC = "^[a-zA-Z0-9_]+$"; 
	public static final String NUMERIC = "^[+0-9]+$";
	public static final String EMAIL = "^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$";
	
	/**
	 * Para que no pueda instanciarse la clase
	 */
	private RegularExpresions(){}
	
}
