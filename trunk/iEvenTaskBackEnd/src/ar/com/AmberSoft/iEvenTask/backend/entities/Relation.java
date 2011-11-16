package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_relacion")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Relation extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity  {
	
	public static final String EVENT_FILES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles";
	public static final String EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP";
	public static final String EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs";
	public static final String EVENT_SERVICES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventServices";
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer id;
	private Event event;
	private Set<VisibleRelation> visibles;
	
	@Transient
	public String getTipo(){
		Map tipos = new HashMap();
		tipos.put(EVENT_LDAP, "LDAP");
		tipos.put(EVENT_LOGS, "Patron en logs");
		tipos.put(EVENT_FILES, "Archivos");
		tipos.put(EVENT_SERVICES, "Servicios");
		return (String) tipos.get(this.getEvent().getClass().getName());
	}
	
	public void setTipo(String tipo){}
	
	@Transient
	public String getNombreEvento(){
		return this.event.getName();
	}
	
	public void setNombreEvento(String nombre){}
	
	@Transient
	public String getAccion(){
		if (this instanceof RelationWithActionCreateTask) {
			return "Crear Tarea";
			
		}
		return "Modificar Estado";
	}
	
	public void setAccion(String accion){}
	
	
	@Transient
	public void addVisible(String usuario){
		if (visibles==null){
			visibles = new HashSet<VisibleRelation>();
		}
		VisibleRelation visible = new VisibleRelation(this, usuario);
		visibles.add(visible);
	}
	
	@OneToMany (mappedBy="relation", fetch=FetchType.EAGER, cascade=CascadeType.ALL )
	public Set<VisibleRelation> getVisibles() {
		return visibles;
	}
	public void setVisibles(Set<VisibleRelation> visibles) {
		this.visibles = visibles;
	}
	
	public Relation(){
		PKGenerator pkGenerator = new PKGenerator();
		id = new Integer(pkGenerator.getIntLastTime());
	}
	
	@Id @Column (name="id_relacion")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne (fetch=FetchType.EAGER)
	@JoinColumn (name="id_evento")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public abstract void execute()  throws Exception ;

}
