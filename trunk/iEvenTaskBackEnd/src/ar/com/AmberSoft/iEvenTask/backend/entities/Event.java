package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_evento")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Event extends ar.com.AmberSoft.iEvenTask.backend.entities.Entity {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer id;
	private String name;
	private Integer periodicity;
	private Date expiration;
	private Integer iterations;
	private Integer executions;
	private Set<Relation> relations;
	private Set<Relation> relationsAvaiables;

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

	@OneToMany (mappedBy="event", fetch= FetchType.LAZY)
	public Set<Relation> getRelations() {
		return relations;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public void filterRelationsAvaiables(){
		Iterator<Relation> iRel = relations.iterator();
		relationsAvaiables = new HashSet();
		while (iRel.hasNext()) {
			Relation relation = (Relation) iRel.next();
			if (relation.getDelete()==null){
				relationsAvaiables.add(relation);
			}
		}
	}
	

	@Transient
	public Set<Relation> getRelationsAvaiable() {
		filterRelationsAvaiables();
		return relationsAvaiables;
	}
	
	public void setRelations(Set<Relation> relations) {
		this.relations = relations;
	}

	@Basic @Column (name="ejecuciones")
	public Integer getExecutions() {
		return executions;
	}

	public void setExecutions(Integer executions) {
		this.executions = executions;
	}
	
}
