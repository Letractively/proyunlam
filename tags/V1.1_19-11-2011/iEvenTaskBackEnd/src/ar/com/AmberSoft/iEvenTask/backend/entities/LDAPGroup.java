package ar.com.AmberSoft.iEvenTask.backend.entities;


public class LDAPGroup {

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof LDAPGroup) {
			LDAPGroup new_name = (LDAPGroup) arg0;
			return this.name.equals(new_name.getName());
			
		}
		return super.equals(arg0);
	}

	private String name;
	
	public LDAPGroup(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
