package ar.com.AmberSoft.iEvenTask.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.backend.entities.VisibleObjetivo;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.ParamsConst;

public class ListObjectiveService extends ListService {

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Map execute(Map params) {

		Map result = super.execute(params);
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);
		
		try {
			Map<String, User> users = LDAPUtils.getUsersMap(user.getId(), user.getPassword());
		
			Collection<Objetivo> objetivos = (Collection<Objetivo>) result.get(ParamsConst.DATA);
			Iterator<Objetivo> it = objetivos.iterator();
			while (it.hasNext()) {
				Objetivo objetivo = (Objetivo) it.next();
				User actualUser = users.get(objetivo.getIdUsuarioAsignado());
				if (actualUser!=null){
					objetivo.setAsignado(actualUser.getName());	
				}
				
				Collection <VisibleObjetivo> visibles = objetivo.getVisibles();
				Iterator<VisibleObjetivo> itVisibles = visibles.iterator();
				Set<VisibleObjetivo> nuevosVisibles = new HashSet<VisibleObjetivo>();
				while (itVisibles.hasNext()) {
					VisibleObjetivo visible = (VisibleObjetivo) itVisibles.next();
					visible.setObjetivo(null);
					nuevosVisibles.add(visible);
				}
				objetivo.setVisibles(nuevosVisibles);
				
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Map onExecute(Map params) {
		Map result = super.onExecute(params);
		
		// Esto es necesario realizar para que estas colecciones no den Lazy initialization exception
		Collection<Objetivo> objetivos = (Collection<Objetivo>) result.get(ParamsConst.DATA);
		Iterator<Objetivo> it = objetivos.iterator();
		while (it.hasNext()) {
			Objetivo objetivo = (Objetivo) it.next();
			preventLazy(objetivo.getVisibles());
		}
		return result;
	}


	@SuppressWarnings("rawtypes")
	public void preventLazy(Collection collection) {
		if (collection!=null){
			collection.iterator();
		}
	}
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
