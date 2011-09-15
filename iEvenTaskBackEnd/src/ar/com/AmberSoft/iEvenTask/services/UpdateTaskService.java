package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

public class UpdateTaskService extends CreateTaskService {

	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

}
