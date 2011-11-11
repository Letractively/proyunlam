package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes","unchecked"})
public class CreateRelationCreateTaskService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params)  throws Exception {
		RelationWithActionCreateTask relation = new RelationWithActionCreateTask();
		String eventId = (String) params.get(ParamsConst.EVENT);
		Event event = getEvent(eventId);
		relation.setEvent(event);
		relation.setName((String) params.get(ParamsConst.NAME));
		relation.setUser((String) params.get(ParamsConst.USER));
		setVisibles(params, relation);
		return relation;
	}
	
	public Event getEvent(String eventId)  throws Exception {
		GetEventService eventService = new GetEventService();
		Map params = new HashMap();
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		params.put(ParamsConst.ID, eventId);
		Map result = eventService.execute(params);
		return (Event) result.get(ParamsConst.ENTITY);
	}
	
	public void setVisibles(Map params, Relation relation) {
		Collection usersView = (Collection) params.get(ParamsConst.USERS_VIEW);
		if (usersView!=null){
			Iterator<String> itUsers = usersView.iterator();
			while (itUsers.hasNext()) {
				String actual = (String) itUsers.next();
				relation.addVisible(actual);	
			}
		}
	}

}
