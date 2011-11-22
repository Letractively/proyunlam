package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table (name="iet_evento_ldap")
public class EventLDAP extends Event {

	private String code;

	@Basic @Column (name="codigo")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
