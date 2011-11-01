package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="iet_rel_visible")
public class VisibleRelation {
	private String id;
	private String usuario;
	private Relation relation;
	
	public VisibleRelation(){}
	
	
	public VisibleRelation(Relation relation, String usuario){
		this.relation = relation;
		this.usuario = usuario;
		changeTask(relation);
	}

	@Transient
	public void changeTask(Relation relation) {
		this.relation = relation;
		if ((relation!=null) && (relation.getId())!=null && (usuario!=null)){
			this.id = usuario + relation.getId().toString();
		}
	}
	
	
	@Id @Column (name="id_rel_visible")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn (name="id_relacion")
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	@Basic @Column (name="id_usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public boolean equals(Object actual) {
		if (actual instanceof Visible) {
			return ((Visible)actual).getId().equals(this.getId());
		}
		return Boolean.FALSE;
	}
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

}
