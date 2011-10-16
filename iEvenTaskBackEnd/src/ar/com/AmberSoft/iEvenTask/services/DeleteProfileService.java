package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.util.ParamsConst;

public class DeleteProfileService extends DeleteService {

	@SuppressWarnings("rawtypes")
	@Override
	public Map onExecute(Map params) {
		Collection ids = (Collection) params.get(ParamsConst.IDS);
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			Entity entity = (Entity) getSession().get(getEntity(), id);
			//entity.setDelete(new Date());
			getSession().delete(entity);
		}
		
		return null;		
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Profile.class;
	}

}
