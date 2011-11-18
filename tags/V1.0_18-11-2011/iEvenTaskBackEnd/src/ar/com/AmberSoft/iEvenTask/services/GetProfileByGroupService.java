package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.util.ParamsConst;

public class GetProfileByGroupService extends GetProfileService {
	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		map.put(ParamsConst.ENTITY, new Profile());
		return map;
	}

	@Override
	public Map onExecute(Map params) {

		String group = (String) params.get(ParamsConst.GROUP);
		
		StringBuffer queryText = new StringBuffer();
		queryText.append(FROM);
		queryText.append(getEntity());
		queryText.append(WHERE);
		queryText.append(Entity.DELETE_COLUMN);
		queryText.append(IS_NULL);
		queryText.append(AND);
		queryText.append(" groupLDAP ");
		queryText.append(EQUAL);
		queryText.append(QUESTION_SYMBOL);
		
		Query query = getSession().createQuery(queryText.toString());
		query.setString(0, group);
		
		Map map = new HashMap();
		map.put(ParamsConst.ENTITY, query.uniqueResult());
		return map;
	}
}
