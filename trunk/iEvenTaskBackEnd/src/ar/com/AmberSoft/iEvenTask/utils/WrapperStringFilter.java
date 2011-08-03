package ar.com.AmberSoft.iEvenTask.utils;

import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.services.Service;

public class WrapperStringFilter extends Wrapper {


	@Override
	public void setValueOnQuery(Query query, int index) {
		query.setString(index, Service.PORCENT + filter.getValue() + Service.PORCENT);
	}

	@Override
	public void setValueOnQuery(Query query, String index) {
		query.setString(index, Service.PORCENT + filter.getValue() + Service.PORCENT);
	}
	
	
	
	
}
