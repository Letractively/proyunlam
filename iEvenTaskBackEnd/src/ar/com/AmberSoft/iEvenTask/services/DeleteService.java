package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.util.ParamsConst;

/**
 * Servicio general para eliminar
 * Precondiciones de uso:
 * 		La entidad debe heredar de entity
 * 		La entidad debe tener su clave principal en el atributo id
 * @author Leo
 *
 */
public abstract class DeleteService extends Service {

	public abstract Class getEntity();
	
	@Override
	public Map onExecute(Map params) {
		Transaction transaction = getSession().beginTransaction();
		Collection ids = (Collection) params.get(ParamsConst.IDS);
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			Entity entity = (Entity) getSession().get(getEntity(), id);
			entity.setDelete(new Date());
			getSession().saveOrUpdate(entity);
		}
		transaction.commit();
		
		return null;		
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}

}