package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.AmberSoft.iEvenTask.backend.entities.Comentario;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.util.PKGenerator;
import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskService extends ListService {

	@Override
	public Map execute(Map params) {
		Map result = super.execute(params);
		
		Collection<Tarea> tareas = (Collection<Tarea>) result.get(ParamsConst.DATA);
		Iterator<Tarea> it = tareas.iterator();
		while (it.hasNext()) {
			Tarea tarea = (Tarea) it.next();
			Collection<Comentario> comentarios = tarea.getComentarios();
			Iterator<Comentario> itComentarios = comentarios.iterator();
			Set<Comentario> nuevosComentarios = new HashSet<Comentario>();
			while (itComentarios.hasNext()) {
				Comentario comentario = (Comentario) itComentarios.next();
				comentario.setTarea(null);
				nuevosComentarios.add(comentario);
			}
			tarea.setComentarios(nuevosComentarios);
		}
		
		
		return result;

	}


	@Override
	public Map onExecute(Map params) {
		Map result = super.onExecute(params);
		
		Collection<Tarea> tareas = (Collection<Tarea>) result.get(ParamsConst.DATA);
		Iterator<Tarea> it = tareas.iterator();
		while (it.hasNext()) {
			Tarea tarea = (Tarea) it.next();
			Collection<Comentario> comentarios = tarea.getComentarios();
			Iterator<Comentario> itComentarios = comentarios.iterator();
			Set<Comentario> nuevosComentarios = new HashSet<Comentario>();
			while (itComentarios.hasNext()) {
				Comentario comentario = (Comentario) itComentarios.next();
				nuevosComentarios.add(comentario);
			}
			tarea.setComentarios(nuevosComentarios);
		}
		
		
		return result;
	}


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
		addTarea(list);
		addTarea(list);
		addTarea(list);
		addTarea(list);
		addTarea(list);
		addTarea(list);
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}
	
	private void addTarea(Collection<Tarea> list) {
		PKGenerator generator = new PKGenerator();
		Tarea tarea = new Tarea();
		tarea.setId(generator.getIntLastTime());
		tarea.setNombreTarea("Tarea" + generator.getPk());
		tarea.setFechaComienzo(new Date());
		tarea.setFechaFin(new Date());
		tarea.setDuracion(String.valueOf(generator.getLastTime()));
		tarea.setDescripcion("Descripcion" + generator.getPk());
		tarea.setId_usuario(generator.getPk());
		
		Comentario comentario = new Comentario();
		comentario.setComentario("Comentarios"  + generator.getPk());
		comentario.setFecha(new Date());
		comentario.setUsuario("USERTEST");
		Set comentarios = new HashSet();
		comentarios.add(comentario);
		tarea.setComentarios(comentarios);
		
		list.add(tarea);
	}
}
