package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.iEvenTask.backend.entities.Permiso;
import ar.com.AmberSoft.util.ParamsConst;


public class ListProfileService extends ListService {

	@Override
	protected String getEntity() {
		return Perfil.class.getName();
	}

	@Override
	public Map onEmulate(Map params) {
		Map map = new HashMap();
		Collection list = new ArrayList();
		addPerfil(list, Boolean.TRUE, Boolean.TRUE);
		addPerfil(list, Boolean.FALSE, Boolean.TRUE);
		addPerfil(list, Boolean.TRUE, Boolean.FALSE);
		addPerfil(list, Boolean.TRUE, Boolean.TRUE);
		addPerfil(list, Boolean.TRUE, Boolean.TRUE);
		addPerfil(list, Boolean.FALSE, Boolean.TRUE);
		addPerfil(list, Boolean.TRUE, Boolean.TRUE);
		addPerfil(list, Boolean.TRUE, Boolean.FALSE);
		addPerfil(list, Boolean.TRUE, Boolean.FALSE);
		addPerfil(list, Boolean.FALSE, Boolean.TRUE);
		map.put(ParamsConst.DATA, list);
		map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
		map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);
		return map;
	}

	private void addPerfil(Collection list, Boolean permiso1, Boolean permiso2) {
		Perfil perfil = new Perfil();
		perfil.setNombre("Perfil" + perfil.getId().toString());
		perfil.setConexion("Conexion" + perfil.getId().toString());
		perfil.setGrupoLDAP("Grupo" + perfil.getId().toString());
		if (permiso1) {
			addPermiso(perfil, CreateProfileService.ID_OBJECTIVE);
		}
		if (permiso2) {
			addPermiso(perfil, CreateProfileService.ID_ADMIN);
		}
		list.add(perfil);
	}

	private void addPermiso(Perfil perfil, String id) {
		Permiso permiso = new Permiso();
		permiso.setId(id);
		perfil.addPermiso(permiso);
	}
	


}
