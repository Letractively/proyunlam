package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="iet_evento_archivos")
public class EventFiles extends Event {
	
	private Integer controlType;
	private String path;
	
	@Basic @Column (name="tipo")
	public Integer getControlType() {
		return controlType;
	}
	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}
	
	@Basic @Column (name="ruta")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
