package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithModifyStateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateRelationModifyStateService extends CreateService {

	@Override
	public Entity getEntity(Map params) throws Exception {
		RelationWithModifyStateTask relation = new RelationWithModifyStateTask();
		String eventId = (String) params.get(ParamsConst.EVENT);
		Event event = getEvent(eventId);
		relation.setEvent(event);
		
		relation.setFromState((String) params.get(ParamsConst.FROM_STATE));
		relation.setToState((String) params.get(ParamsConst.TO_STATE));
	
		setTareas(params, relation);
		
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
	
	public void setTareas(Map params, RelationWithModifyStateTask relation) {
		Collection tareas = (Collection) params.get(ParamsConst.TASK_SELECTED);
		if (tareas!=null){
			Iterator<Integer> itTareas = tareas.iterator();
			while (itTareas.hasNext()) {
				Integer actual = (Integer) itTareas.next();
				relation.addTarea(actual);	
			}
		}
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}
