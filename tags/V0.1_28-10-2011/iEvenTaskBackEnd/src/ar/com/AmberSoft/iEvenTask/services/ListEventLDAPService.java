package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ListEventLDAPService extends ListEventService {
	@Override
	protected String getEntity() {
		return EventLDAP.class.getName();
	}

	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		Collection list = new ArrayList();
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}
}
