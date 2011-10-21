package ar.com.AmberSoft.iEvenTask.shared;

public abstract class ParamsConst {

	private ParamsConst(){}
	
	public static final String CLASS = "class";
	public static final String EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP";
	public static final String EVENT_FILE = "ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles";
	public static final String EVENT_SERVICE = "ar.com.AmberSoft.iEvenTask.backend.entities.EventServices";
	public static final String EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs";
	
	public static final String ERROR = "ERROR";
	public static final String REQUEST = "REQUEST";
	public static final String TRANSACTION_CONTROL = "TRANSACTION_CONTROL";
	
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
	
	public static final String NOMBRE_TAREA = "nombreTarea";
	public static final String FECHA_COMIENZO = "fechaComienzo";
	public static final String FECHA_FIN = "fechaFin";
	public static final String DURACION = "duracion";
	public static final String DESCRIPCION = "descripcion";
	public static final String ID_USUARIO = "id_usuario";
	public static final String ASIGNADO = "asignado";
	public static final String USERS_VIEW = "usersView";
	public static final String VISIBLES = "visibles";
	//constantes para tareas que no son utilizadas
	public static final String HORASASIGNADAS = "horas";
	public static final String FECHAMODIFICACION = "fechaModificacion";
	public static final String ESTADO = "estado";
	public static final String CUMPLIMIENTO = "cumplimiento";
	public static final String TIPO_TAREA = "tipoTarea";
	public static final String COMMENT = "comment";
	public static final String COMENTARIOS = "comentarios";
	public static final String COMENTARIO = "comentario";
	
	//CONSTANTES PARA OBJETIVOS
	public static final String  NOMBRE_OBJETIVO = "nombreObjetivo";
	public static final String  TIPO_OBJETIVO = "tipoObjetivo";
	public static final String  ESCALA_MEDICION = "escalaMedicion";
	public static final String  FECHA_FINALIZACION = "fechaFinalizacion";
	public static final String  PONDERACION = "ponderacion";
	public static final String  ID_USUARIO_ASIGNADO = "idUsuarioAsignado";
	
	public static final String PERIODICITY = "periodicity";
	public static final String EXPIRATION = "expiration";
	public static final String ITERATIONS = "iterations";
	public static final String PATH = "path";
	public static final String IS_DIRECTORY = "isDirectory";
	public static final String PATERN = "patern";
	public static final String CONTROL_TYPE = "controlType";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String FOLDER = "folder";
	
	public static final String CODE = "code";
	
	public static final String FROM_STATE = "fromState";
	public static final String TO_STATE = "toState";
	
	public static final String EVENT = "event";
	
	public static final String DATE = "date";
	public static final String DETAIL = "detail";
	
	public static final String PROCESS = "process";
	public static final String PROFILE = "profile";
	
	public static final String USUARIO = "usuario";
	public static final String FECHA = "fecha";
}
