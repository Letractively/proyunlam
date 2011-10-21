package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
	private ResourceBundle config = ResourceBundle.getBundle("config");
	public static Integer SEG_X_MIN = 60;
	
	@Override
	public Map onExecute(Map params) {
		Map map = new HashMap();
		try {
			User user = LDAPUtils.authenticate((String) params.get(ParamsConst.USER), (String) params.get(ParamsConst.PASSWORD));
			
			if (user.getProfile()==null){
				//FIXME: Lanzar excepcion ya que el usuario no tiene asociado un perfil valido dentro de la aplicacion
			}
			
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			request.getSession().setAttribute(ParamsConst.USER, user);
			String interval = config.getString("max.inactive.interval");
			if (interval!=null){
				request.getSession().setMaxInactiveInterval(new Integer(SEG_X_MIN * Integer.parseInt(interval)));
			}
			
			map.put(ParamsConst.USER, user);
			
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			throw new LoginFailureException();
		}

		return map;	
	}

	private Boolean isTransactionControl(Map params) {
		return Boolean.FALSE;
	}
	
	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		User user = new User();
		user.setId("USEREMULE");
		user.setName("Usuario Emulado");
		map.put(ParamsConst.USER, user);
		return map;
	}

}
