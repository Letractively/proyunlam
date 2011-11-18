package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithModifyStateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateRelationModifyStateService extends
		CreateRelationModifyStateService {
	@Override
	public Entity getEntity(Map params)  throws Exception {
		RelationWithModifyStateTask relation = (RelationWithModifyStateTask) super.getEntity(params);
		relation.setId((Integer) params.get(ParamsConst.ID));
		return relation;
	}
	
	@Override
	public Map onExecute(Map params)  throws Exception {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}
}
