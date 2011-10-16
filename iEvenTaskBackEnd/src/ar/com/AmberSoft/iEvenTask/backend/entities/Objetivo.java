package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String idUsuarioAsignado;
	private String descripcion;
	
	//GETTERS
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
	//SETTERS
	public void setId(Integer id) {
		this.id = id;
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
	
}
