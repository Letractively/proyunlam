package ar.com.AmberSoft.iEvenTask.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.client.utils.PagingLoadResult;
import ar.com.AmberSoft.iEvenTask.server.utils.Compatibilizable;
import ar.com.AmberSoft.iEvenTask.server.utils.GWTCompatibilityEvaluatorTypes;
import ar.com.AmberSoft.iEvenTask.server.utils.Tools;
import ar.com.AmberSoft.iEvenTask.services.Service;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.BDConnectionException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceClassNotFoundException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceInstantationException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceNameNotFoundException;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.UserExpiredException;
import ar.com.AmberSoft.util.ParamsConst;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
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

		params.put(ParamsConst.REQUEST, this.getThreadLocalRequest());
		
		validateActiveSession(params);
		
		try {
			logger.debug("Invocando " + params.get(ServiceNameConst.SERVICIO));
			
			Map toReturn = invoke(params);
			
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
		} catch (JDBCConnectionException e) {
			logger.error(Tools.getStackTrace(e));
			throw new BDConnectionException();
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			throw new ServiceInstantationException();
		} finally {
			logger.debug("Fin ServiceDispatcher");
		}
	}

	public Map invoke(Map params) throws Exception, InstantiationException,
			IllegalAccessException {
		Class type = getType(params);
		Map toReturn = invokeExecute(type.newInstance(), params);
		return toReturn;
	}

	public void validateActiveSession(Map params) {
		Collection excludes = new ArrayList<String>();
		excludes.add("ar.com.AmberSoft.iEvenTask.services.LoginService");
		excludes.add("ar.com.AmberSoft.iEvenTask.services.CheckUserLogonService");
		excludes.add("ar.com.AmberSoft.iEvenTask.services.ExitService");
		
		//Validamos que el usuario se encuentre activo
		Boolean validar = Boolean.TRUE;
		String servicio = (String) params.get(ServiceNameConst.SERVICIO);
		
		Iterator itExcludes = excludes.iterator();
		while (itExcludes.hasNext()) {
			String exc = (String ) itExcludes.next();
			if (servicio.equals(exc)){
				validar = Boolean.FALSE;
				break;
			}
		}
		
		if (validar){
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			User user = null;
			if (request!=null){
				user = (User) request.getSession().getAttribute(ParamsConst.USER);
				if (user==null){
					// Se inactivo la session, lanzar excepcion
					throw new UserExpiredException();
				}
			}
		}
	}

	private static Map invokeExecute(Object instance, Map params)  throws Exception {
		if ((instance!=null) && (instance instanceof Service)){
			Service service = (Service) instance;
			return service.execute(params);
		}
		return null;
	}

	private static Class getType(Map params) {
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
