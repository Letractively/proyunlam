package ar.com.AmberSoft.iEvenTask.services;

import java.sql.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateTaskService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		Tarea tarea = new Tarea();
		tarea.setFechaCreacion((Date) params.get(ParamsConst.FECHACREACION));
		tarea.setFechaFin((Date) params.get(ParamsConst.FECHAFIN));
		tarea.setHorasAsignadas((Integer) params.get(ParamsConst.HORASASIGNADAS));
		tarea.setId_usuario((String) params.get(ParamsConst.ID_USUARIO));
		tarea.setFechaModificacion((Date) params.get(ParamsConst.FECHAMODIFICACION));
		tarea.setEstado((Integer) params.get(ParamsConst.ESTADO));
		tarea.setCumplimiento((Integer) params.get(ParamsConst.CUMPLIMIENTO));
		tarea.setTipo_tarea((Integer) params.get(ParamsConst.TIPO_TAREA));
		
		//private Set<Comentario> comentarios;

		return null;
	}

}


