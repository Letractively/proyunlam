package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventLogsService extends Service {

	protected void setAttributes(Map params, EventLogs event) {
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		//event.setExpiration(new Date());
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setPath((String) params.get(ParamsConst.PATH));
		event.setPatern((String) params.get(ParamsConst.PATERN));
	}

	@Override
	public Map onExecute(Map params) {
		EventLogs event = new EventLogs();
		setAttributes(params, event);
		
		Transaction transaction = getSession().beginTransaction();
		
		getSession().save(event);
		transaction.commit();

		return null;	
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}
