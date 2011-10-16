package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.util.ParamsConst;

public class ListObjectiveService extends ListService {

	@Override
	protected String getEntity() {
		return Objetivo.class.getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map onEmulate(Map params) {
		Map<String, Object> map = new HashMap<String, Object>();
		Collection<Objetivo> list = new ArrayList<Objetivo>();
		addObjetivo(list);
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return null;
	}
	
	private void addObjetivo(Collection<Objetivo> list) {
		Objetivo objetivo = new Objetivo();
		objetivo.setNombreObjetivo(objetivo.getNombreObjetivo());
		objetivo.setTipoObjetivo(objetivo.getTipoObjetivo());
		objetivo.setEscalaMedicion(objetivo.getEscalaMedicion());
		objetivo.setFechaFinalizacion(objetivo.getFechaFinalizacion());
		objetivo.setPonderacion(objetivo.getPonderacion());
		objetivo.setIdUsuarioAsignado(objetivo.getIdUsuarioAsignado());
		objetivo.setDescripcion(objetivo.getDescripcion());
		list.add(objetivo);
	}

}
