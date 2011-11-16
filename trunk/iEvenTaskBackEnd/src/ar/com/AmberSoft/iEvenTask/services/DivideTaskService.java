package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.Visible;
import ar.com.AmberSoft.util.ParamsConst;
import ar.com.AmberSoft.util.StatusConst;

public class DivideTaskService extends Service {

	@Override
	public Map onExecute(Map params)  throws Exception {

		Integer cantidad = (Integer) params.get(ParamsConst.CANTIDAD);
		Integer id = (Integer) params.get(ParamsConst.ID);

		GetTaskService getTaskService = new GetTaskService();
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);

		Map result = getTaskService.execute(params);
		Tarea tarea = (Tarea) result.get(ParamsConst.ENTITY);
		
		for (int i = 1; i<=cantidad; i++){
			Tarea nueva = new Tarea();
			nueva.setNombreTarea(tarea.getNombreTarea() + " - Parte " + i);
			nueva.setId_usuario(tarea.getId_usuario());
			nueva.setAsignado(tarea.getAsignado());
			nueva.setTareaPadre(tarea);
			nueva.setFechaComienzo(tarea.getFechaComienzo());
			nueva.setFechaFin(tarea.getFechaFin());
			nueva.setFechaCreacion(new Date());
			nueva.setEstado(StatusConst.PENDIENTE);
			
			Set<Visible> visibles =  tarea.getVisibles();
			if (visibles!=null){
				Iterator<Visible> itVisible = visibles.iterator();
				while (itVisible.hasNext()) {
					Visible visible = (Visible) itVisible.next();
					nueva.addVisible(visible.getUsuario());
				}
			}
			
			if (tarea.getPeso()!=null){
				nueva.setPeso(tarea.getPeso()/cantidad);
			} else {
				nueva.setPeso(0);
			}
			
			nueva.defaultVisibles();

			getSession().save(nueva);
		}

		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
