package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.util.ParamsConst;

public class SetParamsOnSessionService extends Service {

	@Override
	public Map onExecute(Map params) throws Exception {
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		request.getSession().setAttribute(ParamsConst.PARAMS, params);
		
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		try {
			return onExecute(params);
		} catch (Exception e) {
		}
		return null;
	}

}
