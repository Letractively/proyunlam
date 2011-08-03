package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.exceptions.LoginFailureException;
import ar.com.AmberSoft.util.LDAPClient;
import ar.com.AmberSoft.util.ParamsConst;

public class LoginService extends Service {

	@Override
	public Map execute(Map params) {

		try {
			LDAPClient.authenticate((String) params.get(ParamsConst.USER),
					(String) params.get(ParamsConst.PASSWORD));
		} catch (Exception e) {
			throw new LoginFailureException();
		}

		return null;
	}

}
