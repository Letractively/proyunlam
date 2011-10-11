package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Permission;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateProfileService extends CreateService {

	// Los siguientes IDs tienen que tener relacion con los ids que se encuentran en la tabla de permisos
	public static String ID_OBJECTIVE = "1";
	public static String ID_ADMIN = "2";

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

	@Override
	public Entity getEntity(Map params) {
		Profile profile = new Profile();
		profile.setGroupLDAP((String) params.get(ParamsConst.GROUP));
		
		Boolean checkObjective = (Boolean) params.get(ParamsConst.CHECK_OBJECTIVE);
		Boolean checkAdmin = (Boolean) params.get(ParamsConst.CHECK_ADMIN);
		
		setPermiso(profile, checkObjective, CreateProfileService.ID_OBJECTIVE);
		setPermiso(profile, checkAdmin, CreateProfileService.ID_ADMIN);
		return profile;
	}

	public void setPermiso(Profile perfil, Boolean toCheck, String idPermiso) {
		if (toCheck){
			Permission permiso = new Permission();
			permiso.setId(idPermiso);
			perfil.addPermiso(permiso);
		}
	}

	
}
