package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes"})
public class UpdateRelationCreateTaskService extends
		CreateRelationCreateTaskService {
	
	@Override
	public Entity getEntity(Map params)  throws Exception {
		RelationWithActionCreateTask relation = (RelationWithActionCreateTask) super.getEntity(params);
		relation.setId((Integer) params.get(ParamsConst.ID));
		return relation;
	}
	
	@Override
	public Map onExecute(Map params)  throws Exception {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

}
