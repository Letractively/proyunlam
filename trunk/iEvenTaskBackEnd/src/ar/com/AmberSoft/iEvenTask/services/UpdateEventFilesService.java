package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes"})
public class UpdateEventFilesService extends CreateEventFilesService {

	@Override
	public Entity getEntity(Map params) {
		EventFiles event = (EventFiles) super.getEntity(params);
		event.setId((Integer) params.get(ParamsConst.ID));
		return event;
	}
	
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

}
