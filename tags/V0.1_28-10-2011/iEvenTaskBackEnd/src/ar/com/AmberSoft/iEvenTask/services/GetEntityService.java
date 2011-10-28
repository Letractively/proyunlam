package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.util.ParamsConst;

public abstract class GetEntityService extends Service {

	@Override
	public Map onExecute(Map params) {

		Object id = (Object) params.get(ParamsConst.ID);
		
		StringBuffer queryText = new StringBuffer();
		queryText.append(FROM);
		queryText.append(getEntity());
		queryText.append(WHERE);
		queryText.append(Entity.DELETE_COLUMN);
		queryText.append(IS_NULL);
		queryText.append(AND);
		queryText.append(ParamsConst.ID);
		queryText.append(EQUAL);
		queryText.append(id);
		
		Query query = getSession().createQuery(queryText.toString());
		
		Map map = new HashMap();
		map.put(ParamsConst.ENTITY, query.uniqueResult());
		return map;
	}

	public abstract String getEntity();

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
