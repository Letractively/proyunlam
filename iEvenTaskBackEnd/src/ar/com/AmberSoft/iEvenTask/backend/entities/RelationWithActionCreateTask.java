package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.AmberSoft.iEvenTask.services.CreateTaskService;
import ar.com.AmberSoft.util.ParamsConst;

@Entity
@Table (name="iet_relacion_nueva_tarea")
public class RelationWithActionCreateTask extends Relation {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String name;
	private String user;
	
	@Basic @Column (name="nombre")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic @Column (name="id_user")
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void execute() {
	
		Map params = new HashMap();
		params.put(ParamsConst.NOMBRE_TAREA, name);
		params.put(ParamsConst.ID_USUARIO, user);
		
		CreateTaskService service = new CreateTaskService();
		service.execute(params);
		
	}
	
	
}
