package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_comentario")
public class Comentario {
	private Integer id_comentario;
	private String comentario;
	private Tarea tarea;
	private String usuario;
	private String nombreUsuario;
	private Date fecha;
	
	@Transient
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	@Basic @Column(name="id_usuario")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Basic @Column(name="fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn (name="id_tarea")
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

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

	@Basic @Column (name="comentario")
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}

