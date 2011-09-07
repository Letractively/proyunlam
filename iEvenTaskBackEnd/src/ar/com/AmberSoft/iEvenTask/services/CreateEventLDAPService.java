package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventLDAPService extends Service {

	protected void setAttributes(Map params, EventLDAP event) {
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		//event.setExpiration(new Date());
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setCode((String) params.get(ParamsConst.CODE));
	}

	@Override
	public Map onExecute(Map params) {
		EventLDAP event = new EventLDAP();
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
