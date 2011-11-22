package ar.com.AmberSoft.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.iEvenTask.services.ListProfileService;
import ar.com.AmberSoft.iEvenTask.utils.Tools;

public class ProfileManager {

	private static Logger logger = Logger.getLogger(ProfileManager.class);
	
	private static ProfileManager instance;
	private Map <String, Profile> profiles;
	
	private ProfileManager(){
		try {
			load();
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
	}


	public Profile getProfile(String group){
		return profiles.get(group.trim());
	}
	
	public void load()  throws Exception {
		profiles = new HashMap<String, Profile>();
		ListProfileService service = new ListProfileService();
		Map params = new HashMap();
		Map result = service.execute(params) ;
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
