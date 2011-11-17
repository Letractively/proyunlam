package ar.com.AmberSoft.iEvenTask.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.AsignadoTareaComparatorASC;
import ar.com.AmberSoft.util.AsignadoTareaComparatorDESC;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseDateFilterConfig;
import com.extjs.gxt.ui.client.data.BaseFilterConfig;

public class ListTaskWithVisibleFilterService extends ListTaskService {

	private static Logger logger = Logger.getLogger(ListTaskWithVisibleFilterService.class);
	
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
		
		String sortField = (String) params.get(SORT_FIELD); 
		Object sortDir = params.get(SORT_DIR);
		
		if ((sortField!=null) && (sortDir!=null)){
			try {
				String sortDirText = (String) MethodUtils.invokeExactMethod(sortDir, NAME, null);
				if (!"asignado".equals(sortField)){
					queryText.append(ORDER_BY);
					queryText.append(" T." + sortField);
					queryText.append(SPACE);
					queryText.append(sortDirText);
				}	else {
					params.put(ParamsConst.NOT_COMMON_PAGING, Boolean.TRUE);
				}
			} catch (Exception e) {
			}

		}
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
				if (!"asignado".equals(filter.getField())){
					queryText.append(" T." + filter.getField());
					String operator = (String)operatorOnWhere.get(filter.getClass().getName());
					if (operator !=null){
						queryText.append(operator);
					} else {
						if (filter instanceof BaseDateFilterConfig) {
							BaseDateFilterConfig dateFilter = (BaseDateFilterConfig) filter;
							if ("before".equals(dateFilter.getComparison())){
								queryText.append(" < ");
							} else if ("after".equals(dateFilter.getComparison())){
								queryText.append(" > ");
							} else {
								queryText.append(EQUAL);		
							}
						} else {
							queryText.append(EQUAL);
						}
						
					}
					queryText.append(QUESTION_SYMBOL);
				} else {

					HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
					User user = null;
					if (request != null) {
						user = (User) request.getSession().getAttribute(ParamsConst.USER);
					}
					Map<String, User> users = null;
					if (user != null) {
						try {
							users = LDAPUtils.getUsersMap(user.getId(), user.getPassword());
						} catch (Exception e) {
							logger.error(Tools.getStackTrace(e));
						}
						if ((users!=null)&&(users.size()>0)){
							queryText.append(" T.id_usuario");
							queryText.append(" IN ( ");
							
							Collection values = users.values();
							Iterator itValues = values.iterator();
							queryText.append("''");
							while (itValues.hasNext()) {
								User actual = (User) itValues.next();
								if (actual.getName().indexOf((String)filter.getValue())>=0){
									queryText.append(",'"+actual.getId()+"'");
								}
							}
							
							queryText.append(" )");
						}
					}
				}
			}
		}
	}
	
	public void setFiltersValuesInQuery(Map params, Query query) {
		Collection filters = (Collection) params.get(FILTERS);
		BaseFilterConfig filter = null;
		Boolean encontrado = Boolean.FALSE;
		if (filters!=null){
			int index = 0;
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				filter = (BaseFilterConfig) it.next();
				if ("asignado".equals(filter.getField())){
					encontrado=!encontrado;
					break;
				}
			}
		}
		if ((filter!=null)&&(encontrado)){
			filters.remove(filter);
		}
		params.put(FILTERS, filters);
		super.setFiltersValuesInQuery(params, query);
	}
	
	
	@Override
	public void setOrder(Map params) {
	}
	
	
}
