package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="iet_obj_visible")
public class VisibleObjetivo {
	
	private String id;
	private String usuario;
	private Objetivo objetivo;
	
	public VisibleObjetivo(){}
	
	public VisibleObjetivo(Objetivo objetivo, String usuario){
		this.objetivo = objetivo;
		this.usuario = usuario;
		if ((objetivo!=null) && (objetivo.getId())!=null && (usuario!=null)){
			this.id = usuario + objetivo.getId().toString();
		}
	}
	
	@Id @Column (name="id_visible")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn (name="id_objetivo")
	public Objetivo getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}
	
	@Basic @Column (name="id_usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public boolean equals(Object actual) {
		if (actual instanceof Visible) {
			return ((Visible)actual).getId().equals(this.getId());
		}
		return Boolean.FALSE;
	}
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
