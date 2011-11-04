package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings("rawtypes")
public class CreateEventServicesService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		EventServices event = new EventServices();
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity((Integer)params.get(ParamsConst.PERIODICITY));
		if (params.get(ParamsConst.EXPIRATION)!=null){
			event.setExpiration(new Date((Long)params.get(ParamsConst.EXPIRATION)));
		}
		event.setIterations((Integer)params.get(ParamsConst.ITERATIONS));
		event.setHost((String) params.get(ParamsConst.HOST));
		event.setPort((String) params.get(ParamsConst.PORT));
		return event;
	}

}
