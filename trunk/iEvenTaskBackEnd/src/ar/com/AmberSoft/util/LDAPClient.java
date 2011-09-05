package ar.com.AmberSoft.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPClient {
	
	public static final String DOMAIN = "java.naming.security.domain";
	public static final String DOUBLE_BAR = "\\";

	public static void authenticate(String user, String password){
		
		
		//FIXME: CORREGIR PARA QUE LLAME AL APPADMIN QUE YA TIENE LA CONFIGURACION CARGADA
		ResourceBundle config = ResourceBundle.getBundle("config");
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, config.getString(Context.INITIAL_CONTEXT_FACTORY));
        env.put(Context.PROVIDER_URL, config.getString(Context.PROVIDER_URL));
        env.put(Context.SECURITY_AUTHENTICATION, config.getString(Context.SECURITY_AUTHENTICATION));
        env.put(Context.SECURITY_PRINCIPAL, config.getString(DOMAIN)+ DOUBLE_BAR + user);
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx = null;
        NamingEnumeration results = null;
        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }
		
		
	}
	
	
}
