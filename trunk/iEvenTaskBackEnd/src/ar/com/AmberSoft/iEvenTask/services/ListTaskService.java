package ar.com.AmberSoft.iEvenTask.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Comentario;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.backend.entities.Visible;
import ar.com.AmberSoft.iEvenTask.utils.AppAdmin;
import ar.com.AmberSoft.util.LDAPUtils;
import ar.com.AmberSoft.util.PKGenerator;
import ar.com.AmberSoft.util.ParamsConst;

public class ListTaskService extends ListService {

	private static Logger logger = Logger.getLogger(ListTaskService.class);

	@Override
	public Map execute(Map params) {

		Map result = super.execute(params);

		HttpServletRequest request = (HttpServletRequest) params
				.get(ParamsConst.REQUEST);
		User user = null;
		if (request != null) {
			user = (User) request.getSession().getAttribute(ParamsConst.USER);
		}

		try {

			Map<String, User> users = null;
			if (user != null) {
				if (!AppAdmin.getInstance().getConfig().isEmulate()){
					users = LDAPUtils.getUsersMap(user.getId(), user.getPassword());
				}
			}

			Collection<Tarea> tareas = (Collection<Tarea>) result
					.get(ParamsConst.DATA);
			Iterator<Tarea> it = tareas.iterator();
			while (it.hasNext()) {
				Tarea tarea = (Tarea) it.next();
				if (users != null) {
					User actualUser = users.get(tarea.getId_usuario());
					if (actualUser != null) {
						tarea.setAsignado(actualUser.getName());
					}
				}

				Collection<Comentario> comentarios = tarea.getComentarios();
				if (comentarios!=null){
					Iterator<Comentario> itComentarios = comentarios.iterator();
					Set<Comentario> nuevosComentarios = new HashSet<Comentario>();
					while (itComentarios.hasNext()) {
						Comentario comentario = (Comentario) itComentarios.next();
						comentario.setTarea(null);
						nuevosComentarios.add(comentario);
					}
					tarea.setComentarios(nuevosComentarios);
				}
				
				Collection<Visible> visibles = tarea.getVisibles();
				if (visibles!=null){
					Iterator<Visible> itVisibles = visibles.iterator();
					Set<Visible> nuevosVisibles = new HashSet<Visible>();
					while (itVisibles.hasNext()) {
						Visible visible = (Visible) itVisibles.next();
						visible.setTarea(null);
						nuevosVisibles.add(visible);
					}
					tarea.setVisibles(nuevosVisibles);
				}
				
				//Este seteo previene un ciclo infinito en la adaptacion de tipos, previo a la vista
				//tarea.setSubtareas(null);
				//FIXME: No necesario lo vamos a resolver en otro servicio
				Collection<Tarea> subtareas = tarea.getSubtareas();
				if (subtareas!=null){
					Iterator<Tarea> itTareas = subtareas.iterator();
					Set<Tarea> nuevasSubTareas = new HashSet<Tarea>();
					while (itTareas.hasNext()) {
						Tarea subTarea = (Tarea) itTareas.next();
						subTarea.setSubtareas(null);
						subTarea.setTareaPadre(null);
						nuevasSubTareas.add(subTarea);
					}
					tarea.setSubtareas(nuevasSubTareas);
				}
				

			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return result;

	}

	@Override
	public Map onExecute(Map params) {
		Map result = super.onExecute(params);

		// Esto es necesario realizar para que estas colecciones no den Lazy
		// initialization exception

		Collection<Tarea> tareas = (Collection<Tarea>) result
				.get(ParamsConst.DATA);
		Iterator<Tarea> it = tareas.iterator();
		while (it.hasNext()) {
			Tarea tarea = (Tarea) it.next();
			preventLazy(tarea.getComentarios());
			preventLazy(tarea.getVisibles());
			//FIXME: No necesario se resuelve en otro servicio
			preventLazy(tarea.getSubtareas());
		}

		return result;
	}

	public void preventLazy(Collection collection) {
		if (collection != null) {
			collection.iterator();
		}
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

	public static void addTarea(Collection<Tarea> list) {
		PKGenerator generator = new PKGenerator();
		Tarea tarea = new Tarea();
		tarea.setId(generator.getIntLastTime());
		tarea.setNombreTarea("Tarea" + generator.getPk());
		tarea.setFechaComienzo(new Date());
		tarea.setFechaFin(new Date());
		//tarea.setDuracion(String.valueOf(generator.getLastTime()));
		tarea.setDescripcion("Descripcion" + generator.getPk());
		tarea.setId_usuario(generator.getPk());

		Comentario comentario = new Comentario();
		comentario.setComentario("Comentarios" + generator.getPk());
		comentario.setFecha(new Date());
		comentario.setUsuario("USERTEST");
		Set comentarios = new HashSet();
		comentarios.add(comentario);
		tarea.setComentarios(comentarios);

		Set<Visible> visibles = new HashSet<Visible>();
		setVisible(tarea, visibles, "1");
		setVisible(tarea, visibles, "3");
		setVisible(tarea, visibles, "5");
		tarea.setVisibles(visibles);
		
		Tarea tarea2 = new Tarea();
		tarea2.setId(generator.getIntLastTime());
		tarea2.setNombreTarea("Tarea" + generator.getPk());
		tarea2.setFechaComienzo(new Date());
		tarea2.setFechaFin(new Date());
		tarea2.setDescripcion("Descripcion" + generator.getPk());
		tarea2.setId_usuario(generator.getPk());
		Set subtareas = new HashSet();
		subtareas.add(tarea2);
		tarea.setSubtareas(subtareas);
				
		list.add(tarea);
	}

	public static void setVisible(Tarea tarea, Set<Visible> visibles, String id) {
		Visible visible = new Visible(tarea, id);
		visibles.add(visible);

	}
}
