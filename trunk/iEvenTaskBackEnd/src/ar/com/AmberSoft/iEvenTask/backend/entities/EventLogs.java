package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="iet_evento_logs")
public class EventLogs extends Event {

	private String path;
	private String patern;
	
	@Basic  @Column (name="ruta")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Basic @Column (name="patron")
	public String getPatern() {
		return patern;
	}
	public void setPatern(String patern) {
		this.patern = patern;
	}
	
}
