package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.Visible;
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
		
		deleteOldVisible((Integer) params.get(ParamsConst.ID));
		
		GetTaskService getTaskService = new GetTaskService();
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		params.putAll(getTaskService.execute(params));
		
		Tarea tarea = (Tarea) getEntity(params);

		getSession().saveOrUpdate(tarea);
		return null;
	}
	public void deleteOldVisible(Integer id) {
		StringBuffer queryText = new StringBuffer();
		queryText.append("DELETE ");
		queryText.append(Visible.class.getName());
		queryText.append(" WHERE  tarea.id = ?");
		
		Query query = getSession().createQuery(queryText.toString());
		query.setInteger(0, id);
		
		query.executeUpdate();
	}

}
