package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskService extends ListService {

	@Override
	protected String getEntity() {
		return Tarea.class.getName();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map onEmulate(Map params) {
		Map<String, Object> map = new HashMap();
		Collection<Tarea> list = new ArrayList<Tarea>();
		addTarea(list);
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return null;
	}
	
	private void addTarea(Collection<Tarea> list) {
		Tarea tarea = new Tarea();
		tarea.setNombreTarea(tarea.getNombreTarea());
		tarea.setFechaComienzo(tarea.getFechaComienzo());
		tarea.setFechaFin(tarea.getFechaFin());
		tarea.setDuracion(tarea.getDuracion());
		tarea.setDescripcion(tarea.getDescripcion());
		tarea.setId_usuario(tarea.getId_usuario());
		list.add(tarea);
	}
}
