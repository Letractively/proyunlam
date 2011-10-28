package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="iet_evento_servicios")
public class EventServices extends Event {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String host;
	private String port;
	
	@Basic @Column (name="servidor")
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	@Basic @Column (name="port")
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
