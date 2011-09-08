package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Entity implements Serializable {

	public static String DELETE_COLUMN = "eliminado";
	
	private Date delete;

	@Basic @Column (name="eliminado")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDelete() {
		return delete;
	}

	public void setDelete(Date delete) {
		this.delete = delete;
	}
	
}
