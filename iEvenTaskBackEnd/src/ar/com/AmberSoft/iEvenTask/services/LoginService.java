package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.LoginFailureException;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes"})
public class LoginService extends Service {

	@Override
	public Map onExecute(Map params) {
		try {
			User user = LDAPUtils.authenticate((String) params.get(ParamsConst.USER), (String) params.get(ParamsConst.PASSWORD));
			
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			request.getSession().setAttribute(ParamsConst.USER, user);
			
		} catch (Exception e) {
			throw new LoginFailureException();
		}

		return null;	}

	@Override
	public Map onEmulate(Map params) {
		
		
		return null;
	}

}
