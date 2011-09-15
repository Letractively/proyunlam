package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventLDAPService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		EventLDAP event = new EventLDAP();
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		//event.setExpiration(new Date());
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setCode((String) params.get(ParamsConst.CODE));
		return event;
	}

}
