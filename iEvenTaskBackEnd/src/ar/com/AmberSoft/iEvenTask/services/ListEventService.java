package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
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
		addEventFile(list);
		addEventLDAP(list);
		addEventLDAP(list);
		addEventService(list);
		addEventService(list);
		addEventFile(list);
		addEventLDAP(list);
		addEventLogs(list);
		addEventLogs(list);
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

	private void addEventFile(Collection list) {
		EventFiles event = new EventFiles();
		event.setIterations(event.getId());
		event.setName("EventFile" + event.getId().toString());
		event.setPeriodicity(event.getId());
		event.setPath("Path" + event.getId().toString());
		event.setControlType(new Integer(1));
		list.add(event);
	}

	private void addEventLogs(Collection list) {
		EventLogs event = new EventLogs();
		event.setIterations(event.getId());
		event.setName("EventLogs" + event.getId().toString());
		event.setPeriodicity(event.getId());
		event.setPath("Path" + event.getId().toString());
		event.setPatern("Patern" + event.getId().toString());
		list.add(event);
	}

	private void addEventService(Collection list) {
		EventServices event = new EventServices();
		event.setIterations(event.getId());
		event.setName("EventService" + event.getId().toString());
		event.setPeriodicity(event.getId());
		event.setHost("Host" + event.getId().toString());
		event.setPort(event.getId().toString());
		list.add(event);
	}
	
}
