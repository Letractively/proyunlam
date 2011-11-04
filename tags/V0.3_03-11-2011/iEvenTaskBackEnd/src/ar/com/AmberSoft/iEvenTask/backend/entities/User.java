package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.Date;

public class User {

	private String id;
	private String name;
	private Profile profile;
	private Date created;
	private Date changed;
	private Date lastLogon;
	private Date lastLogoff;
	private byte[] password;
	
	

	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public Date getChanged() {
		return changed;
	}
	public void setChanged(Date changed) {
		this.changed = changed;
	}
	public Date getLastLogoff() {
		return lastLogoff;
	}
	public void setLastLogoff(Date lastLogoff) {
		this.lastLogoff = lastLogoff;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLastLogon() {
		return lastLogon;
	}
	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}

}
