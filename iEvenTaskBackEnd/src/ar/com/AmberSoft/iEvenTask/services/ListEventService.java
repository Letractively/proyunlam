package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

public class ListEventService extends ListService {

	@Override
	protected String getEntity() {
		return Event.class.getName();
	}

	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		Collection list = new ArrayList();
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
		addEventLDAP(list);
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

	private void addEventLDAP(Collection list) {
		EventLDAP eventLDAP = new EventLDAP();
		eventLDAP.setCode(eventLDAP.getId().toString());
		eventLDAP.setIterations(eventLDAP.getId());
		eventLDAP.setName("EventLDAP" + eventLDAP.getId().toString());
		eventLDAP.setPeriodicity(eventLDAP.getId());
		list.add(eventLDAP);
	}

}
