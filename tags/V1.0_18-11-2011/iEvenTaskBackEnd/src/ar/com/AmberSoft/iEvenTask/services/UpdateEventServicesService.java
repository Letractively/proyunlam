package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes"})
public class UpdateEventServicesService extends CreateEventServicesService {
	
	@Override
	public Entity getEntity(Map params) {
		EventServices event = (EventServices) super.getEntity(params);
		event.setId((Integer) params.get(ParamsConst.ID));
		return event;
	}
	
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

	
}
