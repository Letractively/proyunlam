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
@Table (name="iet_visible")
public class Visible {

	private String id;
	private String usuario;
	private Tarea tarea;
	
	@Id @Column (name="id_visible")
	public String getId() {
		return usuario + tarea.getId().toString();
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn (name="id_tarea")
	public Tarea getTarea() {
		return tarea;
	}
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	@Basic @Column (name="id_usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
