package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskByTaskService extends GetTaskService {

	private static Logger logger = Logger.getLogger(ListTaskByTaskService.class);
	
	@Override
	public Map execute(Map params)  throws Exception {

		Map result = super.execute(params);
		
		Tarea tarea = (Tarea) result.get(ParamsConst.ENTITY);

		Set<Tarea> nuevasSubTareas = new HashSet<Tarea>();
		Set subtareas = tarea.getSubtareas();
		if (subtareas!=null){
			Iterator<Tarea> itTareas = subtareas.iterator();
			
			while (itTareas.hasNext()) {
				Tarea subTarea = (Tarea) itTareas.next();
				subTarea.setSubtareas(null);
				subTarea.setTareaPadre(null);
				if (subTarea.getDelete()==null){
					nuevasSubTareas.add(subTarea);
				}
			}
		}
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, nuevasSubTareas);
		map.put(ParamsConst.TOTAL_COUNT, nuevasSubTareas.size());
		//map.put(ParamsConst.OFFSET, (Integer) params.get(OFFSET));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		
		return map;
	}		
	
	
	@Override
	public Map onExecute(Map params) {
		Map result = super.onExecute(params);
		Tarea tarea = (Tarea) result.get(ParamsConst.ENTITY);
		
		preventLazy(tarea.getSubtareas());
		
		result.put(ParamsConst.ENTITY, tarea);
		
		return result;
	}
	
	public void preventLazy(Collection collection) {
		if (collection != null) {
			collection.iterator();
		}
	}

	
	@Override
	public Map onEmulate(Map params) {
		ListTaskService listTask = new ListTaskService();
		try {
			return listTask.execute(new HashMap());
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
		return null;
	}


}
