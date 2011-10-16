package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.shared.exceptions.LoginFailureException;
import ar.com.AmberSoft.util.LDAPClient;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes"})
public class LoginService extends Service {

	@Override
	public Map onExecute(Map params) {
		try {
			LDAPClient.authenticate((String) params.get(ParamsConst.USER),
					(String) params.get(ParamsConst.PASSWORD));
			
			// TODO: Obtener el usuario real y setearlo en session
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			request.getSession().setAttribute(ParamsConst.USER, params.get(ParamsConst.USER));
			
		} catch (Exception e) {
			throw new LoginFailureException();
		}

		return null;	}

	@Override
	public Map onEmulate(Map params) {
		
		
		return null;
	}

}
