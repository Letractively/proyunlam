package ar.com.AmberSoft.iEvenTask.server;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceClassNotFoundException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceInstantationException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceNameNotFoundException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServiceDispatcherImpl extends RemoteServiceServlet implements
		ServiceDispatcher {
	
	public static String EXECUTE = "execute";
	public static String EMULATE_PACKAGE = "ar.com.AmberSoft.iEvenTask.server.emulate";

	public Map execute(Map params) throws IllegalArgumentException {
		
		if ((params==null)||(params.get(ServiceNameConst.SERVICIO)==null)){
			throw new ServiceNameNotFoundException();
		}
		
		String serviceName = (String) params.get(ServiceNameConst.SERVICIO);
		Class type;
		try {
			type = Class.forName(serviceName);
		} catch (ClassNotFoundException e) {
			// Si la clase no existe, es probable que se encuentre ejecutando el APP ENGINE
			// Con lo cual se buscará un emulador del servicio
			
			String onlyName = serviceName.substring(serviceName.lastIndexOf("."));
			try {
				type = Class.forName(EMULATE_PACKAGE + onlyName);
			} catch (ClassNotFoundException e1) {
				throw new ServiceClassNotFoundException();
			}
		}
		
		try {
			java.lang.reflect.Method method = type.getDeclaredMethod(EXECUTE, Map.class);
			return (Map) method.invoke(type.newInstance(), params);
		} catch (Exception e) {
			throw new ServiceInstantationException();
		}
	}



}
