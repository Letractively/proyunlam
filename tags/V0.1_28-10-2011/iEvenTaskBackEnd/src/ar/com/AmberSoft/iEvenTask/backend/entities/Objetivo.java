package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_objetivo")
public class Objetivo extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private Integer id;
	private String nombreObjetivo;
	private String tipoObjetivo;
	private String escalaMedicion;
	private Date fechaFinalizacion;
	private int ponderacion;
	/**
	 * Usuario que tiene asignada la tarea actualmente
	 */
	private String idUsuarioAsignado;
	private String descripcion;
	/**
	 * Usuario creador de la tarea
	 */
	private String creator;
	private String asignado;
	private Set<VisibleObjetivo> visibles;
	private Set<Tarea> tareas;
	
	@Id @Column (name="id_objetivo")
	public Integer getId() {
		if (id==null){
			PKGenerator pkGenerator = new PKGenerator();
			id = new Integer(pkGenerator.getIntLastTime());
		}
		return id;
	}
	@Basic @Column (name="nombre_objetivo")
	public String getNombreObjetivo() {
		return nombreObjetivo;
	}
	@Basic @Column (name="tipo_objetivo")
	public String getTipoObjetivo() {
		return tipoObjetivo;
	}
	@Basic @Column (name="escala_medicion")
	public String getEscalaMedicion() {
		return escalaMedicion;
	}
	@Basic @Column (name="fecha_finalizacion")
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	@Basic @Column (name="ponderacion")
	public int getPonderacion() {
		return ponderacion;
	}
	@Basic @Column (name="id_usuario_asignado")
	public String getIdUsuarioAsignado() {
		return idUsuarioAsignado;
	}
	@Basic @Column (name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setId(Integer id) {
		this.id = id;
		if (visibles!=null){
			Iterator<VisibleObjetivo> it = visibles.iterator();
			while (it.hasNext()) {
				VisibleObjetivo visible = (VisibleObjetivo) it.next();
				visible.changeObjective(this);
			}
		}
	}
	
	public void setNombreObjetivo(String nombreObjetivo) {
		this.nombreObjetivo = nombreObjetivo;
	}
	public void setTipoObjetivo(String tipoObjetivo) {
		this.tipoObjetivo = tipoObjetivo;
	}
	public void setEscalaMedicion(String escalaMedicion) {
		this.escalaMedicion = escalaMedicion;
	}
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}
	public void setIdUsuarioAsignado(String idUsuarioAsignado) {
		this.idUsuarioAsignado = idUsuarioAsignado;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Transient
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@OneToMany (mappedBy="objetivo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public Set<VisibleObjetivo> getVisibles() {
		return visibles;
	}
	public void setVisibles(Set<VisibleObjetivo> visibles) {
		this.visibles = visibles;
	}
	
	@Transient
	public void defaultVisibles() {
		if (visibles==null){
			visibles = new HashSet<VisibleObjetivo>();
		}
		obligatoryVisibles();
	}
	
	@Transient
	public void addVisible(String usuario){
		VisibleObjetivo visible = new VisibleObjetivo(this, usuario);
		visibles.add(visible);
	}
	
	private void obligatoryVisibles(){
		if (this.creator!=null){
			visibles.add(new VisibleObjetivo(this, this.creator));
		}
		if (this.idUsuarioAsignado!=null){
			visibles.add(new VisibleObjetivo(this, this.idUsuarioAsignado));
		}
	}
	
	@Transient
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	
	@OneToMany (mappedBy="objetivo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public Set<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}
}
