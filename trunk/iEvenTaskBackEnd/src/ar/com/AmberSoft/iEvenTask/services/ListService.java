package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.utils.FiltersWrapperFactory;
import ar.com.AmberSoft.iEvenTask.utils.Wrapper;
import ar.com.AmberSoft.util.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseFilterConfig;

public abstract class ListService extends Service {
	
	public static String SELECT_COUNT = "SELECT COUNT(*) ";
	public static String NAME = "name";
	public static String OFFSET = "offset";
	public static String LIMIT = "limit";
	
	protected Query query;
	protected Query queryCount;
	protected StringBuffer queryText;
	
	
	@Override
	public Map onExecute(Map params) {
		initList(params);
		
		getSession().beginTransaction();
		queryText = new StringBuffer();
		queryText.append(FROM);
		queryText.append(getEntity());
		
		setFiltersInQueryText(params);

		StringBuffer queryTextWithoutOrder = new StringBuffer();
		queryTextWithoutOrder.append(queryText);
		setOrder(params);
		
		previousCreateQuery(params);
		
		query = getSession().createQuery(queryText.toString());
		queryCount = getSession().createQuery(SELECT_COUNT + queryTextWithoutOrder.toString());
		
		query.setFirstResult((Integer) params.get(OFFSET));
		query.setMaxResults((Integer) params.get(LIMIT));
		
		postCreateQuery(params);
		
		setFiltersValuesInQuery(params, query);
		setFiltersValuesInQuery(params, queryCount);
		
		Map map = new HashMap();
		Collection list = new ArrayList();
		map.put(ParamsConst.DATA, query.list());
		map.put(ParamsConst.TOTAL_COUNT, queryCount.uniqueResult());
		map.put(ParamsConst.OFFSET, (Integer) params.get(OFFSET));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		
		getSession().getTransaction().commit();
		
		previousReturnMap(params, map);
		
		return map;
	}


	private void setOrder(Map params) {
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
				wrapper.setValueOnQuery(query, index);
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
				queryText.append(operatorOnWhere.get(filter.getClass().getName()));
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
