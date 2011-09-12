package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class ListRelationService extends ListService {

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
