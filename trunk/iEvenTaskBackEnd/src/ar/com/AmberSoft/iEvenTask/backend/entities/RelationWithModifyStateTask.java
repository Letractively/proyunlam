package ar.com.AmberSoft.iEvenTask.backend.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.services.GetTaskService;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

@Entity
@Table (name="iet_relacion_modifica_estado")
public class RelationWithModifyStateTask extends Relation {
	
	private static Logger logger = Logger
	.getLogger(RelationWithModifyStateTask.class);
	
	private String fromState;
	private String toState;
	
	private Set<Tarea> tareas;

	
	@Transient
	public void addTarea(Integer id){
		if (tareas==null){
			tareas = new HashSet<Tarea>();
		}
		GetTaskService service = new GetTaskService();
		Map params = new HashMap();
		params.put(ParamsConst.ID, id);
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		Map result;
		try {
			result = service.execute(params);
			tareas.add((Tarea) result.get(ParamsConst.ENTITY));
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
		
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "iet_relacion_modifica_estado_tarea", 
			joinColumns = { @JoinColumn(name = "id_relacion") }, inverseJoinColumns = { @JoinColumn(name = "id_tarea") })
	public Set<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}
	
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
	@Override
	public void execute() {

		if (tareas!=null){
			Iterator it = tareas.iterator();
			while (it.hasNext()) {
				Tarea tarea = (Tarea) it.next();
				if (fromState.equals(tarea.getEstado())){
					tarea.setEstado(toState);
				}
				UpdateEntityService entityService = new UpdateEntityService();
				Map params = new HashMap();
				params.put(ParamsConst.ENTITY, tarea);
				try {
					entityService.execute(params);
				} catch (Exception e) {
					logger.error(Tools.getStackTrace(e));
				}
			}
		}		
		
	}

}
