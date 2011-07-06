package ar.com.AmberSoft.iEvenTask.server.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceClassNotFoundException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceInstantationException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceNameNotFoundException;

public abstract class Service {
	
	/**
	 * Retorna el servicio a utilizar instanciado
	 * @param map
	 * @return
	 * @throws ServiceNameNotFoundException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Service getService(Map map){
		if ((map==null)||(map.get(ServiceNameConst.SERVICIO)==null)){
			throw new ServiceNameNotFoundException();
		}
		String serviceName = (String) map.get(ServiceNameConst.SERVICIO);
		Class type;
		try {
			type = Class.forName(serviceName);
		} catch (ClassNotFoundException e) {
			throw new ServiceClassNotFoundException();
		}
		
		try {
			return (Service) type.newInstance();
		} catch (Exception e) {
			throw new ServiceInstantationException();
		}
	}

	public abstract Map execute(Map params);
	
	
}
