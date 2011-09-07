package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateEventFilesService extends Service {

	protected void setAttributes(Map params, EventFiles event) {
		event.setName((String) params.get(ParamsConst.NAME));
		event.setPeriodicity(Service.stringToInteger((String)params.get(ParamsConst.PERIODICITY)));
		//event.setExpiration(new Date());
		event.setIterations(Service.stringToInteger((String)params.get(ParamsConst.ITERATIONS)));
		event.setPath((String) params.get(ParamsConst.PATH));
		event.setControlType(new Integer((String) params.get(ParamsConst.CONTROL_TYPE)));
	}

	@Override
	public Map onExecute(Map params) {
		EventFiles event = new EventFiles();
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
