package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowNotRegisteredException;
import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowPreviouslyRegisteredException;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.google.gwt.i18n.client.DateTimeFormat;


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
	
	private Map usuario;
	private Map avaiable;
	private String perfil;
	private boolean sesion = false;
	/**
	 * HTML con los comentarios de las tareas
	 * que se encuentra en la pantalla principal
	 */
	private HtmlEditor htmlEditor;
	
	/**
	 * Grilla de tareas de la pantalla principal
	 */
	private Grid taskGrid;
	/**
	 * Grilla de objetivos de la pantalla principal
	 */
	private Grid objectiveGrid;
	
	public Grid getTaskGrid() {
		return taskGrid;
	}

	public void setTaskGrid(Grid taskGrid) {
		this.taskGrid = taskGrid;
	}

	public Grid getObjectiveGrid() {
		return objectiveGrid;
	}

	public void setObjectiveGrid(Grid objectiveGrid) {
		this.objectiveGrid = objectiveGrid;
	}

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

	public void addComment(Integer id, String comment, Date date, String usuario){
		addComment(comment, date, usuario);
		Map actualEnPrincipal = Context.getInstance().getTaskGrid().search(ParamsConst.ID, id);
		Collection comentarios = (Collection) actualEnPrincipal.get(ParamsConst.COMENTARIOS);
		if (comentarios==null){
			comentarios = new ArrayList();
		}
		Map comentario = new HashMap();
		comentario.put(ParamsConst.COMENTARIO, comment);
		comentario.put(ParamsConst.FECHA, date); 
		comentario.put(ParamsConst.USUARIO, usuario);
		comentarios.add(comentario);
		actualEnPrincipal.put(ParamsConst.COMENTARIOS, comentarios);
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

	public String getUserID(){
		return (String) usuario.get(ParamsConst.ID);
	}
	
	public String getUserName(){
		if (usuario!=null){
			return (String) usuario.get(ParamsConst.NAME);
		}
		return "";
	}
	
	public Map getUsuario() {
		return usuario;
	}

	public void setUsuario(Map usuario) {
		this.usuario = usuario;
		avaiable = new HashMap();
		Map profile = (Map) usuario.get(ParamsConst.PROFILE);
		if (profile!=null){
			Collection permisos = (Collection) profile.get(ParamsConst.PERMISSIONS);
			Iterator<Map> itPermisos = permisos.iterator();
			while (itPermisos.hasNext()) {
				Map actual = (Map) itPermisos.next();
				avaiable.put(actual.get(ParamsConst.ID), actual);
			}
			perfil = (String) profile.get(ParamsConst.GROUP);
		} else {
			DialogFactory.error("Atencion!!! El usuario no tiene perfil asignado.");
		}
		
		
	}

	public String getPerfil() {
		return perfil;
	}

	public boolean isSesion() {
		return sesion;
	}
	
	public Boolean isAvaiable(String code){
		if ((avaiable!=null) && (avaiable.get(code)!=null)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
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
	
		
	public void validateUserExpired(Throwable e){
		if ((e.getMessage()!=null) && (e.getMessage().contains("UserExpiredException"))){
			DialogFactory.info("Su session a expirado.");
			IEvenTask.iniciarLogin();
		}
	}
	
	
	
}
