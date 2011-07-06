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
	
	public static final String LOGIN = "ar.com.AmberSoft.iEvenTask.server.services.LoginService";
	
}
