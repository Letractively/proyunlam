package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings("rawtypes")
public class CreateEventLogsService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		EventLogs event = new EventLogs();
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity((Integer)params.get(ParamsConst.PERIODICITY));
		if (params.get(ParamsConst.EXPIRATION)!=null){
			event.setExpiration(new Date((Long)params.get(ParamsConst.EXPIRATION)));
		}
		event.setIterations((Integer)params.get(ParamsConst.ITERATIONS));
		event.setPath((String) params.get(ParamsConst.PATH));
		event.setPatern((String) params.get(ParamsConst.PATERN));
		return event;
	}

}
