package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateRelationCreateTaskService extends Service {

	protected void setAttributes(Map params, RelationWithActionCreateTask relation) {
		
		String eventId = (String) params.get(ParamsConst.EVENT);
		Event event = getEvent(eventId);
		relation.setEvent(event);
		relation.setName((String) params.get(ParamsConst.NAME));
		relation.setUser((String) params.get(ParamsConst.USER));
	}
	
	public Event getEvent(String eventId){
		GetEventService eventService = new GetEventService();
		Map params = new HashMap();
		params.put(ParamsConst.ID, eventId);
		Map result = eventService.execute(params);
		return (Event) result.get(ParamsConst.ENTITY);
	}

	@Override
	public Map onExecute(Map params) {
		RelationWithActionCreateTask relation = new RelationWithActionCreateTask();
		setAttributes(params, relation);
		
		Transaction transaction = getSession().beginTransaction();
		
		getSession().save(relation);
		transaction.commit();

		return null;	
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}
}
