package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import com.extjs.gxt.ui.client.data.BaseFilterConfig;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.utils.FiltersWrapperFactory;
import ar.com.AmberSoft.iEvenTask.utils.Wrapper;
import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskWithVisibleFilterService extends ListTaskService {

	public static String SELECT_COUNT_DISTINCT = "SELECT COUNT ( DISTINCT T ) ";
	
	@Override
	protected void afterFrom(Map params) {
		super.afterFrom(params);
		queryText.append(" T left join T.visibles V");
	}

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

	@Override
	protected void postCreateQuery(Map params) {
		super.postCreateQuery(params);

		queryCount = getSession().createQuery(SELECT_COUNT_DISTINCT + queryTextWithoutOrder.toString());
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);
		
		query.setString("uservisible", user.getId());
		queryCount.setString("uservisible", user.getId());
	}
	
	@Override
	public void setFiltersInQueryText(Map params) {
		queryText.append(WHERE);
		queryText.append(Entity.DELETE_COLUMN);
		queryText.append(IS_NULL);
		Collection filters = (Collection) params.get(FILTERS);
		if (filters!=null){
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				queryText.append(AND);
				BaseFilterConfig filter = (BaseFilterConfig) it.next();
				queryText.append(" T." + filter.getField());
				queryText.append(operatorOnWhere.get(filter.getClass().getName()));
				queryText.append(QUESTION_SYMBOL);
			}
		}
	}
	
}
