package ar.com.AmberSoft.iEvenTask.shared;


/**
 * Contiene las constantes que ser�n utilizadas para invocar servicios
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
	
}
