package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

public class EventWindowOptionLDAP extends EventWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.CODE, eventWindow.getFldCode().getValue());
		if (eventWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_EVENT_LDAP);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_LDAP);
		}

	}

	@Override
	public void setVisiblePanel() {
		eventWindow.getvPanelLDAP().show();
		eventWindow.getvPanelPatron().setVisible(Boolean.FALSE);
		eventWindow.getvPanelArchivos().setVisible(Boolean.FALSE);
		eventWindow.getvPanelServicios().setVisible(Boolean.FALSE);
	}

	@Override
	public void beforeUpdate(Map actual) {
		eventWindow.getFldCode().setValue(actual.get(ParamsConst.CODE));
		eventWindow.setCombo(eventWindow.getFldType(), (String)actual.get(ParamsConst.CLASS));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		return eventWindow.getFldCode().isValid();
	}


}
