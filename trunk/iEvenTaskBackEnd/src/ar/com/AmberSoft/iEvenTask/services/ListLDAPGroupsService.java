package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.LDAPGroup;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes","unchecked"})
public final class ListLDAPGroupsService extends Service {

	@Override
	public Map onExecute(Map params) {
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);

		Collection<LDAPGroup> groups = LDAPUtils.searchGroups(user.getId(), user.getPassword());
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, groups);
		map.put(ParamsConst.TOTAL_COUNT, groups.size());
		//map.put(ParamsConst.OFFSET, null);
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	@SuppressWarnings("unused")
	@Override
	public Map onEmulate(Map params) {
		LDAPUtils ldapSearch = new LDAPUtils();
		Collection<LDAPGroup> groups = new HashSet<LDAPGroup>();
		
		groups.add(new LDAPGroup("Prueba"));
		groups.add(new LDAPGroup("Prueba"));
		groups.add(new LDAPGroup("Prueba2"));
		
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, groups);
		map.put(ParamsConst.TOTAL_COUNT, groups.size());
		//map.put(ParamsConst.OFFSET, null);
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;	}

}
