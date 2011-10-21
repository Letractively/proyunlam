package ar.com.AmberSoft.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.iEvenTask.services.ListProfileService;

public class ProfileManager {

	private static ProfileManager instance;
	private Map <String, Profile> profiles;
	
	private ProfileManager(){
		load();
	}


	public Profile getProfile(String group){
		return profiles.get(group.trim());
	}
	
	public void load() {
		profiles = new HashMap<String, Profile>();
		ListProfileService service = new ListProfileService();
		Map params = new HashMap();
		Map result = service.execute(params);
		Collection datas = (Collection) result.get(ParamsConst.DATA);
		Iterator itData = datas.iterator();
		while (itData.hasNext()) {
			Profile actual = (Profile) itData.next();
			profiles.put(actual.getGroupLDAP().trim(), actual);
		}
	}

	
	public static ProfileManager getInstance(){
		if (instance==null){
			instance = new ProfileManager();
		}
		return instance;
	}
	
}
