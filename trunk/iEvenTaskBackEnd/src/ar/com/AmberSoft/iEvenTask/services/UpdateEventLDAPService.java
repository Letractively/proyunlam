package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes"})
public class UpdateEventLDAPService extends CreateEventLDAPService {

	@Override
	public Entity getEntity(Map params) {
		EventLDAP event = (EventLDAP) super.getEntity(params);
		event.setId((Integer) params.get(ParamsConst.ID));
		return event;
	}
	
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}


}
