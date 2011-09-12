package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateRelationCreateTaskService extends
		CreateRelationCreateTaskService {
	@Override
	public Map onExecute(Map params) {
		RelationWithActionCreateTask relation = new RelationWithActionCreateTask();
		relation.setId((Integer) params.get(ParamsConst.ID));

		setAttributes(params, relation);
		
		Transaction transaction = getSession().beginTransaction();
		getSession().saveOrUpdate(relation);
		transaction.commit();
		
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}
}
