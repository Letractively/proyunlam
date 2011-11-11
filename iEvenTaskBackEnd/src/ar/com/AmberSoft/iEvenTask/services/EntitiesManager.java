package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;

public class EntitiesManager {
	
	private static EntitiesManager instance;
	
	private Map entities;
	
	public Map getEntities() {
		return entities;
	}

	private EntitiesManager(){
		entities = new HashMap();
	}
	
	public static EntitiesManager getInstance(){
		if (instance==null){
			instance = new EntitiesManager();
		}
		return instance;
	}
	
	public static User lock(Object key, User user){
		
		User actualUser =  (User)getInstance().entities.get(key);
		if (actualUser!=null){
			return actualUser;
		} 
		
		user.setLockTime(new Date());
		getInstance().entities.put(key, user);
		return null;
	}
	
	public static void unlock(Object key){
		getInstance().entities.put(key, null);
	}
	
	public static void unlockAll(User user){
		if (getInstance().entities!=null){
			Iterator it = getInstance().entities.keySet().iterator();
			while (it.hasNext()) {
				Object key = (Object) it.next();
				User actual = (User)getInstance().entities.get(key);
				if ((actual!=null)&&(actual.equals(user))){
					getInstance().entities.put(key, null);
				}
			}
		}
	}
	
}
