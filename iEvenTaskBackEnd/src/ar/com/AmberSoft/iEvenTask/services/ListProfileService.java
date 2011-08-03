package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;


public class ListProfileService extends ListService {

	@Override
	protected String getEntity() {
		return Perfil.class.getName();
	}
	


}
