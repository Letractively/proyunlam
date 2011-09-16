package ar.com.AmberSoft.util;

/**
 * Es MUY IMPORTANTE que los campos mencionados en estas constantes,
 * se correspondan textualmente con los campos de los beans que se persisten
 * para que pueda obtenerse su valor dinamicamente.
 * Si se escriben en mayuscula no se podran obtener.
 * @author Leo
 *
 */
public abstract class ParamsConst {

	private ParamsConst(){}
	
	public static final String PAGING_LOAD_RESULT = "PAGING_LOAD_RESULT";
	
	public static final String DATA = "DATA";
	public static final String OFFSET = "OFFSET";
	public static final String TOTAL_COUNT = "TOTAL_COUNT";
	public static final String LIST = "LIST";
	public static final String USER = "user";
	public static final String PASSWORD = "PASSWORD";
	public static final String SELECT = "SELECT";
	
	public static final String IDS = "ids";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String CONNECTION = "connection";
	public static final String GROUP = "groupLDAP";
	public static final String PERMISSIONS = "permissions";
	public static final String CHECK_OBJECTIVE = "checkObjective";
	public static final String CHECK_ADMIN = "checkAdmin";
	
	public static final String FECHACREACION = "fechaCreacion";
	public static final String FECHAFIN = "fechaFin";
	public static final String HORASASIGNADAS = "horas";
	public static final String ID_USUARIO = "idUsuario";
	public static final String FECHAMODIFICACION = "fechaModificacion";
	public static final String ESTADO = "estado";
	public static final String CUMPLIMIENTO = "cumplimiento";
	public static final String TIPO_TAREA = "tipoTarea";
	
	public static final String PERIODICITY = "periodicity";
	public static final String EXPIRATION = "expiration";
	public static final String ITERATIONS = "iterations";
	public static final String PATH = "path";
	public static final String PATERN = "patern";
	public static final String CONTROL_TYPE = "controlType";
	public static final String HOST = "host";
	public static final String PORT = "port";
	
	public static final String CODE = "code";
	
	public static final String FROM_STATE = "fromState";
	public static final String TO_STATE = "toState";
	
	public static final String ENTITY = "entity";
	
	public static final String EVENT = "event";
	
	public static final String TRANSACTION_CONTROL = "TRANSACTION_CONTROL";
}
