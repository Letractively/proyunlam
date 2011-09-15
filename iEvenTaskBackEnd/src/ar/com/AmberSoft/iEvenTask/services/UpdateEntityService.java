package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateEntityService extends Service {

	@Override
	public Map onExecute(Map params) {
		Entity entity = (Entity) params.get(ParamsConst.ENTITY);
		getSession().saveOrUpdate(entity);
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}
