package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.iEvenTask.backend.entities.Permission;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateProfileService extends Service {

	// Los siguientes IDs tienen que tener relacion con los ids que se encuentran en la tabla de permisos
	public static String ID_OBJECTIVE = "1";
	public static String ID_ADMIN = "2";
	
	protected void setAttributes(Map params, Profile perfil) {
		perfil.setName((String) params.get(ParamsConst.NAME));
		perfil.setConnection((String) params.get(ParamsConst.CONNECTION));
		perfil.setGroupLDAP((String) params.get(ParamsConst.GROUP));
		
		Boolean checkObjective = (Boolean) params.get(ParamsConst.CHECK_OBJECTIVE);
		Boolean checkAdmin = (Boolean) params.get(ParamsConst.CHECK_ADMIN);
		
		setPermiso(perfil, checkObjective, CreateProfileService.ID_OBJECTIVE);
		setPermiso(perfil, checkAdmin, CreateProfileService.ID_ADMIN);
	}

	public void setPermiso(Profile perfil, Boolean toCheck, String idPermiso) {
		if (toCheck){
			Permission permiso = new Permission();
			permiso.setId(idPermiso);
			perfil.addPermiso(permiso);
		}
	}

	@Override
	public Map onExecute(Map params) {
		Profile perfil = new Profile();
		setAttributes(params, perfil);
		
		Transaction transaction = getSession().beginTransaction();
		
		getSession().save(perfil);
		transaction.commit();

		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
