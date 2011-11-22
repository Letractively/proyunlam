package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.utils.FiltersWrapperFactory;
import ar.com.AmberSoft.iEvenTask.utils.Wrapper;
import ar.com.AmberSoft.util.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseDateFilterConfig;
import com.extjs.gxt.ui.client.data.BaseFilterConfig;
import com.extjs.gxt.ui.client.data.BaseNumericFilterConfig;

public abstract class ListService extends Service {
	
	public static String SELECT_COUNT = "SELECT COUNT(*) ";
	public static String NAME = "name";
	public static String OFFSET = "offset";
	public static String LIMIT = "limit";
	
	protected Query query;
	protected Query queryCount;
	protected StringBuffer queryText;
	protected StringBuffer queryTextWithoutOrder;
	
	
	@Override
	public Map onExecute(Map params) {
		initList(params);
		
		queryText = new StringBuffer();
		queryText.append(FROM);
		queryText.append(getEntity());
		
		afterFrom(params);
		
		setFiltersInQueryText(params);

		queryTextWithoutOrder = new StringBuffer();
		queryTextWithoutOrder.append(queryText);
		setOrder(params);
		
		previousCreateQuery(params);
		
		query = getSession().createQuery(queryText.toString());
		queryCount = getSession().createQuery(SELECT_COUNT + queryTextWithoutOrder.toString());
		
		Boolean notCommonPaging = (Boolean)params.get(ParamsConst.NOT_COMMON_PAGING);
		if ((notCommonPaging==null) ||(!notCommonPaging)){
			if (params.get(OFFSET)!=null){
				query.setFirstResult((Integer) params.get(OFFSET));
			}
			if (params.get(LIMIT)!=null){
				query.setMaxResults((Integer) params.get(LIMIT));
			}
		}
		
		postCreateQuery(params);
		
		setFiltersValuesInQuery(params, query);
		setFiltersValuesInQuery(params, queryCount);

		Map map = new HashMap();
		map.put(ParamsConst.DATA, query.list());
		map.put(ParamsConst.TOTAL_COUNT, queryCount.uniqueResult());
		map.put(ParamsConst.OFFSET, (Integer) params.get(OFFSET));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		
		previousReturnMap(params, map);
		
		return map;
	}

	protected void afterFrom(Map params) {
	}


	public void setOrder(Map params) {
		String sortField = (String) params.get(SORT_FIELD); 
		Object sortDir = params.get(SORT_DIR);
		
		if ((sortField!=null) && (sortDir!=null)){
			try {
				String sortDirText = (String) MethodUtils.invokeExactMethod(sortDir, NAME, null);
				queryText.append(ORDER_BY);
				queryText.append(sortField);
				queryText.append(SPACE);
				queryText.append(sortDirText);
			} catch (Exception e) {
			}
		}
	}
	

	public void setFiltersValuesInQuery(Map params, Query query) {
		Collection filters = (Collection) params.get(FILTERS);
		if (filters!=null){
			int index = 0;
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				BaseFilterConfig filter = (BaseFilterConfig) it.next();
				Wrapper wrapper = FiltersWrapperFactory.getInstance().getWrapper(filter);
				if (wrapper!=null){
					wrapper.setValueOnQuery(query, index);
				} else {
					if (filter instanceof BaseDateFilterConfig) {
						query.setDate(index, (Date)filter.getValue());
					} else if (filter instanceof BaseNumericFilterConfig) {
						query.setDouble(index, (Double)filter.getValue());
					}
				}
				index++;
			}
		}
	}

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
				queryText.append(filter.getField());
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
			}
		}
	}

	/**
	 * Obligatorio redefinir para saber con que entidad se trabaja
	 * @return
	 */
	protected abstract String getEntity();
	
	/**
	 * Es posible redefinir para tomar alguna accion, antes de retornar el map como resultado del servicio
	 * @param params
	 * @param map
	 */
	protected void previousReturnMap(Map params, Map map) {}

	/**
	 * Es posible redefinir para tomar alguna accion despues de la creacion de la Query
	 * @param params
	 */
	protected void postCreateQuery(Map params) {}

	/**
	 * Es posible redefinir para tomar alguna accion antes de la creacion de la Query
	 * @param params
	 */
	protected void previousCreateQuery(Map params) {}

	/**
	 * Es posible redefinir para tomar alguna accion al comienzo de la ejecucion del servicio
	 * @param params
	 */
	protected void initList(Map params) {}


}
