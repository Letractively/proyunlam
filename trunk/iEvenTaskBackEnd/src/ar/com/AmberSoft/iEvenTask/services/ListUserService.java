package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.PKGenerator;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes","unchecked"})
public class ListUserService extends Service {

	private static int i = 0;
	
	@Override
	public Map onExecute(Map params) {
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);

		Collection<User> users = LDAPUtils.search(user.getId(), user.getPassword());
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, users);
		map.put(ParamsConst.TOTAL_COUNT, users.size());
		//map.put(ParamsConst.OFFSET, null);
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	@Override
	public Map onEmulate(Map params) {

		i=0;
		Collection<User> users = new ArrayList<User>();
		
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		users.add(generateNewUser());
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, users);
		map.put(ParamsConst.TOTAL_COUNT, users.size());
		//map.put(ParamsConst.OFFSET, null);
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	public User generateNewUser() {
		User user = new User();
		user.setId(new Integer(i).toString());
		user.setName("Usuario" + user.getId());
		user.setLastLogon(new Date());
		i++;
		return user;
	}

}