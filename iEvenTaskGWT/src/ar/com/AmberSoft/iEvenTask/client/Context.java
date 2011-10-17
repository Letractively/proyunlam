package ar.com.AmberSoft.iEvenTask.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowNotRegisteredException;
import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowPreviouslyRegisteredException;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;


/**
 * Singleton para la parte de la vista GWT
 * mantiene el contexto de la aplicacion
 * Tener en cuenta que existiran tantos singleton como clientes se conecten a la aplicacion
 * No sirve para mantener por ejemplo cantidad de usuarios o para que exista visibilidad entre usuarios
 * @author Leo
 *
 */
public class Context {
	
	public static final String VACIO = "";
	public static final String BARRA_R = "\r";
	public static final String BARRA_N = "\n";
	public static final String COMMENT_FORMAT_START = "<STRONG><EM><FONT size=2>";
	public static final String COMMENT_FORMAT_END = "</FONT></EM></STRONG>"; 
	public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";
	public static final String GUION_MEDIO = " - ";
	public static final String DOS_PUNTOS = ":";
	public static final String ABRE_P = "<P>";
	public static final String CIERRA_P = "</P>";

	/**
	 * Instancia unica del contexto para la aplicacion en el cliente actual
	 */
	private static Context context;
	/**
	 * Ventanas que fueron registradas al momento de la instanciacion
	 */
	private Map<Class, Window> registeredWindows;
	
	private String usuario;
	private String perfil;
	private boolean sesion = false;
	/**
	 * HTML con los comentarios de las tareas
	 * que se encuentra en la pantalla principal
	 */
	private HtmlEditor htmlEditor;
	
	//private Html html;
	
	/*public Html getHtml() {
		return html;
	}
	
	public void addHtml(String html){
		this.html.setHtml(this.getHtml().getHtml() + html);
		this.html.repaint();
	}

	public void setHtml(Html html) {
		this.html = html;
	}*/

	public HtmlEditor getHtmlEditor() {
		return htmlEditor;
	}
	
	public void addHtml(String html){
		String old = this.htmlEditor.getValue();
		if (old != null){
			this.htmlEditor.setValue(old + "\r\n" + html);
		} else {
			this.htmlEditor.setValue(html);
		}
	}

	public void addComment(String comment, Date date, String usuario){
		StringBuffer textToAdd = new StringBuffer();
		
		textToAdd.append(ABRE_P);
		textToAdd.append(COMMENT_FORMAT_START);
		textToAdd.append(DateTimeFormat.getFormat(DATE_FORMAT).format(date));
		textToAdd.append(GUION_MEDIO);
		textToAdd.append(usuario);
		textToAdd.append(DOS_PUNTOS);
		textToAdd.append(COMMENT_FORMAT_END);
		textToAdd.append(CIERRA_P);
		textToAdd.append(BARRA_R);
		textToAdd.append(BARRA_N);
		textToAdd.append(ABRE_P);
		textToAdd.append(comment);
		textToAdd.append(CIERRA_P);
		
		if (htmlEditor.getValue()!=null){
			textToAdd.append(BARRA_R);
			textToAdd.append(BARRA_N);
			textToAdd.append(htmlEditor.getValue());
		}

		htmlEditor.setValue(textToAdd.toString());
	}

	public void setHtmlEditor(HtmlEditor htmlEditor) {
		this.htmlEditor = htmlEditor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public boolean isSesion() {
		return sesion;
	}

	public void setSesion(boolean sesion) {
		this.sesion = sesion;
	}

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
	
	/**
	 * Muestra una ventana
	 * Si ya fue creada previamente muestra dicha ventana
	 * Sino la crea
	 * @param windowClass
	 */
	public void windowShow(Window window){
		Window windowContextRegistered = window;
		
		try {
			context.registerWindow(windowContextRegistered);
		} catch (WindowPreviouslyRegisteredException e){
			windowContextRegistered = Context.getInstance().getWindowInstance(window.getClass());
		}
		windowContextRegistered.show();

	}
	
	/**
	 * Retorna una referencia a la ventana de detalles de ejecucion
	 * @return
	 */
	public ExecutionDetailWindow getExecutionDetailWindow(){
		ExecutionDetailWindow detailWindow = new ExecutionDetailWindow();
		try {
			context.registerWindow(detailWindow);
		} catch (WindowPreviouslyRegisteredException e){
			detailWindow = (ExecutionDetailWindow) Context.getInstance().getWindowInstance(detailWindow.getClass());
		}
		return detailWindow;
	}
	

	/**
	 * Agrega un detalle de ejecucion a la ventana correspondiente
	 * @param detail
	 */
	public void addDetailExecution(String detail){
		ExecutionDetailWindow detailWindow = getExecutionDetailWindow();
		detailWindow.addDetailExecution(new Date(), detail);
	}
	
		
	
	
	
	
}
