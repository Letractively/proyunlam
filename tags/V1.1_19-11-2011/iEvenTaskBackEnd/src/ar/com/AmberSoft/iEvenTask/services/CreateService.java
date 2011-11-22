package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings("rawtypes")
public abstract class CreateService extends Service {

	@Override
	public Map onExecute(Map params)  throws Exception {
		Map map = new HashMap();
		Entity entity = getEntity(params);
		if (Boolean.TRUE.equals(params.get(ParamsConst.GET_ENTITY))){
			map.put(ParamsConst.ENTITY, entity);
		}
		getSession().save(entity);
		return map;
	}
	
	/**
	 * Retorna la entidad que sera almacenada
	 * @param params
	 * @return
	 */
	public abstract Entity getEntity (Map params)  throws Exception ; 


}
