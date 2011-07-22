package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_perfil")
public class Perfil implements Serializable{
	
	private Integer id;
	private String nombre;
	private String conexion;
	private String grupoLDAP;
	private Set<Permiso> permisos;
	
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
	@Basic
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Basic @Column (name="ruta_conexion")
	public String getConexion() {
		return conexion;
	}
	public void setConexion(String conexion) {
		this.conexion = conexion;
	}
	@Basic @Column(name="grupo_ldap")
	public String getGrupoLDAP() {
		return grupoLDAP;
	}
	public void setGrupoLDAP(String grupoLDAP) {
		this.grupoLDAP = grupoLDAP;
	}
	/*@ManyToOne(targetEntity=ar.com.AmberSoft.iEvenTask.backend.entities.Permiso.class)
	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}
	public Set<Permiso> getPermisos() {
		return permisos;
	}*/
	
	
	
	
	
	
}
