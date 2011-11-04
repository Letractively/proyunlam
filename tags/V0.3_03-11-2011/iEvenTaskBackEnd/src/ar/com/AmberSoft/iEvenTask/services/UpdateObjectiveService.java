package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateObjectiveService extends CreateObjectiveService {
	@SuppressWarnings("rawtypes")
	@Override
	public Entity getEntity(Map params) {
		Objetivo objetivo = (Objetivo) super.getEntity(params);
		objetivo.setId((Integer) params.get(ParamsConst.ID));
		return objetivo;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}
}
