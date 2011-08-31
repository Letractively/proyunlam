package ar.com.AmberSoft.iEvenTask.server.emulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.utils.PagingLoadResult;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

public class ListProfileService implements Emulate {

	@Override
	public Map execute(Map params) {
		
			Map map = new PagingLoadResult();
			Collection list = new ArrayList();
			add(list, "Perfil1", "Grupo1", "Conexion1");
			add(list, "Perfil2", "Grupo2", "Conexion2");
			add(list, "Perfil3", "Grupo3", "Conexion3");
			add(list, "Perfil4", "Grupo4", "Conexion4");
			add(list, "Perfil5", "Grupo5", "Conexion5");
			add(list, "Perfil6", "Grupo6", "Conexion6");
			add(list, "Perfil7", "Grupo7", "Conexion7");
			add(list, "Perfil8", "Grupo8", "Conexion8");
			add(list, "Perfil9", "Grupo9", "Conexion9");
			add(list, "Perfil10", "Grupo10", "Conexion10");
			map.put(ParamsConst.DATA, list);
			map.put(ParamsConst.TOTAL_COUNT, new Long(list.size()));
			map.put(ParamsConst.OFFSET, (Integer) params.get("offset"));
			
			return map;
		
	}

	private void add(Collection list, String perfil, String grupo,
			String conexion) {
		Map profile = new HashMap();
		profile.put(ParamsConst.ID, perfil);
		profile.put(ParamsConst.NAME, perfil);
		profile.put(ParamsConst.CONECTION, conexion);
		profile.put(ParamsConst.GROUP, grupo);
		list.add(profile);
	}

}
