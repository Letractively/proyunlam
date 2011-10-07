package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventFilesService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		EventFiles event = new EventFiles();
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		if (params.get(ParamsConst.EXPIRATION)!=null){
			event.setExpiration(new Date((Long)params.get(ParamsConst.EXPIRATION)));
		}
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setPath((String) params.get(ParamsConst.PATH));
		event.setControlType(new Integer((String) params.get(ParamsConst.CONTROL_TYPE)));
		return event;
	}

}
