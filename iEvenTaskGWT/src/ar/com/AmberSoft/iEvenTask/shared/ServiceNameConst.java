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
	public static final String CHECK_USER = "ar.com.AmberSoft.iEvenTask.services.CheckUserLogonService";
	public static final String EXIT = "ar.com.AmberSoft.iEvenTask.services.ExitService";
	
	public static final String BROWSE_FILE = "ar.com.AmberSoft.iEvenTask.services.BrowseFilesService";
	
	public static final String CREATE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.CreateProfileService";
	public static final String UPDATE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.UpdateProfileService";
	public static final String DELETE_PROFILE = "ar.com.AmberSoft.iEvenTask.services.DeleteProfileService";
	public static final String LIST_PROFILE = "ar.com.AmberSoft.iEvenTask.services.ListProfileService";
	
	public static final String CREATE_TASK = "ar.com.AmberSoft.iEvenTask.services.CreateTaskService";
	public static final String UPDATE_TASK = "ar.com.AmberSoft.iEvenTask.services.UpdateTaskService";
	public static final String DELETE_TASK = "ar.com.AmberSoft.iEvenTask.services.DeleteTaskService";
	public static final String LIST_TASK = "ar.com.AmberSoft.iEvenTask.services.ListTaskService";
	public static final String LIST_TASK_WITH_VISIBLE_FILTER = "ar.com.AmberSoft.iEvenTask.services.ListTaskWithVisibleFilterService";
	public static final String LIST_TASK_BY_OBJECTIVE = "ar.com.AmberSoft.iEvenTask.services.ListTaskByObjectiveService";
	public static final String DIVIDE_TASK = "ar.com.AmberSoft.iEvenTask.services.DivideTaskService";

	public static final String CREATE_OBJECTIVE = "ar.com.AmberSoft.iEvenTask.services.CreateObjectiveService";
	public static final String UPDATE_OBJECTIVE = "ar.com.AmberSoft.iEvenTask.services.UpdateObjectiveService";
	public static final String DELETE_OBJECTIVE = "ar.com.AmberSoft.iEvenTask.services.DeleteObjectiveService";
	public static final String LIST_OBJECTIVE = "ar.com.AmberSoft.iEvenTask.services.ListObjectiveService";
	public static final String LIST_OBJECTIVE_WITH_VISIBLE_FILTER = "ar.com.AmberSoft.iEvenTask.services.ListObjectiveWithVisibleFilterService";

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
	public static final String LIST_EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.services.ListEventLDAPService";
	public static final String LIST_EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.services.ListEventLogsService";
	public static final String LIST_EVENT_FILES = "ar.com.AmberSoft.iEvenTask.services.ListEventFilesService";
	public static final String LIST_EVENT_SERVICES = "ar.com.AmberSoft.iEvenTask.services.ListEventServicesService";
	
	public static final String LIST_RELATION = "ar.com.AmberSoft.iEvenTask.services.ListRelationService";
	public static final String CREATE_RELATION_CREATE_TASK = "ar.com.AmberSoft.iEvenTask.services.CreateRelationCreateTaskService";
	public static final String UPDATE_RELATION_CREATE_TASK = "ar.com.AmberSoft.iEvenTask.services.UpdateRelationCreateTaskService";
	public static final String CREATE_RELATION_MODIFY_STATE = "ar.com.AmberSoft.iEvenTask.services.CreateRelationModifyStateService";
	public static final String UPDATE_RELATION_MODIFY_STATE = "ar.com.AmberSoft.iEvenTask.services.UpdateRelationModifyStateService";
	public static final String DELETE_RELATION = "ar.com.AmberSoft.iEvenTask.services.DeleteRelationService";
	
	public static final String BACKGROUND_PROCESS_CONSULTING = "ar.com.AmberSoft.iEvenTask.services.BackgroundProcessService";
	public static final String LIST_USERS = "ar.com.AmberSoft.iEvenTask.services.ListUserService";
	public static final String LIST_GROUPS = "ar.com.AmberSoft.iEvenTask.services.ListLDAPGroupsService";
	
	public static final String ADD_COMMENT = "ar.com.AmberSoft.iEvenTask.services.AddCommentService";
}
