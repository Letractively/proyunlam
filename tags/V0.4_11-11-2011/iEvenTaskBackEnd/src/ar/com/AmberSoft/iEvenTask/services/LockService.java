package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;

public class LockService extends Service {

	@Override
	public Map onExecute(Map params) {
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		Map map = new HashMap();
		User userLoggon = (User)request.getSession().getAttribute(ParamsConst.USER);
		
		Map resp = new HashMap();
		User userLock = EntitiesManager.lock(params.get(ParamsConst.ID), userLoggon);
		if (userLock!=null){
			resp.put(ParamsConst.USER, userLock);
		}
		return resp;
	}

	@Override
	public Map onEmulate(Map params) {
		return onExecute(params);
	}

}
