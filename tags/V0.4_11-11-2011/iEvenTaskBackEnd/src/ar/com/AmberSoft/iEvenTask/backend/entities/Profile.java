package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_perfil")
public class Profile extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity  {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer id;
	private String groupLDAP;
	private Set<Permission> permissions;
	
	@Id @Column (name="id_perfil")
	public Integer getId() {
		if (id==null){
			PKGenerator pkGenerator = new PKGenerator();
			id = new Integer(pkGenerator.getIntLastTime());
		}
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Basic @Column(name="grupo_ldap")
	public String getGroupLDAP() {
		return groupLDAP;
	}
	public void setGroupLDAP(String groupLDAP) {
		this.groupLDAP = groupLDAP;
	}
	
	@ManyToMany (fetch=FetchType.EAGER)
	@JoinTable(name="iet_permiso_perfil", 
			joinColumns = { @JoinColumn(name = "id_perfil") }, 
			inverseJoinColumns = { @JoinColumn(name = "id_permiso") })
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public void addPermiso(Permission permiso){
		if (permissions==null){
			permissions = new HashSet<Permission>();
		}
		permissions.add(permiso);
	}
	
}
