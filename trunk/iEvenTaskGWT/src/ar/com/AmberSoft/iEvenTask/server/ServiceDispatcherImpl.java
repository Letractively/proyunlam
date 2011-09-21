package ar.com.AmberSoft.iEvenTask.server;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.client.utils.PagingLoadResult;
import ar.com.AmberSoft.iEvenTask.events.BackgroundEventController;
import ar.com.AmberSoft.iEvenTask.server.utils.Compatibilizable;
import ar.com.AmberSoft.iEvenTask.server.utils.GWTCompatibilityEvaluatorTypes;
import ar.com.AmberSoft.iEvenTask.server.utils.Tools;
import ar.com.AmberSoft.iEvenTask.services.Service;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceClassNotFoundException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceInstantationException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceNameNotFoundException;
import ar.com.AmberSoft.util.ParamsConst;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServiceDispatcherImpl extends RemoteServiceServlet implements
		ServiceDispatcher {
	
	private static Logger logger = Logger.getLogger(ServiceDispatcherImpl.class);
	
	public static String EXECUTE = "execute";
	public static String EMULATE_PACKAGE = "ar.com.AmberSoft.iEvenTask.server.emulate";

	public Map execute(Map params) throws IllegalArgumentException {
		
		logger.debug("Inicio ServiceDispatcher");
		
		//FIXME: Solo de prueba, luego colocar en el login y en el save de relaciones (evaluar si hace falta en el save)
		try{
			BackgroundEventController.getInstance();
		}catch(Exception e){e.printStackTrace();}
		
		if ((params==null)||(params.get(ServiceNameConst.SERVICIO)==null)){
			throw new ServiceNameNotFoundException();
		}
		
		try {
			logger.debug("Invocando " + params.get(ServiceNameConst.SERVICIO));
			Class type = getType(params);
			Map toReturn = invokeExecute(type.newInstance(), params);
			
			if (toReturn!=null){
				
				if (toReturn.get(ParamsConst.PAGING_LOAD_RESULT)!=null){
					Map aux = new PagingLoadResult();
					aux.putAll(toReturn);
					toReturn = aux;
				}
				
				Map map = toReturn.getClass().newInstance();
				Iterator keys = toReturn.keySet().iterator();
				while (keys.hasNext()) {
					Object key = (Object) keys.next();
					Object fieldValue = toReturn.get(key);
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(fieldValue);
					Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
					fieldValue = compatibilizable.adapt(fieldValue);
					map.put(key, fieldValue);
				}
				return map;
			}
			return null;
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			throw new ServiceInstantationException();
		} finally {
			logger.debug("Fin ServiceDispatcher");
		}
	}

	private Map invokeExecute(Object instance, Map params) {
		if ((instance!=null) && (instance instanceof Service)){
			Service service = (Service) instance;
			return service.execute(params);
		}
		return null;
	}

	private Class getType(Map params) {
		String serviceName = (String) params.get(ServiceNameConst.SERVICIO);
		Class type;
		try {
			type = Class.forName(serviceName);
		} catch (ClassNotFoundException e) {
			logger.error(Tools.getStackTrace(e));
			throw new ServiceClassNotFoundException();
		}
		return type;
	}



}
