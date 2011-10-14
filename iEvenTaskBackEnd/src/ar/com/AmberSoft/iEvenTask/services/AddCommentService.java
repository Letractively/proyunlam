package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Comentario;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

public class AddCommentService extends Service {

	private static Logger logger = Logger.getLogger(AddCommentService.class);
	
	@Override
	public Map onExecute(Map params) {

		GetTaskService getTaskService = new GetTaskService();
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		Transaction transaction = getTaskService.getSession().beginTransaction();
		try {
		Map result = getTaskService.execute(params);
		Tarea tarea = (Tarea) result.get(ParamsConst.ENTITY);
		
		String comment = (String) params.get(ParamsConst.COMMENT);
		Comentario comentario = new Comentario();
		comentario.setComentario(comment);
		comentario.setTarea(tarea);

		getSession().save(comentario);		
		transaction.commit();
		
		} catch (Exception e){
			transaction.rollback();
			logger.error(Tools.getStackTrace(e));
		}
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}
