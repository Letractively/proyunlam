package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_relacion")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Relation extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity  {
	
	private Integer id;
	private Event event;

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

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name = "iet_evento", joinColumns = { @JoinColumn(name = "id_evento") }, inverseJoinColumns = { @JoinColumn(name = "id_relacion") })
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	

}
