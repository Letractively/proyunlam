package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.LoginFailureException;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes"})
public class LoginService extends Service {

	private static Logger logger = Logger.getLogger(LoginService.class);
	@Override
	public Map onExecute(Map params) {
		try {
			User user = LDAPUtils.authenticate((String) params.get(ParamsConst.USER), (String) params.get(ParamsConst.PASSWORD));
			
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			request.getSession().setAttribute(ParamsConst.USER, user);
			
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			throw new LoginFailureException();
		}

		return null;	}

	@Override
	public Map onEmulate(Map params) {
		
		
		return null;
	}

}
