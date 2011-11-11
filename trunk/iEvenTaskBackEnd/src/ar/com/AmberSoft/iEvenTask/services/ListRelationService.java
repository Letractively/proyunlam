package ar.com.AmberSoft.iEvenTask.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithModifyStateTask;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.backend.entities.VisibleRelation;
import ar.com.AmberSoft.iEvenTask.utils.AppAdmin;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ListRelationService extends ListService {

	@Override
	public Map execute(Map params) throws Exception  {

		Map result = super.execute(params);

		HttpServletRequest request = (HttpServletRequest) params
				.get(ParamsConst.REQUEST);
		User user = null;
		if (request != null) {
			user = (User) request.getSession().getAttribute(ParamsConst.USER);
		}

		try {

			Map<String, User> users = null;
			if (user != null) {
				if (!AppAdmin.getInstance().getConfig().isEmulate()){
					users = LDAPUtils.getUsersMap(user.getId(), user.getPassword());
				}
			}

			Collection<Relation> relaciones = (Collection<Relation>) result.get(ParamsConst.DATA);
			Iterator<Relation> itRelaciones = relaciones.iterator();
			while (itRelaciones.hasNext()) {
				Relation relation = (Relation) itRelaciones.next();
				if (relation instanceof RelationWithActionCreateTask) {
					RelationWithActionCreateTask create = (RelationWithActionCreateTask) relation;
					create.setTarea(null);
				}
				if (relation instanceof RelationWithModifyStateTask) {
					RelationWithModifyStateTask modify = (RelationWithModifyStateTask) relation;
					Collection tareas = modify.getTareas();
					if (tareas!=null){
						Iterator<Tarea> itTareas = tareas.iterator();
						while (itTareas.hasNext()) {
							Tarea tarea = (Tarea) itTareas.next();
							//tarea.set
						}
					}
					//modify.setTareas(null);
				}
				Collection visibles = relation.getVisibles();
				if (visibles!=null){
					Iterator<VisibleRelation> itVisibles = visibles.iterator();
					while (itVisibles.hasNext()) {
						VisibleRelation visibleRelation = (VisibleRelation) itVisibles.next();
						visibleRelation.setRelation(null);
					}
				}
				
			}
			
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return result;

	}
	
	protected void previousReturnMap(Map params, Map map) {
		Collection<Relation> relaciones = (Collection<Relation>) map.get(ParamsConst.DATA);
		Iterator<Relation> itRelaciones = relaciones.iterator();
		while (itRelaciones.hasNext()) {
			Relation relation = (Relation) itRelaciones.next();
			/*if (relation instanceof RelationWithActionCreateTask) {
				RelationWithActionCreateTask create = (RelationWithActionCreateTask) relation;
				create.setTarea(null);
			}*/
			if (relation instanceof RelationWithModifyStateTask) {
				RelationWithModifyStateTask modify = (RelationWithModifyStateTask) relation;
				Collection tareas = modify.getTareas();
				if (tareas!=null){
					Iterator<Tarea> itTareas = tareas.iterator();
					while (itTareas.hasNext()) {
						Tarea tarea = (Tarea) itTareas.next();
						//tarea.set
					}
				}
				//modify.setTareas(null);
			}
		}
	}
	
	
	@Override
	protected String getEntity() {
		return Relation.class.getName();
	}

	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		Collection list = new ArrayList();
		
		addRelationCreate(list);
		addRelationCreate(list);
		addRelationCreate(list);
		addRelationCreate(list);
		addRelationCreate(list);
		addRelationCreate(list);
		addRelationCreate(list);
		
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	protected void addRelationCreate(Collection list) {
		RelationWithActionCreateTask relation = new RelationWithActionCreateTask();
		relation.setName("Relacion" + relation.getId().toString());
		
		Event event;
		Random random = new Random();
		int intRandom = random.nextInt(4);
		switch (intRandom) {
		case 1:
			event = new EventLDAP();
			break;
		case 2:
			event = new EventFiles();
			break;
		case 3:
			event = new EventLogs();
			break;
		case 4:
			event = new EventServices();
			break;
		default:
			event = new EventLDAP();
			break;
		}
		relation.setEvent(event);
		list.add(relation);
	}
	
}
