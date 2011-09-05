package ar.com.AmberSoft.iEvenTask.utils;


public class AppAdmin {
	
	private static AppAdmin instance;
	private Config config;
	
	private AppAdmin(){
		config = new Config();
	}
	
	public static AppAdmin getInstance(){
		if (instance==null){
			instance = new AppAdmin();
		}
		return instance;
	}
	
	public Config getConfig(){
		return config;
	}
	
	
	
	
}
