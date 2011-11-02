package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.util.ParamsConst;

public class DivideTaskService extends Service {

	@Override
	public Map onExecute(Map params) {

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
			nueva.defaultVisibles();
			//FIXME: Revisar si es necesario setear algun otro valor adicional
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
