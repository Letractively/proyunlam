package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_tarea")
public class Task implements Serializable {
	private Integer id;
	private Date start;
	private Date end;
	private Integer hs;
	private String user;
	private Date creation;
	private Date lastModify;
	
	private Integer complete;
	private Set subtasks;
	
	
	
	@OneToMany 
	public Set getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(Set subtasks) {
		this.subtasks = subtasks;
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
	}

	@Basic @Column (name="fecha_inicio")
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@Basic @Column (name="fecha_fin")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Basic @Column (name="horas_asignadas")
	public Integer getHs() {
		return hs;
	}

	public void setHs(Integer hs) {
		this.hs = hs;
	}

	@Basic @Column (name="id_usuario")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Basic @Column (name="fecha_creacion")
	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Basic @Column (name="fecha_modificacion")
	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	@Basic @Column (name="cumplimiento")
	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	
	
	
	
}
