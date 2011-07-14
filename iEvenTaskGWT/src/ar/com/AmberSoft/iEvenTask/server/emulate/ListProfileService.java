package ar.com.AmberSoft.iEvenTask.server.emulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

public class ListProfileService implements Emulate {

	@Override
	public Map execute(Map params) {
		Map map = new HashMap();
		Collection list = new ArrayList();
		map.put(ParamsConst.LIST, list);
		
		Map profile = new HashMap();
		profile.put(ParamsConst.NAME, "Perfil1");
		profile.put(ParamsConst.CONECTION, "Conexion1");
		profile.put(ParamsConst.GROUP, "Grupo1");
		list.add(profile);
		
		return map;
	}

}
