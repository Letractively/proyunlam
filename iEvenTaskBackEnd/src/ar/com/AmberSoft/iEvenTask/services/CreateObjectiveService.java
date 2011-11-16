package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateObjectiveService extends CreateService {

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Entity getEntity(Map params) {
		
		Objetivo objetivo = null;
		if (params.get(ParamsConst.ENTITY)!=null){
			objetivo = (Objetivo) params.get(ParamsConst.ENTITY);
		} else {
			objetivo = new Objetivo();
		}
		
		objetivo.setNombreObjetivo((String)params.get(ParamsConst.NOMBRE_OBJETIVO));
		objetivo.setTipoObjetivo((String)params.get(ParamsConst.TIPO_OBJETIVO));
		objetivo.setEscalaMedicion((String)params.get(ParamsConst.ESCALA_MEDICION));
		objetivo.setFechaFinalizacion((Date)params.get(ParamsConst.FECHA_FINALIZACION));
		objetivo.setPonderacion((Integer)params.get(ParamsConst.PONDERACION));
		objetivo.setIdUsuarioAsignado((String) params.get(ParamsConst.ID_USUARIO_ASIGNADO));
		objetivo.setDescripcion((String)params.get(ParamsConst.DESCRIPCION));
		
		HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
		User user = (User) request.getSession().getAttribute(ParamsConst.USER);
		
		objetivo.setCreator(user.getId());
		
		objetivo.defaultVisibles();
		
		Collection usersView = (Collection) params.get(ParamsConst.USERS_VIEW);
		if (usersView!=null){
			Iterator<String> itUsers = usersView.iterator();
			while (itUsers.hasNext()) {
				String actual = (String) itUsers.next();
				objetivo.addVisible(actual);	
			}
		}
		
		return objetivo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
