package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskByObjectiveService extends ListTaskService {
	
	@Override
	protected void afterFrom(Map params) {
		super.afterFrom(params);
	}

	@Override
	protected void previousCreateQuery(Map params) {
		super.previousCreateQuery(params);
		StringBuffer userQuery = new StringBuffer();
		userQuery.append(AND);
		userQuery.append(" objetivo.id ");
		userQuery.append(EQUAL);
		userQuery.append(" :idObjective ");
		
		queryText.append(userQuery);
		queryTextWithoutOrder.append(userQuery);
	}

	@Override
	protected void postCreateQuery(Map params) {
		super.postCreateQuery(params);
		
		query.setInteger("idObjective", (Integer) params.get(ParamsConst.ID_OBJETIVO));
		queryCount.setInteger("idObjective", (Integer) params.get(ParamsConst.ID_OBJETIVO));
	}
}
