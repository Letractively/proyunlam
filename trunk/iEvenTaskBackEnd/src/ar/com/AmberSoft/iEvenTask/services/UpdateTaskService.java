package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateTaskService extends CreateTaskService {

	@SuppressWarnings("rawtypes")
	@Override
	public Entity getEntity(Map params) {
		Tarea tarea = (Tarea) super.getEntity(params);
		tarea.setId((Integer) params.get(ParamsConst.ID));
		return tarea;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

}
