package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Comentario;
import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceExecuteException;
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

			HttpServletRequest request = (HttpServletRequest) params.get(ParamsConst.REQUEST);
			String user = (String) request.getSession().getAttribute(ParamsConst.USER);
			
			String comment = (String) params.get(ParamsConst.COMMENT);
			Comentario comentario = new Comentario();
			comentario.setComentario(comment);
			comentario.setTarea(tarea);
			
			// FIXME: Solo de prueba luego descomentar y quitar la linea que setea el usuario a la fuerza
			//comentario.setUsuario(user);
			comentario.setUsuario("USERTEST");
			
			comentario.setFecha(new Date());
	
			getSession().save(comentario);		
			transaction.commit();
		
		} catch (Exception e){
			transaction.rollback();
			logger.error(Tools.getStackTrace(e));
			throw new ServiceExecuteException();
		}
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}
