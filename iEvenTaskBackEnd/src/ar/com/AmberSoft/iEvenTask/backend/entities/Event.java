package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_evento")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Event extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity {

	private Integer id;
	private String name;
	private Integer periodicity;
	private Date expiration;
	private Integer iterations;
	private Set<Relation> relations;

	public Event (){
		PKGenerator pkGenerator = new PKGenerator();
		id = new Integer(pkGenerator.getIntLastTime());
	}
	
	@Id @Column (name="id_evento")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic @Column (name="nombre")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic @Column (name="periodicidad")
	public Integer getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Integer periodicity) {
		this.periodicity = periodicity;
	}
	
	@Basic @Column (name="fecha_expiracion")
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	@Basic @Column (name="iteraciones")
	public Integer getIterations() {
		return iterations;
	}
	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}

	@OneToMany (mappedBy="event")
	public Set<Relation> getRelations() {
		return relations;
	}

	public void setRelations(Set<Relation> relations) {
		this.relations = relations;
	}


	
}
