package ar.com.AmberSoft.iEvenTask.utils;

import java.util.ResourceBundle;

public class Config {

	private ResourceBundle config = ResourceBundle.getBundle("config");;
	private Boolean emulate;
	
	public Config(){
		String configProperty = config.getString("service.emulate");
		emulate = Boolean.FALSE;
		try{
			emulate = new Boolean(configProperty);
		}catch (Exception e){}
	}
	
	public Boolean isEmulate(){
		return emulate;
	}
	
	
	
}
