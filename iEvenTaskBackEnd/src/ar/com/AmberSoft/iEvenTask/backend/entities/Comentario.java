package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_comentario")
@Inheritance(strategy=InheritanceType.JOINED)
public class Comentario {
	private Integer id_comentario;
	private Tarea task;
	private String comentario;
	
	public Comentario(){
		PKGenerator pkGenerator = new PKGenerator();
		id_comentario = new Integer(pkGenerator.getIntLastTime());
	}
	
	@Id @Column(name="id_comentario")
	public Integer getId_comentario() {
		return id_comentario;
	}

	public void setId_comentario(Integer id_comentario) {
		this.id_comentario = id_comentario;
	}

	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn (name="id_tarea")
	public Tarea getTask() {
		return task;
	}

	public void setTask(Tarea task) {
		this.task = task;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}

