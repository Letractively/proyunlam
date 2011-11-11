package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.events.BackgroundEventController;
import ar.com.AmberSoft.iEvenTask.events.BackgroundUserUnlockController;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes","unchecked"})
public class CheckUserLogonService extends Service {

	private static Logger logger = Logger.getLogger(CheckUserLogonService.class);
	
	@Override
	public Map onExecute(Map params) {
		
		// Instancia de ser necesario el controlador de procesos
		try{
			// Activacion del controlador de eventos
			BackgroundEventController.getInstance();
			// Activacion del controlador de bloqueos
			BackgroundUserUnlockController.getInstance();
		}catch(Exception e){logger.error(Tools.getStackTrace(e));}

		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		Map map = new HashMap();
		Object user = request.getSession().getAttribute(ParamsConst.USER);
		if (user!=null){
			map.put(ParamsConst.USER, user);
		}
		
		return map;
	}

	@Override
	public Map onEmulate(Map params) {
		return onExecute(params);
	}

}
