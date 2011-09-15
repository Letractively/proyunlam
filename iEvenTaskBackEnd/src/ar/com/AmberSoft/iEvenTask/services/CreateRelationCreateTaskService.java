package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateRelationCreateTaskService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		RelationWithActionCreateTask relation = new RelationWithActionCreateTask();
		String eventId = (String) params.get(ParamsConst.EVENT);
		Event event = getEvent(eventId);
		relation.setEvent(event);
		relation.setName((String) params.get(ParamsConst.NAME));
		relation.setUser((String) params.get(ParamsConst.USER));
		return relation;
	}
	
	public Event getEvent(String eventId){
		GetEventService eventService = new GetEventService();
		Map params = new HashMap();
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		params.put(ParamsConst.ID, eventId);
		Map result = eventService.execute(params);
		return (Event) result.get(ParamsConst.ENTITY);
	}

}
