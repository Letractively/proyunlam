package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.events.BackgroundEventDetectProcess;
import ar.com.AmberSoft.iEvenTask.services.CreateTaskService;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.util.ParamsConst;
import ar.com.AmberSoft.util.StatusConst;

@Entity
@Table (name="iet_relacion_nueva_tarea")
public class RelationWithActionCreateTask extends Relation {

	private static Logger logger = Logger
	.getLogger(RelationWithActionCreateTask.class);

	private String name;
	private String user;
	
	private Tarea tarea;
	

	@OneToOne 
	@JoinColumn(name="id_tarea")
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
	
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
	public void execute() throws Exception {
	
		if ((this.getTarea()==null) || (this.getTarea().getDelete()!=null) ||
				((!StatusConst.PENDIENTE.equals(this.getTarea().getEstado())) && (!StatusConst.EN_CURSO.equals(this.getTarea().getEstado()))) ){
			Map params = new HashMap();
			params.put(ParamsConst.NOMBRE_TAREA, name);
			params.put(ParamsConst.ID_USUARIO, user);
			
			Set visibles = this.getVisibles();
			if (visibles!=null){
				Collection<String> nuevosVisibles = new ArrayList<String>();
				Iterator<VisibleRelation> it = visibles.iterator();
				while (it.hasNext()) {
					VisibleRelation visibleRelation = (VisibleRelation) it.next();
					nuevosVisibles.add(visibleRelation.getUsuario());
				}
				params.put(ParamsConst.USERS_VIEW, nuevosVisibles);
			}
			
			CreateTaskService service = new CreateTaskService();
			params.put(ParamsConst.ESTADO, StatusConst.PENDIENTE);
			params.put(ParamsConst.GET_ENTITY, Boolean.TRUE);
			Map result = service.execute(params);
			Tarea tarea = (Tarea) result.get(ParamsConst.ENTITY);
			this.setTarea(tarea);

			result.put(ParamsConst.ENTITY, this);
			UpdateEntityService updateEntityService = new UpdateEntityService();
			updateEntityService.execute(result);
		} else {
			logger.info("La tarea anterior (" + this.getTarea().getNombreTarea() + "), aun no se encuentra finalizada o suspendida." );
		}
		
	}
	
	
}
