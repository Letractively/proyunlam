package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="iet_perfil")
public class Perfil {
	
	private String id;
	private String nombre;
	private String conexion;
	private String grupoLDAP;
	private Set<Permiso> permisos;
	
	@Id @Column (name="id_perfil")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Basic
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Basic
	public String getConexion() {
		return conexion;
	}
	public void setConexion(String conexion) {
		this.conexion = conexion;
	}
	@Basic
	public String getGrupoLDAP() {
		return grupoLDAP;
	}
	public void setGrupoLDAP(String grupoLDAP) {
		this.grupoLDAP = grupoLDAP;
	}
	@ManyToOne(targetEntity=ar.com.AmberSoft.iEvenTask.backend.entities.Permiso.class)
	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}
	public Set<Permiso> getPermisos() {
		return permisos;
	}
	
	
	
	
	
	
}
