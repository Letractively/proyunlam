package ar.com.AmberSoft.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPClient {
	
	public static final String DOMAIN = "java.naming.security.domain";
	public static final String DOUBLE_BAR = "\\";
	private static final ResourceBundle config = ResourceBundle.getBundle("config"); 

	/**
	 * En caso de no poder autentificarse en el LDAP lanza una excepcion de tipo runtime
	 * @param user
	 * @param password
	 */
	public static void authenticate(String user, String password){

        DirContext ctx = null;
        try {
        	ctx = connect(user, password);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }
		
	}

	public static void search(String user, String password){
		NamingEnumeration results = null;
        DirContext ctx = null;
        try {
        	ctx = connect(user, password);
        	SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrs={"distinguishedName","lastLogon"};
            ctls.setReturningAttributes(attrs);
            results = ctx.search("dc=" + config.getString(DOMAIN), "(objectclass=*)", ctls);
            while (results.hasMore()){
            	SearchResult result = (SearchResult) results.next();
            	Attributes attribs = result.getAttributes();
            }
        	
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }
	}
	
	/**
	 * Establece la conexion con el LDAP
	 * @param user
	 * @param password
	 * @return
	 * @throws NamingException
	 */
	public static DirContext connect(String user, String password)
			throws NamingException {
		DirContext ctx;
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, config.getString(Context.INITIAL_CONTEXT_FACTORY));
		env.put(Context.PROVIDER_URL, config.getString(Context.PROVIDER_URL));
		env.put(Context.SECURITY_AUTHENTICATION, config.getString(Context.SECURITY_AUTHENTICATION));
		env.put(Context.SECURITY_PRINCIPAL, config.getString(DOMAIN)+ DOUBLE_BAR + user);
		env.put(Context.SECURITY_CREDENTIALS, password);
      
		ctx = new InitialDirContext(env);
		return ctx;
	}
	
	
	
	
}
