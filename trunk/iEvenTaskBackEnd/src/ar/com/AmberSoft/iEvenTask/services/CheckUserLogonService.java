package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.util.ParamsConst;

public class CheckUserLogonService extends Service {

	@Override
	public Map onExecute(Map params) {
		
		
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
