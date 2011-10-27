package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.PeriodicEventListener;

import ar.com.AmberSoft.iEvenTask.backend.entities.Permission;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
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
			
			setUserInSession(params, user);
			
			map.put(ParamsConst.USER, user);
			
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			throw new LoginFailureException();
		}

		return map;	
	}

	public void setUserInSession(Map params, User user) {
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		request.getSession().setAttribute(ParamsConst.USER, user);
		String interval = config.getString("max.inactive.interval");
		if (interval!=null){
			request.getSession().setMaxInactiveInterval(new Integer(SEG_X_MIN * Integer.parseInt(interval)));
		}
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
		user.setPassword("pass".getBytes());
		Profile profile = new Profile();
		profile.setId(-1);
		Set<Permission> permissions = new HashSet<Permission>();
		profile.setPermissions(permissions);

		addPermission(permissions, "1", "");
		addPermission(permissions, "2", "");
		addPermission(permissions, "3", "");
		addPermission(permissions, "4", "");
		addPermission(permissions, "5", "");
		addPermission(permissions, "6", "");
		addPermission(permissions, "7", "");
		addPermission(permissions, "8", "");
		addPermission(permissions, "9", "");
		addPermission(permissions, "10", "");
		addPermission(permissions, "11", "");
		addPermission(permissions, "12", "");
		
		profile.setPermissions(permissions);
		user.setProfile(profile);		
		
		setUserInSession(params, user);
		
		map.put(ParamsConst.USER, user);
		return map;
	}

	public void addPermission(Set<Permission> permissions, String id,
			String description) {
		Permission permission = new Permission();
		permission.setId(id);
		permission.setDescription(description);
		permissions.add(permission);
	}

}
