package ar.com.AmberSoft.iEvenTask.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.com.AmberSoft.util.PKGenerator;

@Entity
@Table (name="iet_tarea_plantilla")
public class TaskTemplate extends Task {

	
	@Id @Column (name="id_tarea")
	public Integer getId() {
		if (getId()==null){
			PKGenerator pkGenerator = new PKGenerator();
			setId(new Integer(pkGenerator.getIntLastTime()));
		}
		return getId();
	}

	
	@Override
	@Basic @Column (name="id_usuario")
	public String getUser() {
		return super.getUser();
	}

	@Basic @Column (name="nombre")
	public String getName() {
		return getName();
	}
}
