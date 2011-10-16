package ar.com.AmberSoft.iEvenTask.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseModel;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class EventWindowOption {

	public static final String LDAP = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP";
	public static final String LOGS = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs";
	public static final String FILES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles";
	public static final String SERVICES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventServices";

	protected EventWindow eventWindow;

	public EventWindow getEventWindow() {
		return eventWindow;
	}

	public void setEventWindow(EventWindow eventWindow) {
		this.eventWindow = eventWindow;
	}

	public abstract void onSave(Map params);

	public abstract void setVisiblePanel();

	public abstract void beforeUpdate(Map actual);

	public abstract Boolean isValid();

	protected void setId(Map params) {
		List seleccionados = eventWindow.getGrid().getSelectionModel().getSelection();
		if (seleccionados.size() == 1) {
			Iterator it = seleccionados.iterator();
			if (it.hasNext()) {
				BaseModel baseModel = (BaseModel) it.next();
				params.put(ParamsConst.ID, baseModel.get(ParamsConst.ID));
			}
		}

	}

}
