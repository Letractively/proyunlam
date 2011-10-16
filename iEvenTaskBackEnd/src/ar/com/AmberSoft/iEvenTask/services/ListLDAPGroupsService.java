package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.LDAPGroup;
import ar.com.AmberSoft.util.LdapSearch;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes","unchecked"})
public final class ListLDAPGroupsService extends Service {

	@SuppressWarnings("static-access")
	@Override
	public Map onExecute(Map params) {
		LdapSearch ldapSearch = new LdapSearch();
		Collection<LDAPGroup> groups = ldapSearch.searchGroups();
		
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
		LdapSearch ldapSearch = new LdapSearch();
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
