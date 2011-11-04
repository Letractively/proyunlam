package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventLDAPService extends CreateService {

	@SuppressWarnings("rawtypes")
	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Entity getEntity(Map params) {
		EventLDAP event = new EventLDAP();
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity((Integer)params.get(ParamsConst.PERIODICITY));
		if (params.get(ParamsConst.EXPIRATION)!=null){
			event.setExpiration(new Date((Long)params.get(ParamsConst.EXPIRATION)));
		}
		event.setIterations((Integer)params.get(ParamsConst.ITERATIONS));
		event.setCode((String) params.get(ParamsConst.CODE));
		return event;
	}

}
