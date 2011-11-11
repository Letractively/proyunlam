package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes"})
public class ExitService extends Service {

	@Override
	public Map onExecute(Map params) {
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User userLoggon = (User)request.getSession().getAttribute(ParamsConst.USER);
		EntitiesManager.unlockAll(userLoggon);
		request.getSession().removeAttribute(ParamsConst.USER);
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
