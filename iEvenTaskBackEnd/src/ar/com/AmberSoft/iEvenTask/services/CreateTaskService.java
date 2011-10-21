package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings("rawtypes")
public class CreateTaskService extends CreateService {

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		
		Tarea tarea = new Tarea();
		tarea.setNombreTarea((String)params.get(ParamsConst.NOMBRE_TAREA));
		tarea.setFechaComienzo((Date)params.get(ParamsConst.FECHA_COMIENZO));
		tarea.setFechaFin((Date)params.get(ParamsConst.FECHA_FIN));
		tarea.setDuracion((String)params.get(ParamsConst.DURACION));
		tarea.setDescripcion((String)params.get(ParamsConst.DESCRIPCION));
		tarea.setId_usuario((String) params.get(ParamsConst.ID_USUARIO));

		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);
		
		tarea.setCreator(user.getId());
		
		tarea.defaultVisibles();
		
		Collection usersView = (Collection) params.get(ParamsConst.USERS_VIEW);
		if (usersView!=null){
			Iterator<String> itUsers = usersView.iterator();
			while (itUsers.hasNext()) {
				String actual = (String) itUsers.next();
				tarea.addVisible(actual);	
			}
		}
		
//		tarea.setHorasAsignadas((Integer) params.get(ParamsConst.HORASASIGNADAS));
//		tarea.setFechaModificacion((Date) params.get(ParamsConst.FECHAMODIFICACION));
//		tarea.setEstado((Integer) params.get(ParamsConst.ESTADO));
//		tarea.setCumplimiento((Integer) params.get(ParamsConst.CUMPLIMIENTO));
//		tarea.setTipo_tarea((Integer) params.get(ParamsConst.TIPO_TAREA));
		
		//private Set<Comentario> comentarios;
		return tarea;
	}
}