package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;

public class ListObjectiveWithVisibleFilterService extends ListObjectiveService {
	
	public static String SELECT_COUNT_DISTINCT = "SELECT COUNT ( DISTINCT T ) ";
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void afterFrom(Map params) {
		super.afterFrom(params);
		queryText.append(" T left join T.visibles V");
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void previousCreateQuery(Map params) {
		super.previousCreateQuery(params);
		StringBuffer userQuery = new StringBuffer();
		userQuery.append(AND);
		userQuery.append(" V.usuario ");
		userQuery.append(EQUAL);
		userQuery.append(" :uservisible ");
		
		StringBuffer tmpBuffer = new StringBuffer(queryText);
		queryText = new StringBuffer();
		queryText.append("Select distinct T ");
		queryText.append(tmpBuffer);
		queryText.append(userQuery);
		queryTextWithoutOrder.append(userQuery);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void postCreateQuery(Map params) {
		super.postCreateQuery(params);

		queryCount = getSession().createQuery(SELECT_COUNT_DISTINCT + queryTextWithoutOrder.toString());
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);
		
		query.setString("uservisible", user.getId());
		queryCount.setString("uservisible", user.getId());
	}
}
