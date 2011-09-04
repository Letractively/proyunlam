package ar.com.AmberSoft.iEvenTask.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.server.utils.Compatibilizable;
import ar.com.AmberSoft.iEvenTask.server.utils.GWTCompatibilityEvaluatorTypes;
import ar.com.AmberSoft.iEvenTask.server.utils.Tools;
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
	
	private static Logger logger = Logger.getLogger(ServiceDispatcherImpl.class);
	
	public static String EXECUTE = "execute";
	public static String EMULATE_PACKAGE = "ar.com.AmberSoft.iEvenTask.server.emulate";

	public Map execute(Map params) throws IllegalArgumentException {
		
		logger.debug("Inicio ServiceDispatcher");
		
		if ((params==null)||(params.get(ServiceNameConst.SERVICIO)==null)){
			throw new ServiceNameNotFoundException();
		}
		
		try {
			logger.debug("Invocando " + params.get(ServiceNameConst.SERVICIO));
			Class type = getType(params);
			Map toReturn = invokeExecute(type.newInstance(), type, params);
			
			if (toReturn!=null){
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

	private Map invokeExecute(Object instance, Class type, Map params) {
		if (!Object.class.getName().equals(type.getClass().getName())){
			Method method;
			try {
				method = type.getDeclaredMethod(EXECUTE, Map.class);
				return (Map) method.invoke(instance, params);
			} catch (SecurityException e) {
				logger.error(Tools.getStackTrace(e));
			} catch (NoSuchMethodException e) {
				logger.error(Tools.getStackTrace(e));
				return invokeExecute(instance, type.getSuperclass(), params);
			} catch (IllegalArgumentException e) {
				logger.error(Tools.getStackTrace(e));
			} catch (IllegalAccessException e) {
				logger.error(Tools.getStackTrace(e));
			} catch (InvocationTargetException e) {
				logger.error(Tools.getStackTrace(e));
			}
		}
		// TODO: En lugar de retornar nulo, lanzar una excepcion ya que no encontro el metodo execute
		return null;
	}

	private Class getType(Map params) {
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
		return type;
	}



}
