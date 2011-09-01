package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventLDAPService extends Service {

	@Override
	public Map execute(Map params) {
		EventLDAP event = new EventLDAP();
		setAttributes(params, event);
		
		Transaction transaction = getSession().beginTransaction();
		
		getSession().save(event);
		transaction.commit();

		return null;
	}

	protected void setAttributes(Map params, EventLDAP event) {
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity((Integer) params.get(ParamsConst.PERIODICITY));
		event.setExpiration((Date) params.get(ParamsConst.EXPIRATION));
		event.setIterations( (Integer) params.get(ParamsConst.ITERATIONS));
		event.setCode((String) params.get(ParamsConst.CODE));
	}

}
