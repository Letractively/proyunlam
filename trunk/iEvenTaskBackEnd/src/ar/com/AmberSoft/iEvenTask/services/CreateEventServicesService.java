package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventServicesService extends Service {

	protected void setAttributes(Map params, EventServices event) {
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		//event.setExpiration(new Date());
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setHost((String) params.get(ParamsConst.HOST));
		event.setPort((String) params.get(ParamsConst.PORT));
	}

	@Override
	public Map onExecute(Map params) {
		EventServices event = new EventServices();
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
