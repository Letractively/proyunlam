package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table (name="iet_visible")
public class Visible {

	private String id;
	private String usuario;
	private Tarea tarea;
	
	public Visible(){}
	
	
	public Visible(Tarea tarea, String usuario){
		this.tarea = tarea;
		this.usuario = usuario;
		changeTask(tarea);
	}

	@Transient
	public void changeTask(Tarea tarea) {
		this.tarea = tarea;
		if ((tarea!=null) && (tarea.getId())!=null && (usuario!=null)){
			this.id = usuario + tarea.getId().toString();
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
	@JoinColumn (name="id_tarea")
	@OnDelete(action=OnDeleteAction.CASCADE)
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
