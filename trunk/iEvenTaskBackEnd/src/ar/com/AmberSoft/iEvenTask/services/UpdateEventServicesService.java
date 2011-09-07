package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateEventServicesService extends CreateEventServicesService {
	@Override
	public Map onExecute(Map params) {
		EventServices event = new EventServices();
		event.setId((Integer) params.get(ParamsConst.ID));

		setAttributes(params, event);
		
		Transaction transaction = getSession().beginTransaction();
		getSession().saveOrUpdate(event);
		transaction.commit();
		
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}
}
