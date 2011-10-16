package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.LdapSearch;
import ar.com.AmberSoft.util.PKGenerator;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes","unchecked"})
public class ListUserService extends Service {

	@SuppressWarnings("static-access")
	@Override
	public Map onExecute(Map params) {
		
		LdapSearch ldapSearch = new LdapSearch();
		Collection<User> users = ldapSearch.search();
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, users);
		map.put(ParamsConst.TOTAL_COUNT, users.size());
		//map.put(ParamsConst.OFFSET, null);
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	@Override
	public Map onEmulate(Map params) {

		Collection<User> users = new ArrayList<User>();
		
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
		PKGenerator pkGenerator = new PKGenerator();
		user.setId(pkGenerator.getPk());
		user.setName("Usuario" + pkGenerator.getPk());
		user.setLastLogon(new Date());
		return user;
	}

}
