package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskByObjectiveService extends ListTaskService {

	//public static String SELECT_COUNT_DISTINCT = "SELECT COUNT ( DISTINCT T ) ";
	
	@Override
	protected void afterFrom(Map params) {
		super.afterFrom(params);
		//queryText.append(" T left join T.visibles V");
	}

	@Override
	protected void previousCreateQuery(Map params) {
		super.previousCreateQuery(params);
		StringBuffer userQuery = new StringBuffer();
		userQuery.append(AND);
		userQuery.append(" objetivo.id ");
		userQuery.append(EQUAL);
		userQuery.append(" :idObjective ");
		
		//StringBuffer tmpBuffer = new StringBuffer(queryText);
		//queryText = new StringBuffer();
		//queryText.append("Select distinct T ");
		//queryText.append(tmpBuffer);
		queryText.append(userQuery);
		queryTextWithoutOrder.append(userQuery);
	}

	@Override
	protected void postCreateQuery(Map params) {
		super.postCreateQuery(params);

		//queryCount = getSession().createQuery(SELECT_COUNT_DISTINCT + queryTextWithoutOrder.toString());
		
		query.setInteger("idObjective", (Integer) params.get(ParamsConst.ID_OBJETIVO));
		queryCount.setInteger("idObjective", (Integer) params.get(ParamsConst.ID_OBJETIVO));
	}

	
	
}
