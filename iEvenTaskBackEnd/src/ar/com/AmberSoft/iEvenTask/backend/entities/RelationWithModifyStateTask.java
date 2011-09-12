package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="iet_relacion_modifica_estado")
public class RelationWithModifyStateTask extends Relation {
	
	private String fromState;
	private String toState;
	
	@Basic @Column (name="estado_inicial")
	public String getFromState() {
		return fromState;
	}
	public void setFromState(String fromState) {
		this.fromState = fromState;
	}
	
	@Basic @Column (name="estado_final")
	public String getToState() {
		return toState;
	}
	public void setToState(String toState) {
		this.toState = toState;
	}

}
