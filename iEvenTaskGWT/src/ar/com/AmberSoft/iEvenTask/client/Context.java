package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowNotRegisteredException;
import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowPreviouslyRegisteredException;


/**
 * Singleton para la parte de la vista GWT
 * mantiene el contexto de la aplicacion
 * Tener en cuenta que existiran tantos singleton como clientes se conecten a la aplicacion
 * No sirve para mantener por ejemplo cantidad de usuarios o para que exista visibilidad entre usuarios
 * @author Leo
 *
 */
public class Context {

	/**
	 * Instancia unica del contexto para la aplicacion en el cliente actual
	 */
	private static Context context;
	/**
	 * Ventanas que fueron registradas al momento de la instanciacion
	 */
	private Map<Class, Window> registeredWindows;

	/**
	 * Privado para que no pueda ser instanciado 
	 * y que al momento de solicitar una instancia entonces
	 * se retorne siempre la misma
	 */
	private Context(){}
	
	public static Context getInstance(){
		if (context == null){
			context = new Context();
			context.registeredWindows = new HashMap<Class, Window>();
		}
		return context;
	}
	
	/**
	 * Registra la instancia de una nueva ventana si aun no fue registrada
	 * Caso contrario lanza excepcion
	 * @param window
	 * @throws WindowPreviouslyRegisteredException 
	 */
	public void registerWindow(Window window) throws WindowPreviouslyRegisteredException{
		if (registeredWindows.get(window.getClass()) == null){
			registeredWindows.put(window.getClass(), window);
		} else {
			throw new WindowPreviouslyRegisteredException();
		}
	}
	
	/**
	 * Retorna la instancia de una ventana anteriormente registrada
	 * Si no la encuentra lanza una excepcion identificando esta situacion
	 * @return
	 */
	public Window getWindowInstance(Class windowClass){
		Window window = registeredWindows.get(windowClass);
		if (window!=null){
			return window;
		}
		throw new WindowNotRegisteredException();
	}
	
	
}