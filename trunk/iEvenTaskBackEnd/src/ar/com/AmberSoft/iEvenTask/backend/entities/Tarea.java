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
@Table (name="iet_tarea")
public class Tarea extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombreTarea;
	private Date fechaComienzo;
	private Date fechaFin;
	private String duracion;
	private String descripcion;

	private String estado; //estado de la tarea "pendiente", "en curso", "suspendida"
	private int cumplimiento; //porcentaje de completitud de la tarea
	/**
	 * Usuario creador de la tarea
	 */
	private String creator;
	/**
	 * Usuario que tiene asignada la tarea actualmente
	 */
	private String id_usuario;
	private String asignado;
	
	private int horasAsignadas;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private int tipo_tarea;
	private Set<Comentario> comentarios;
	private Set<Visible> visibles;
	
	public Tarea() {
		super();
	}

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
		if (visibles!=null){
			Iterator<Visible> it = visibles.iterator();
			while (it.hasNext()) {
				Visible visible = (Visible) it.next();
				visible.changeTask(this);
			}
		}
	}
	@Basic @Column (name="nombre_tarea")
	public String getNombreTarea() {
		return nombreTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	@Basic @Column (name="fecha_comienzo")
	public Date getFechaComienzo() {
		return fechaComienzo;
	}
	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}
	@Basic @Column (name="duracion")
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	@Basic @Column (name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
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
	
	@OneToMany (mappedBy="tarea", fetch=FetchType.LAZY)
	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Transient
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}

	@Transient
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@OneToMany (mappedBy="tarea", fetch=FetchType.LAZY, cascade=CascadeType.ALL )
	public Set<Visible> getVisibles() {
		return visibles;
	}
	public void setVisibles(Set<Visible> visibles) {
		this.visibles = visibles;
	}
	
	@Transient
	public void defaultVisibles() {
		visibles = new HashSet<Visible>();
		obligatoryVisibles();
	}
	
	@Transient
	public void addVisible(String usuario){
		Visible visible = new Visible(this, usuario);
		visibles.add(visible);
	}
	
	private void obligatoryVisibles(){
		if (this.creator!=null){
			visibles.add(new Visible(this, this.creator));
		}
		if (this.id_usuario!=null){
			visibles.add(new Visible(this, this.id_usuario));
		}
	}
	
	
	
	
}
