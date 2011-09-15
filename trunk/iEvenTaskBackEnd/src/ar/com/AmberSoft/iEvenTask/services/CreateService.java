package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;

public abstract class CreateService extends Service {

	@Override
	public Map onExecute(Map params) {
		getSession().save(getEntity(params));
		return null;
	}
	
	/**
	 * Retorna la entidad que sera almacenada
	 * @param params
	 * @return
	 */
	public abstract Entity getEntity(Map params);


}
