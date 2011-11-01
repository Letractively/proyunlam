package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.HashSet;
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
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer id;
	private Event event;
	private Set<VisibleRelation> visibles;

	@Transient
	public void addVisible(String usuario){
		if (visibles==null){
			visibles = new HashSet<VisibleRelation>();
		}
		VisibleRelation visible = new VisibleRelation(this, usuario);
		visibles.add(visible);
	}
	
	@OneToMany (mappedBy="relation", fetch=FetchType.LAZY, cascade=CascadeType.ALL )
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
	
	public abstract void execute();

}
