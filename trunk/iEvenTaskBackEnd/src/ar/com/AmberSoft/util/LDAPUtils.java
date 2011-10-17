package ar.com.AmberSoft.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import ar.com.AmberSoft.iEvenTask.backend.entities.LDAPGroup;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.services.GetProfileService;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPSearchResults;

/**
 * Informacion relevante por usuario
 * 
name:logonCount, value:0
name:sAMAccountType, value:805306368
name:sAMAccountName, value:analista8
name:memberOf, value:CN=G_ieventask_analista,CN=Users,DC=amber,DC=local
name:objectCategory, value:CN=Person,CN=Schema,CN=Configuration,DC=amber,DC=local
name:whenCreated, value:20110925191320.0Z
name:lastLogon, value:0
name:primaryGroupID, value:513
name:pwdLastSet, value:129614516008783213
name:badPasswordTime, value:0
name:objectSid, value:
name:accountExpires, value:9223372036854775807
name:objectClass, value:top
name:uSNCreated, value:57484
name:objectGUID, value:??`%?C??+?O"??
name:name, value:Analista 8
name:whenChanged, value:20110925191320.0Z
name:uSNChanged, value:57489
name:codePage, value:0
name:lastLogoff, value:0
name:distinguishedName, value:CN=Analista 8,CN=Users,DC=amber,DC=local
name:sn, value:8
name:userAccountControl, value:66048
name:countryCode, value:0
name:displayName, value:Analista 8
name:givenName, value:Analista
name:userPrincipalName, value:analista8@amber.local
name:instanceType, value:4
name:badPwdCount, value:0
name:cn, value:Analista 8
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class LDAPUtils {

	private static final ResourceBundle config = ResourceBundle.getBundle("config");
	public static final String DOMAIN = "ldap.domain";
	public static final String IP = "ldap.ip";
	public static final String PORT = "ldap.port";
	public static final String DEFAULT_USER = "ldap.default.user";
	public static final String DEFAULT_PASSWORD = "ldap.default.password";
	public static final String DOUBLE_BAR = "\\";
	public static final String UTF8 = "UTF8";
	
	
	/**
	 * En caso de no poder autentificarse en el LDAP lanza una excepcion de tipo runtime
	 * @param user
	 * @param password
	 */
	public static User authenticate(String user, String password){

		LDAPConnection connection = new LDAPConnection();
		try {
			// Se establece la conexion con el servidor LDAP
			connection.connect(config.getString(IP), new Integer(config.getString(PORT)));
			
			// Se autentica el usuario
			byte[] encryptPassword = password.getBytes(UTF8);
			connection.bind(LDAPConnection.LDAP_V3, config.getString(DOMAIN)+ DOUBLE_BAR + user, encryptPassword);
			
			Collection<User> users = search(user, encryptPassword);
			Iterator it = users.iterator();
			while (it.hasNext()) {
				User actual = (User) it.next();
				if (user.equals(actual.getId())){
					actual.setPassword(encryptPassword);
					return actual;
				}
			}
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
        
        return null;
	}
	
	public static Collection<LDAPGroup> searchGroups(String user,  byte[] password){
		
		Set<LDAPGroup> groups = new HashSet<LDAPGroup>();
		
		LDAPConnection connection = new LDAPConnection();
		try {
			// Se establece la conexion con el servidor LDAP
			connection.connect(config.getString(IP), new Integer(config.getString(PORT)));
			
			// Se autentica el usuario
			connection.bind(LDAPConnection.LDAP_V3, config.getString(DOMAIN)+ DOUBLE_BAR + user, password);
			
			LDAPSearchResults ldapSearchResults = connection.search("CN=Users,DC=amber,DC=local", 
					LDAPConnection.SCOPE_SUB, "(&(objectCategory=person)(objectClass=user))", null, Boolean.FALSE);

			while (ldapSearchResults.hasMore()){
				LDAPEntry ldapEntry = ldapSearchResults.next();
				
				LDAPAttribute ldapAttribute = ldapEntry.getAttribute("memberOf");
				if (ldapAttribute!=null){
					String memberOf = ldapAttribute.getStringValue();
					String[] items = memberOf.split(",");
					if (items!=null){
						Collection<String> cItems = Arrays.asList(items);;
						Iterator<String> it = cItems.iterator();
						while (it.hasNext()) {
							String actual = (String) it.next();
							String [] subItems = actual.split("=");
							if (subItems!=null){
								Collection<String> cSubItems = Arrays.asList(subItems); 
								Iterator<String> itSubs = cSubItems.iterator();
								if ((itSubs.hasNext()) && ("CN".equals(itSubs.next()))){
									String possibleProfile = itSubs.next();
									LDAPGroup group = new LDAPGroup(possibleProfile);
									groups.add(group);
								}
							}
						}
					}
				}
				
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return groups;
	}

	public static Map<String, User> getUsersMap(String user, byte[] password) throws UnsupportedEncodingException{
		Map <String, User> map = new HashMap<String, User>();
		Collection<User> users = search(user, password);
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User actual = (User) it.next();
			map.put(actual.getId(), actual);
		}
		return map;
	}
	
	public static Collection<User> search(String user, String password) throws UnsupportedEncodingException{
		return search(user, password.getBytes(UTF8));
	}
	
	public static Collection<User> search(String user, byte[] password){
		
		Collection<User> users = new ArrayList<User>();
		
		LDAPConnection connection = new LDAPConnection();
		try {
			// Se establece la conexion con el servidor LDAP
			connection.connect(config.getString(IP), new Integer(config.getString(PORT)));
			
			// Se autentica el usuario
			connection.bind(LDAPConnection.LDAP_V3, config.getString(DOMAIN)+ DOUBLE_BAR + user, password);
			
			LDAPSearchResults ldapSearchResults = connection.search("CN=Users,DC=amber,DC=local", LDAPConnection.SCOPE_SUB, "(&(objectCategory=person)(objectClass=user))", null, Boolean.FALSE);

			while (ldapSearchResults.hasMore()){
				LDAPEntry ldapEntry = ldapSearchResults.next();

				users.add(getUser(ldapEntry));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return users;
	}


	public static User getUser(LDAPEntry ldapEntry) {
		User user = new User();
		user.setId(getValue(ldapEntry, "sAMAccountName"));
		user.setName(getValue(ldapEntry, "displayName"));
		//user.setProfile(getProfile(ldapEntry, "memberOf"));
		user.setCreated(getDate(ldapEntry, "whenCreated"));
		user.setChanged(getDate(ldapEntry, "whenChanged"));
		user.setLastLogon(getDate(ldapEntry, "lastLogon"));
		user.setLastLogoff(getDate(ldapEntry, "lastLogoff"));
		return user;
	}

	/**
	 * 20110925191320.0Z
	 * @param ldapEntry
	 * @param attribute
	 * @return
	 */
	public static Date getDate(LDAPEntry ldapEntry, String attribute){
		LDAPAttribute ldapAttribute = ldapEntry.getAttribute(attribute);
		if (ldapAttribute!=null){
			String cDate = ldapAttribute.getStringValue();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				// Sentido estricto, para que valide tambien que la fecha tenga sentido
				format.setLenient(Boolean.FALSE);
				return format.parse(cDate);
			} catch (ParseException e) {
				System.err.println("No se pudo parsear " + attribute + ":" + cDate);
				if (cDate.length() >= 18){
					long llastLogonAdjust=11644473600000L;  // adjust factor for converting it to java    
					return new Date(new Long(cDate)/10000-llastLogonAdjust);
				}
				
			}
		}
		return null;
	}
	
	public static String getValue(LDAPEntry ldapEntry, String attribute){
		LDAPAttribute ldapAttribute = ldapEntry.getAttribute(attribute);
		if (ldapAttribute!=null){
			return ldapAttribute.getStringValue();
		}
		return "";
	}
	
	/**
	 * name:memberOf, value:CN=G_ieventask_analista,CN=Users,DC=amber,DC=local
	 * @param ldapEntry
	 * @param attribute
	 * @return
	 */
	public static Profile getProfile(LDAPEntry ldapEntry, String attribute){
		LDAPAttribute ldapAttribute = ldapEntry.getAttribute(attribute);
		if (ldapAttribute!=null){
			String memberOf = ldapAttribute.getStringValue();
			String[] items = memberOf.split(",");
			if (items!=null){
				Collection<String> cItems = Arrays.asList(items);;
				Iterator<String> it = cItems.iterator();
				while (it.hasNext()) {
					String actual = (String) it.next();
					String [] subItems = actual.split("=");
					if (subItems!=null){
						Collection<String> cSubItems = Arrays.asList(subItems); 
						Iterator<String> itSubs = cSubItems.iterator();
						if ((itSubs.hasNext()) && ("CN".equals(itSubs.next()))){
							String possibleProfile = itSubs.next();
							Map params = new HashMap();
							params.put(ParamsConst.ID, possibleProfile);
							GetProfileService service = new GetProfileService();
							Map resp = service.execute(params);
							Profile profile = (Profile) resp.get(ParamsConst.ENTITY);
							if (profile!=null){
								return profile;
							}
						}
					}
				}
			}
		}
		return null;
		
	}
	
}
