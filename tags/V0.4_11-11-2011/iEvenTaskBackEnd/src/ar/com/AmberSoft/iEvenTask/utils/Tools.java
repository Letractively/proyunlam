package ar.com.AmberSoft.iEvenTask.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

public class Tools {
	
	@SuppressWarnings("unused")
	private ResourceBundle config = ResourceBundle.getBundle("config");
	
	/**
	 * Retorna en un String la traza del error
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pilaMensajes = new PrintWriter(sWriter);
		e.printStackTrace(pilaMensajes);
		String stackTrace = sWriter.toString();
		return stackTrace;
	}
	
	
	
}
