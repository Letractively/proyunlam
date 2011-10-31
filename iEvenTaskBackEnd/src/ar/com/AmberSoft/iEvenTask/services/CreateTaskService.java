package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings("rawtypes")
public class CreateTaskService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		
		Tarea tarea = null;
		if (params.get(ParamsConst.ENTITY)!=null){
			tarea = (Tarea) params.get(ParamsConst.ENTITY);
		} else {
			tarea = new Tarea();
		}
		
		tarea.setNombreTarea((String)params.get(ParamsConst.NOMBRE_TAREA));
		tarea.setFechaComienzo((Date)params.get(ParamsConst.FECHA_COMIENZO));
		tarea.setFechaFin((Date)params.get(ParamsConst.FECHA_FIN));
		//tarea.setDuracion((String)params.get(ParamsConst.DURACION));
		tarea.setEstado((String) params.get(ParamsConst.ESTADO));

		Object cumplimiento = params.get(ParamsConst.CUMPLIMIENTO);
		if (cumplimiento != null){
			if (cumplimiento instanceof Integer) {
				tarea.setCumplimiento((Integer) cumplimiento);			
			} else {
				tarea.setCumplimiento(new Integer((String)cumplimiento));
			}
		}
		tarea.setDescripcion((String)params.get(ParamsConst.DESCRIPCION));
		tarea.setId_usuario((String) params.get(ParamsConst.ID_USUARIO));

		String id_objetivo = (String) params.get(ParamsConst.ID_OBJETIVO);
		GetObjectiveService getObjectiveService = new GetObjectiveService();
		Map paramsObj = new HashMap();
		paramsObj.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		paramsObj.put(ParamsConst.ID, id_objetivo);
		Map result = getObjectiveService.execute(paramsObj);
		tarea.setObjetivo((Objetivo) result.get(ParamsConst.ENTITY));
		
		tarea.setPeso((Integer) params.get(ParamsConst.PESO));
		
		if (params.get(ParamsConst.ENTITY)==null){
			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			if (request != null){
				User user = (User) request.getSession().getAttribute(ParamsConst.USER);
				tarea.setCreator(user.getId());
			} else {
				tarea.setCreator("DETECT_PROCESS");
			}
			
		}
		
		setVisibles(params, tarea);
		
//		tarea.setHorasAsignadas((Integer) params.get(ParamsConst.HORASASIGNADAS));
//		tarea.setFechaModificacion((Date) params.get(ParamsConst.FECHAMODIFICACION));
//		tarea.setCumplimiento((Integer) params.get(ParamsConst.CUMPLIMIENTO));
//		tarea.setTipo_tarea((Integer) params.get(ParamsConst.TIPO_TAREA));
		
		//private Set<Comentario> comentarios;
		return tarea;
	}

	public void setVisibles(Map params, Tarea tarea) {
		tarea.defaultVisibles();
		
		Collection usersView = (Collection) params.get(ParamsConst.USERS_VIEW);
		if (usersView!=null){
			Iterator<String> itUsers = usersView.iterator();
			while (itUsers.hasNext()) {
				String actual = (String) itUsers.next();
				tarea.addVisible(actual);	
			}
		}
	}
}