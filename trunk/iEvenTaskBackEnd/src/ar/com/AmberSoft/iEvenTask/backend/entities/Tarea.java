package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_tarea")
@Inheritance(strategy=InheritanceType.JOINED)
public class Tarea implements Serializable{

	private Integer id;
	private Date fechaInicio;
	private Date fechaFin;
	private int horasAsignadas;
	private String id_usuario;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private int estado;
	private int cumplimiento;
	private int tipo_tarea;
	private Set<Comentario> comentarios;
	
	@Id @Column (name="id_tarea")
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
	
	@Basic @Column (name="fecha_inicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	@Basic @Column (name="fecha_fin")
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	@Basic @Column (name="horas_asignadas")
	public int getHorasAsignadas() {
		return horasAsignadas;
	}
	public void setHorasAsignadas(int horasAsignadas) {
		this.horasAsignadas = horasAsignadas;
	}
	@Basic @Column (name="id_usuario")
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	@Basic @Column (name="fecha_creacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	@Basic @Column (name="fecha_modificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	@Basic @Column (name="estado")
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Basic @Column (name="cumplimiento")
	public int getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(int cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	@Basic @Column (name="tipo_tarea")
	public int getTipo_tarea() {
		return tipo_tarea;
	}
	public void setTipo_tarea(int tipo_tarea) {
		this.tipo_tarea = tipo_tarea;
	}
	
	@OneToMany (mappedBy="task")
	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void addComentario(Comentario comentario){
		if (comentarios==null){
			comentarios = new HashSet<Comentario>();
		}
		comentarios.add(comentario);
	}
}
