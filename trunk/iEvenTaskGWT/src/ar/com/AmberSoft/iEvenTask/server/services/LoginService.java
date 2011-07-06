package ar.com.AmberSoft.iEvenTask.server.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.LoginFailureException;
import ar.com.AmberSoft.util.LDAPClient;

public class LoginService extends Service {

	@Override
	public Map execute(Map params) {

		try {
			LDAPClient.authenticate((String) params.get(ParamsConst.USER),
					(String) params.get(ParamsConst.PASSWORD));
		} catch (NoClassDefFoundError e) {
			// Esta excepcion la da cuando se ejecuta desde el GWT Engine
			// Hacemos de cuenta que se loguea normalment
			// Cuando se supere la etapa de desarrollo quitar este catch y dejar solo el de abajo
			return null;
		} catch (Exception e) {
			throw new LoginFailureException();
		}

		return null;
	}

}
