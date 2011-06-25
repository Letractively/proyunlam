package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="operation")
public class Operation {
	private String id;
	private String description;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Basic
	public String getDescription() {
		return description;
	}
	public void setDescription(String descripcion) {
		this.description = descripcion;
	}
	

}
