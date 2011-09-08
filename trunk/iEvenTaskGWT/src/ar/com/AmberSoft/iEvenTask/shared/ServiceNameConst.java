package ar.com.AmberSoft.iEvenTask.shared;


/**
 * Contiene las constantes que serán utilizadas para invocar servicios
 * @author Leo
 *
 */
public abstract class ServiceNameConst {

	/**
	 * Es privado para que la clase no pueda ser instanciada
	 */
	private ServiceNameConst(){}
	
	public static final String SERVICIO = "Servicio";
	
	public static final String LOGIN = "ar.com.AmberSoft.iEvenTask.services.LoginService";
	
	public static final String CREATE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.CreateProfileService";
	public static final String UPDATE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.UpdateProfileService";
	public static final String DELETE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.DeleteProfileService";
	public static final String LIST_PROFILE = "ar.com.AmberSoft.iEvenTask.services.ListProfileService";
	
	public static final String CREATE_TASK = "ar.com.AmberSoft.iEvenTask.services.CreateTaskService";
	public static final String UPDATE_TASK = "ar.com.AmberSoft.iEvenTask.services.UpdateTaskService";
	public static final String LIST_TASK = "ar.com.AmberSoft.iEvenTask.services.ListTaskService";

	public static final String CREATE_EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.services.CreateEventLDAPService";
	public static final String CREATE_EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.services.CreateEventLogsService";
	public static final String CREATE_EVENT_FILES = "ar.com.AmberSoft.iEvenTask.services.CreateEventFilesService";
	public static final String CREATE_EVENT_SERVICES = "ar.com.AmberSoft.iEvenTask.services.CreateEventServicesService";
	
	public static final String UPDATE_EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.services.UpdateEventLDAPService";
	public static final String UPDATE_EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.services.UpdateEventLogsService";
	public static final String UPDATE_EVENT_FILES = "ar.com.AmberSoft.iEvenTask.services.UpdateEventFilesService";
	public static final String UPDATE_EVENT_SERVICES = "ar.com.AmberSoft.iEvenTask.services.UpdateEventServicesService";
	
	public static final String DELETE_EVENT = "ar.com.AmberSoft.iEvenTask.services.DeleteEventService";
	public static final String LIST_EVENT = "ar.com.AmberSoft.iEvenTask.services.ListEventService";

}
